package client.map.states;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.session.SessionManager;

public class Robbing_State implements IMapState 
{
	@Override
	public boolean canBuildRoad(EdgeLocation e)
	{
		return false;
	}

	@Override
	public boolean canBuildSettlement(VertexLocation v)
	{
		return false;
	}

	@Override
	public boolean canPlaceCity(VertexLocation v)
	{
		return false;
	}

	@Override
	public boolean canPlaceRobber(HexLocation h)
	{
		return SessionManager.instance().getClientFacade().canPlaceRobber(SessionManager.get_instance().getPlayerIndex(),h);
	}

	@Override
	public String getStateName()
	{
		return "robbing";
	}
}
