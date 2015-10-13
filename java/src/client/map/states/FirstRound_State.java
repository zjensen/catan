package client.map.states;

import client.session.SessionManager;
import shared.communication.moves.*;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

public class FirstRound_State implements IMapState {

	@Override
	public boolean canBuildRoad(EdgeLocation e)
	{
		BuildRoad_Input params = new BuildRoad_Input(SessionManager.instance().getPlayerIndex(), e, true);
		return SessionManager.instance().getClientFacade().canBuildFirstRoad(params);
	}

	@Override
	public boolean canBuildSettlement(VertexLocation v)
	{
		BuildSettlement_Input params = new BuildSettlement_Input(SessionManager.instance().getPlayerIndex(), v, true);
		return SessionManager.instance().getClientFacade().canBuildSettlement(params);
	}

	@Override
	public boolean canPlaceCity(VertexLocation v)
	{
		return false;
	}

	@Override
	public boolean canPlaceRobber(HexLocation h)
	{
		return false;
	}
	
	@Override
	public String getStateName()
	{
		return "first";
	}

}
