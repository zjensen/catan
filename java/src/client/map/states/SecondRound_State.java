package client.map.states;

import shared.communication.moves.BuildRoad_Input;
import shared.communication.moves.BuildSettlement_Input;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.session.SessionManager;

public class SecondRound_State implements IMapState
{
	@Override
	public boolean canBuildRoad(EdgeLocation e)
	{
		BuildRoad_Input params = new BuildRoad_Input(SessionManager.instance().getPlayerIndex(), e, true);
		return SessionManager.instance().getClientFacade().canBuildSecondRoad(params);
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
		return "second";
	}
	
}
