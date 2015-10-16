package client.map.states;

import shared.communication.moves.BuildCity_Input;
import shared.communication.moves.BuildRoad_Input;
import shared.communication.moves.BuildSettlement_Input;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.session.SessionManager;

public class Playing_State implements IMapState 
{
	@Override
	public boolean canBuildRoad(EdgeLocation e)
	{
		BuildRoad_Input params = new BuildRoad_Input(SessionManager.instance().getPlayerIndex(), e, false);
		return SessionManager.instance().getClientFacade().canBuildRoad(params);
	}

	@Override
	public boolean canBuildSettlement(VertexLocation v)
	{
		BuildSettlement_Input params = new BuildSettlement_Input(SessionManager.instance().getPlayerIndex(), v, false);
		return SessionManager.instance().getClientFacade().canBuildSettlement(params);
	}

	@Override
	public boolean canPlaceCity(VertexLocation v)
	{
		BuildCity_Input params = new BuildCity_Input(SessionManager.instance().getPlayerIndex(), v);
		return SessionManager.instance().getClientFacade().canBuildCity(params);
	}

	@Override
	public boolean canPlaceRobber(HexLocation h)
	{
		return false;
	}
	
	@Override
	public String getStateName()
	{
		return "playing";
	}
}
