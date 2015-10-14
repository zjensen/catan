package client.map;

import java.util.*;
import java.util.Map.Entry;

import shared.communication.moves.RobPlayer_Input;
import shared.definitions.*;
import shared.locations.*;
import shared.models.ClientModel;
import shared.models.Hex;
import shared.models.Player;
import shared.models.Port;
import client.base.*;
import client.data.*;
import client.map.states.*;
import client.session.SessionManager;


/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController, Observer {
	
	private IRobView robView;
	private boolean initiated = false;
	private IMapState state;
	private boolean robStarted = false;
	private boolean playingSoldierCard = false;
	HexLocation robberLocation;
	
	public MapController(IMapView view, IRobView robView) {
		
		super(view);
		
		setRobView(robView);
		
		makeItRain();
		
		SessionManager.instance().addObserver(this);
		
		state = new Nothing_State();
	}
	
	@Override
	public void update(Observable o, Object arg)
	{ 	
		if(!initiated)
		{
			initiated=true;
			initFromModel();
		}
		updateFromModel();
		
		if(SessionManager.instance().getClientModel().getPlayers().length == 4) //are all the players here??
		{
			setupState();
		}
	}
	
	private void setupState()
	{
		if(!SessionManager.instance().isOurTurn())
		{
			if(!state.getStateName().equals("nothing"))
			{
				state = new Nothing_State();
			}
			return;
		}
		switch(SessionManager.instance().clientModel.getTurnTracker().getStatus().toLowerCase())
		{
			case "firstround":
				if(!state.getStateName().equals("first"))
				{
					state = new FirstRound_State();
				}
				break;
			case "secondround":
				if(!state.getStateName().equals("second"))
				{
					state = new SecondRound_State();
				}
				break;
			case "robbing":
				if(!robStarted)
				{
					robStarted = true;
					getView().startDrop(PieceType.ROBBER, SessionManager.instance().getPlayerInfo().getColor(), false);
				}
				if(!state.getStateName().equals("robbing"))
				{
					state = new Robbing_State();
				}
				break;
			case "playing":
				if(!state.getStateName().equals("playing"))
				{
					state = new Playing_State();
				}
				break;
			case "rolling":
			case "discarding":
			default:
				if(!state.getStateName().equals("nothing"))
				{
					state = new Nothing_State();
				}
				break;
				
		}
		
	}

	public IMapView getView() {
		
		return (IMapView)super.getView();
	}
	
	private IRobView getRobView() {
		return robView;
	}
	private void setRobView(IRobView robView) {
		this.robView = robView;
	}
	
	//update the map to reflect the model's cities, settlements, roads, and robber
	private void updateFromModel()
	{
		ClientModel model = SessionManager.instance().getClientModel();
		
		//place cities
		HashMap<VertexLocation,Player> cities = model.getMap().getCities();
		for (Entry<VertexLocation, Player> entry : cities.entrySet())
		{
			getView().placeCity(entry.getKey(), entry.getValue().getCatanColor());
		}
		
		//place settlements
		HashMap<VertexLocation,Player> settlements = model.getMap().getSettlements();
		for (Entry<VertexLocation, Player> entry : settlements.entrySet())
		{
			getView().placeSettlement(entry.getKey(), entry.getValue().getCatanColor());
		}
		
		//place roads
		HashMap<EdgeLocation,Player> roads = model.getMap().getRoads();
		for (Entry<EdgeLocation, Player> entry : roads.entrySet())
		{
			getView().placeRoad(entry.getKey(), entry.getValue().getCatanColor());
		}
		
		//place robber
		getView().placeRobber(model.getMap().getRobber());
	}
	
	private void initFromModel()
	{
		ClientModel model = SessionManager.instance().getClientModel();
		
		Hex[] hexes = model.getMap().getHexes();
		for(Hex hex : hexes)
		{
			if(hex.getResource()==null)
			{
				getView().addHex(hex.getLocation(), HexType.DESERT);
			}
			else
			{
				getView().addHex(hex.getLocation(), hex.getResource());
				getView().addNumber(hex.getLocation(), hex.getNumber());
			}
		}
		
		Port[] ports = model.getMap().getPorts();
		for(Port port : ports)
		{
			getView().addHex(port.getLocation(), HexType.WATER);
			if(port.getResourceType() == null)
			{
				getView().addPort(new EdgeLocation(port.getLocation(),port.getDirection()), PortType.THREE);
			}
			else
			{
				getView().addPort(new EdgeLocation(port.getLocation(),port.getDirection()), PortType.valueOf(port.getResourceType().toString()));
			}
		}
		
	}
	
	//cover it in water. all of it.
	protected void makeItRain() 
	{
		for (int x = 0; x <= 3; ++x) 
		{
			
			int maxY = 3 - x;			
			for (int y = -3; y <= maxY; ++y) {				
				HexType hexType = HexType.WATER;
				HexLocation hexLoc = new HexLocation(x, y);
				getView().addHex(hexLoc, hexType);
				
			}
			
			if (x != 0) {
				int minY = x - 3;
				for (int y = minY; y <= 3; ++y) {
					HexType hexType = HexType.WATER;
					HexLocation hexLoc = new HexLocation(-x, y);
					getView().addHex(hexLoc, hexType);
				}
			}
		}
	}

	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		return state.canBuildRoad(edgeLoc.getNormalizedLocation());
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		return state.canBuildSettlement(vertLoc.getNormalizedLocation());
	}

	public boolean canPlaceCity(VertexLocation vertLoc) {
		return state.canPlaceCity(vertLoc.getNormalizedLocation());
	}

	public boolean canPlaceRobber(HexLocation hexLoc) {
		return state.canPlaceRobber(hexLoc);
	}

	public void placeRoad(EdgeLocation edgeLoc) {
		
		getView().placeRoad(edgeLoc, SessionManager.instance().getPlayerInfo().getColor());
	}

	public void placeSettlement(VertexLocation vertLoc) {
		
		getView().placeSettlement(vertLoc, SessionManager.instance().getPlayerInfo().getColor());
	}

	public void placeCity(VertexLocation vertLoc) {
		
		getView().placeCity(vertLoc, SessionManager.instance().getPlayerInfo().getColor());
	}

	public void placeRobber(HexLocation hexLoc) {
		getView().placeRobber(hexLoc);
		getRobView().showModal();
		ArrayList<RobPlayerInfo> robPlayerArrayList = new ArrayList<RobPlayerInfo>();
		int index = SessionManager.instance().getPlayerIndex();
		
		for(int i=0;i<4;i++)
		{
			if(i!=index)
			{
				int q = 3;
				RobPlayer_Input params = new RobPlayer_Input(index, i, hexLoc);
				if(SessionManager.instance().getClientFacade().canRobPlayer(params))
				{
					RobPlayerInfo r = SessionManager.instance().getClientFacade().getRobPlayerInfo(i);
					robPlayerArrayList.add(r);
				}
			}
		}
		
		RobPlayerInfo[] robPlayerArray = new RobPlayerInfo[robPlayerArrayList.size()];
		robPlayerArrayList.toArray(robPlayerArray);
		getRobView().setPlayers(robPlayerArray);
		robberLocation = hexLoc;
	}
	
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {	
		
		getView().startDrop(pieceType, SessionManager.instance().getPlayerInfo().getColor(), true);
	}
	
	public void cancelMove() {
		int r = 2;
		r++;
	}
	
	public void playSoldierCard() {	
		playingSoldierCard = true;
	}
	
	public void playRoadBuildingCard() {	
		
	}
	
	public void robPlayer(RobPlayerInfo victim) {	
		RobPlayer_Input params = new RobPlayer_Input(SessionManager.instance().getPlayerIndex(),victim.getPlayerIndex(),robberLocation);
		SessionManager.instance().getClientFacade().robPlayer(params);
		robStarted = false;
		playingSoldierCard = false;
	}
	
}

