package client.map;

import java.util.*;
import java.util.Map.Entry;

import shared.communication.moves.*;
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
	private boolean roadBuilding = false;
	private EdgeLocation firstRoadBuilding;
	HexLocation robberLocation;
	RobPlayerInfo[] empty = {};
	
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
		if(arg.equals("reset")) //are all the players here??
		{
			getView().reset();
			makeItRain();
			state = new Nothing_State();
			initiated = false;
			return;
		}
		
		if(!initiated)
		{
			getView().reset();
			makeItRain();
			state = new Nothing_State();
			initiated=true;
			initFromModel();
		}
		
		
		if(arg.equals(true)) //are all the players here??
		{
			setupState();
			updateFromModel();
		}
	}
	
	private void setupState()
	{
		if(!SessionManager.instance().isOurTurn()) //sit tight 
		{
			if(!state.getStateName().equals("nothing"))
			{
				state = new Nothing_State();
			}
			return;
		}
		if(playingSoldierCard) //rob them woes
		{
			if(!state.getStateName().equals("robbing"))
			{
				state = new Robbing_State();
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
				if(SessionManager.instance().getClientModel().getPlayerByIndex(SessionManager.instance().getPlayerIndex()).getRoadsPlayed() == 0)
				{
					if(!getView().isOverlayShowing())
					{
						getView().startDrop(PieceType.ROAD, SessionManager.instance().getPlayerInfo().getColor(), false);
					}
				}
				else if(SessionManager.instance().getClientModel().getPlayerByIndex(SessionManager.instance().getPlayerIndex()).getSettlementsPlayed() == 0)
				{
					if(!getView().isOverlayShowing())
					{
						getView().startDrop(PieceType.SETTLEMENT, SessionManager.instance().getPlayerInfo().getColor(), false);
					}
				}
				break;
			case "secondround":
				if(!state.getStateName().equals("second"))
				{
					state = new SecondRound_State();
				}
				if(SessionManager.instance().getClientModel().getPlayerByIndex(SessionManager.instance().getPlayerIndex()).getRoadsPlayed() == 1)
				{
					if(!getView().isOverlayShowing())
					{
						getView().startDrop(PieceType.ROAD, SessionManager.instance().getPlayerInfo().getColor(), false);
					}
				}
				else if(SessionManager.instance().getClientModel().getPlayerByIndex(SessionManager.instance().getPlayerIndex()).getSettlementsPlayed() == 1)
				{
					if(!getView().isOverlayShowing())
					{
						getView().startDrop(PieceType.SETTLEMENT, SessionManager.instance().getPlayerInfo().getColor(), false);
					}
				}	
				break;
			case "robbing":
				if(!robStarted)
				{
					robStarted = true;
				}
				if(!getView().isOverlayShowing())
				{
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
		if(roadBuilding)
		{
			if(firstRoadBuilding==null) //first of 2 roads
			{
				return SessionManager.instance().getClientFacade().canRoadBuilding(new RoadBuilding_Input(SessionManager.instance().getPlayerIndex(), edgeLoc, null));
			}
			else if(SessionManager.instance().getClientFacade().getRoads(SessionManager.instance().getPlayerIndex()) < 2)
			{
				//alert not enough roads left to build, and we should send this request on it's way
				return false;
			}
			else
			{
				return SessionManager.instance().getClientFacade().canRoadBuilding(new RoadBuilding_Input(SessionManager.instance().getPlayerIndex(), edgeLoc, firstRoadBuilding));
			}
		}
		return state.canBuildRoad(edgeLoc.getNormalizedLocation());
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		return state.canBuildSettlement(vertLoc.getNormalizedLocation());
	}

	public boolean canPlaceCity(VertexLocation vertLoc) {
		return state.canPlaceCity(vertLoc.getNormalizedLocation());
	}

	//might not work when playing knight card
	public boolean canPlaceRobber(HexLocation hexLoc) {
		return state.canPlaceRobber(hexLoc);
	}

	//may need some work
	public void placeRoad(EdgeLocation edgeLoc) {
		getView().placeRoad(edgeLoc, SessionManager.instance().getPlayerInfo().getColor());
		
		if(state.getStateName().equalsIgnoreCase("first") || state.getStateName().equalsIgnoreCase("second"))
		{
			SessionManager.instance().getClientFacade().buildRoad(new BuildRoad_Input(SessionManager.instance().getPlayerIndex(), edgeLoc, true));
//			getView().startDrop(PieceType.SETTLEMENT, SessionManager.instance().getPlayerInfo().getColor(), false);
		}
		else if(roadBuilding)
		{
			if(firstRoadBuilding==null) //first road yet to be placed
			{
				if(SessionManager.instance().getClientFacade().getRoads(SessionManager.instance().getPlayerIndex()) == 1)
				{
					//can only place 1 road
					RoadBuilding_Input params = new RoadBuilding_Input(SessionManager.instance().getPlayerIndex(), edgeLoc, null);
					SessionManager.instance().getClientFacade().roadBuilding(params);
					roadBuilding = false;
				}
				else
				{
					//1 more road to place
					firstRoadBuilding = edgeLoc;
					getView().startDrop(PieceType.ROAD, SessionManager.instance().getPlayerInfo().getColor(), true);
				}
			}
			else //placing second road
			{
				RoadBuilding_Input params = new RoadBuilding_Input(SessionManager.instance().getPlayerIndex(), edgeLoc, firstRoadBuilding);
				SessionManager.instance().getClientFacade().roadBuilding(params);
				firstRoadBuilding = null;
				roadBuilding = false;
			}
		}
		else
		{
			SessionManager.instance().getClientFacade().buildRoad(new BuildRoad_Input(SessionManager.instance().getPlayerIndex(), edgeLoc, false));
		}
	}

	public void placeSettlement(VertexLocation vertLoc) {
		getView().placeSettlement(vertLoc, SessionManager.instance().getPlayerInfo().getColor());
		if(state.getStateName().equalsIgnoreCase("first") || state.getStateName().equalsIgnoreCase("second"))
		{
			SessionManager.instance().getClientFacade().buildSettlement(new BuildSettlement_Input(SessionManager.instance().getPlayerIndex(), vertLoc, true));
			SessionManager.instance().getClientFacade().finishTurn(new FinishTurn_Input(SessionManager.instance().getPlayerIndex()));
		}
		else
		{
			SessionManager.instance().getClientFacade().buildSettlement(new BuildSettlement_Input(SessionManager.instance().getPlayerIndex(), vertLoc, false));
		}
	}

	public void placeCity(VertexLocation vertLoc) {
		
		getView().placeCity(vertLoc, SessionManager.instance().getPlayerInfo().getColor());
		SessionManager.instance().getClientFacade().buildCity(new BuildCity_Input(SessionManager.instance().getPlayerIndex(), vertLoc));
	}

	public void placeRobber(HexLocation hexLoc) {
		if(getRobView().isModalShowing())
		{
			getRobView().closeModal();
		}
		getRobView().setPlayers(null);
		getView().placeRobber(hexLoc);
		
		ArrayList<RobPlayerInfo> robPlayerArrayList = new ArrayList<RobPlayerInfo>();
		int index = SessionManager.instance().getPlayerIndex();
		
		for(int i=0;i<4;i++)
		{
			if(i!=index)
			{
				RobPlayer_Input params = new RobPlayer_Input(index, i, hexLoc);
				if(SessionManager.instance().getClientFacade().canRobPlayer(params))
				{
					RobPlayerInfo r = SessionManager.instance().getClientFacade().getRobPlayerInfo(i);
					robPlayerArrayList.add(r);
				}
			}
		}
		if(robPlayerArrayList.size() != 0)
		{
			RobPlayerInfo[] robPlayerArray = new RobPlayerInfo[robPlayerArrayList.size()];
			robPlayerArrayList.toArray(robPlayerArray);
			getRobView().setPlayers(robPlayerArray);
		}
		
		getRobView().showModal();
		robberLocation = hexLoc;
	}
	
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {	
		
		getView().startDrop(pieceType, SessionManager.instance().getPlayerInfo().getColor(), true);
	}
	
	//guessing this is for dev cards
	public void cancelMove() {
		playingSoldierCard = false;
		roadBuilding = false;
		if(!state.getStateName().equals("playing"))
		{
			state = new Playing_State();
		}
		if(firstRoadBuilding != null)
		{
			getView().reset();
			makeItRain();
			initFromModel();
			updateFromModel();
		}
	}
	
	public void playSoldierCard() {	
		playingSoldierCard = true;
		state = new Robbing_State();
		getView().startDrop(PieceType.ROBBER, SessionManager.instance().getPlayerInfo().getColor(), true);
	}
	
	public void playRoadBuildingCard() {
		
		if(SessionManager.instance().getClientFacade().getRoads(SessionManager.instance().getPlayerIndex()) == 0)
		{
			//alert no roads left to build
			return;
		}
		firstRoadBuilding=null;
		roadBuilding = true;
		getView().startDrop(PieceType.ROAD, SessionManager.instance().getPlayerInfo().getColor(), true);
	}
	
	public void robPlayer(RobPlayerInfo victim) {	
		if(playingSoldierCard)
		{
			playingSoldierCard = false;
			Soldier_Input params;
			if(victim == null)
			{
				params = new Soldier_Input(SessionManager.instance().getPlayerIndex(),-1,robberLocation);
			}
			else
			{
				params = new Soldier_Input(SessionManager.instance().getPlayerIndex(),victim.getPlayerIndex(),robberLocation);
			}
			
			SessionManager.instance().getClientFacade().soldier(params);
		}
		else
		{
			RobPlayer_Input params;
			if(victim == null)
			{
				params = new RobPlayer_Input(SessionManager.instance().getPlayerIndex(),-1,robberLocation);
			}
			else
			{
				params = new RobPlayer_Input(SessionManager.instance().getPlayerIndex(),victim.getPlayerIndex(),robberLocation);
			}
			SessionManager.instance().getClientFacade().robPlayer(params);
			robStarted = false;
		}
		if(getRobView().isModalShowing())
		{
			getRobView().closeModal();
		}
	}
	
}

