package client.map.states;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

public interface IMapState 
{
	public String getStateName();
	
	public boolean canBuildRoad(EdgeLocation e);
	
	public boolean canBuildSettlement(VertexLocation v);
	
	public boolean canPlaceCity(VertexLocation v);
	
	public boolean canPlaceRobber(HexLocation h);
}
