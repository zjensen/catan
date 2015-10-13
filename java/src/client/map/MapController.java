package client.map;

import java.util.*;
import java.util.Map.Entry;

import shared.communication.moves.BuildCity_Input;
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
		
		return true;
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		
		return true;
	}

	public boolean canPlaceCity(VertexLocation vertLoc) {
		return SessionManager.instance().getClientFacade().canBuildCity(new BuildCity_Input(SessionManager.instance().getPlayerIndex(), vertLoc));
	}

	public boolean canPlaceRobber(HexLocation hexLoc) {
		
		return true;
	}

	public void placeRoad(EdgeLocation edgeLoc) {
		
		getView().placeRoad(edgeLoc, CatanColor.ORANGE);
	}

	public void placeSettlement(VertexLocation vertLoc) {
		
		getView().placeSettlement(vertLoc, CatanColor.ORANGE);
	}

	public void placeCity(VertexLocation vertLoc) {
		
		getView().placeCity(vertLoc, CatanColor.ORANGE);
	}

	public void placeRobber(HexLocation hexLoc) {
		
		getView().placeRobber(hexLoc);
		
		getRobView().showModal();
	}
	
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {	
		
		getView().startDrop(pieceType, CatanColor.ORANGE, true);
	}
	
	public void cancelMove() {
		
	}
	
	public void playSoldierCard() {	
		
	}
	
	public void playRoadBuildingCard() {	
		
	}
	
	public void robPlayer(RobPlayerInfo victim) {	
		
	}
	
}

