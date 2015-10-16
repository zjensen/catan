package client.map.states;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

public class Nothing_State implements IMapState 
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
		return false;
	}

	@Override
	public String getStateName()
	{
		return "nothing";
	}
}
