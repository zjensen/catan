package shared.models;

import java.util.HashMap;
import java.util.Map.Entry;

import shared.communication.moves.*;
import shared.locations.*;

public class Map {
	private Hex[] hexes;
	private Port[] ports;
	private HashMap<EdgeLocation, Player> roads;
	private HashMap<VertexLocation, Player> settlements;
	private HashMap<VertexLocation, Player> cities;
	private int radius;
	private HexLocation robber;
	
	public Map(Hex[] hexes, Port[] ports, HashMap<EdgeLocation, Player> roads,
			HashMap<VertexLocation, Player> settlements, HashMap<VertexLocation, Player> cities, int radius,
			HexLocation robber) 
	{
		super();
		this.hexes = hexes;
		this.ports = ports;
		this.roads = roads;
		this.settlements = settlements;
		this.cities = cities;
		this.radius = radius;
		this.robber = robber;
	}
	
	/**
	 * check if vertex is a valid location on the map
	 * @param v
	 * @return
	 */
	public boolean vertexIsOnMap(VertexLocation v)
	{
		v = v.getNormalizedLocation();
		HexLocation h = v.getHexLoc();
		int max = radius+1;
		int x = h.getX();
		int y = h.getY();
		
		if(isOceanHex(h))
		{
			if(v.getDir() == VertexDirection.NorthWest)
			{
				return((x == max && y > -max) || (y == max && x > -max) || x + y == max);
			}
			else// if(e.getDir() == VertexDirection.NorthEast)
			{
				return((x == -max && y > 0) || y == max || (x + y == max && y > 0));
			}
		}
		else
		{
			return(Math.abs(x) < max && Math.abs(y) < max); //it is on a suitable hex
		}
	}

	/**
	 * ensures the road location is on the map
	 * @param e
	 * @return
	 */
	public boolean roadIsOnMap(EdgeLocation e)
	{
		e = e.getNormalizedLocation();
		HexLocation h = e.getHexLoc();
		int x = h.getX();
		int y = h.getY();
		int max = radius+1;
		
		if(isOceanHex(h))
		{
			if(e.getDir() == EdgeDirection.NorthWest)
			{
				return((x == max && y > -max) || x + y == max);
			}
			else if(e.getDir() == EdgeDirection.North) 
			{
				return((y == max && x > -max) || x + y == max);
			}
			else// if(e.getDir() == EdgeDirection.NorthEast)
			{
				return((x == -max && y > 0) || (y == max && x < 0));
			}
		}
		else
		{
			return(Math.abs(x) < max && Math.abs(y) < max); //it is on a suitable hex
		}
	}
	
	public boolean isOceanHex(HexLocation h)
	{
		int max = radius+1;
		if(Math.abs(h.getX()) == max || Math.abs(h.getY()) == max)
		{
			return true;
		}
		else if(Math.abs(h.getX() + h.getY()) == max)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * checks if the specified location on the map is available for that person to build on
	 * @param params
	 * @return
	 */
	public boolean canRobPlayer(RobPlayer_Input params)
	{
		if(params.getLocation() == robber) //the location is not the same as the previous
		{
			return false;
		}
		else
		{
			for (Entry<VertexLocation, Player> entry : settlements.entrySet()) //does the victim have a settlement at the location?
			{
			    if(entry.getKey().getHexLoc() == params.getLocation() && entry.getValue().getIndex() == params.getVictimIndex())
			    {
			    	return true; //victim has a settlement at the location
			    }
			}
			for (Entry<VertexLocation, Player> entry : cities.entrySet()) //does the victim have a city at the location?
			{
				if(entry.getKey().getHexLoc() == params.getLocation() && entry.getValue().getIndex() == params.getVictimIndex())
			    {
			    	return true; //victim has a city at the location
			    }
			}
		}
		
		return false;
	}
	
	public boolean canSoldier(Soldier_Input params)
	{
		if(params.getLocation() == robber) //the location is not the same as the previous
		{
			return false;
		}
		else
		{
			for (Entry<VertexLocation, Player> entry : settlements.entrySet()) //does the victim have a settlement at the location?
			{
			    if(entry.getKey().getHexLoc() == params.getLocation() && entry.getValue().getIndex() == params.getVictimIndex())
			    {
			    	return true; //victim has a settlement at the location
			    }
			}
			for (Entry<VertexLocation, Player> entry : cities.entrySet()) //does the victim have a city at the location?
			{
				if(entry.getKey().getHexLoc() == params.getLocation() && entry.getValue().getIndex() == params.getVictimIndex())
			    {
			    	return true; //victim has a city at the location
			    }
			}
		}
		
		return false;
	}
	
	/**
	 * checks if the specified location on the map is available for that person to build on
	 * @param params
	 * @return
	 */
	public boolean canBuildRoad(BuildRoad_Input params)
	{
		boolean can = false;
		if(roads.containsKey(params.getRoadLocation())) //already a road here man
		{
			return false;
		}
		else if(!roadIsOnMap(params.getRoadLocation()))
		{
			return false;
		}
		
		for (Entry<EdgeLocation, Player> entry : roads.entrySet()) //loop through the roads
		{
			if(entry.getValue().getIndex() == params.getPlayerIndex()) //this road belongs to the player
		    {
		    	if(params.getRoadLocation().areNeighbors(entry.getKey())) //is this road connected to the road the player wants to build?
		    	{
		    		can = true;
		    	}
		    }
		}
		return can;
	}
	
	/**
	 * checks if the specified location on the map is available 
	 * @param params
	 * @return
	 */
	public boolean canBuildFreeRoad(BuildRoad_Input params)
	{
		if(!roadIsOnMap(params.getRoadLocation()))
		{
			return false;
		}
		return !roads.containsKey(params.getRoadLocation()); //is there already a road built here?
	}
	
	/**
	 * 
	 * checks whether a city or settlement is on the given location
	 * @param v
	 * @return true if location is unused, else false
	 */
	public boolean isVertexLocationAvailable(VertexLocation v)
	{
		if(settlements.containsKey(v.getNormalizedLocation())) //already a settlement here
		{
			return false;
		}
		else if(cities.containsKey(v.getNormalizedLocation())) //already a city here
		{
			return false;
		}
		else if(!vertexIsOnMap(v))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * 
	 * checks whether a city or settlement is on the given location
	 * @param v
	 * @return true if location is unused, else false
	 */
	public boolean hasRoadAtLocation(EdgeLocation e, int playerIndex)
	{
		e = e.getNormalizedLocation();
		if(roads.containsKey(e)) //does this road exist?
		{
			return (roads.get(e).getIndex() == playerIndex); //does this player own this road?
		}
		return false;
	}
	
	/**
	 * checks if the specified location on the map is available for that person to build a settlement on
	 * @param params 
	 * @param params
	 * @return
	 */
	public boolean canBuildSettlement(BuildSettlement_Input params) //todo
	{
		if(!isVertexLocationAvailable(params.getVertexLocation())) //make sure this location is free
		{
			return false;
		}
		
		HexLocation h = params.getVertexLocation().getNormalizedLocation().getHexLoc(); //hex location where we want to build
		VertexDirection d = params.getVertexLocation().getNormalizedLocation().getDir(); //vertex direction where we want to build
		int i = params.getPlayerIndex(); //index of player that wants to build
		
		if(d == VertexDirection.NorthWest)
		{
			//make sure no other buildings are too close
			if(!isVertexLocationAvailable(new VertexLocation(h,VertexDirection.NorthEast))) //location to east is free
			{
				return false;
			}
			else if(!isVertexLocationAvailable(new VertexLocation(h.getNeighborLoc(EdgeDirection.NorthWest),VertexDirection.NorthEast))) //location to northwest is free
			{
				return false;
			}
			else if(!isVertexLocationAvailable(new VertexLocation(h.getNeighborLoc(EdgeDirection.SouthWest),VertexDirection.NorthEast))) //location to southwest is free
			{
				return false;
			}
			
			//check if user has a road connecting to this location
			if(hasRoadAtLocation(new EdgeLocation(h, EdgeDirection.North), i))
			{
				return true;
			}
			else if(hasRoadAtLocation(new EdgeLocation(h, EdgeDirection.NorthWest), i))
			{
				return true;
			}
			else if(hasRoadAtLocation(new EdgeLocation(h.getNeighborLoc(EdgeDirection.NorthWest), EdgeDirection.NorthEast), i))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else //if(d == VertexDirection.NorthEast)
		{
			//make sure no other buildings are too close
			if(!isVertexLocationAvailable(new VertexLocation(h,VertexDirection.NorthWest))) //location to west is free
			{
				return false;
			}
			else if(!isVertexLocationAvailable(new VertexLocation(h.getNeighborLoc(EdgeDirection.NorthEast),VertexDirection.NorthWest))) //location to northeast is free
			{
				return false;
			}
			else if(!isVertexLocationAvailable(new VertexLocation(h.getNeighborLoc(EdgeDirection.SouthEast),VertexDirection.NorthWest))) //location to southeast is free
			{
				return false;
			}
			
			//check if user has a road connecting to this location
			if(hasRoadAtLocation(new EdgeLocation(h, EdgeDirection.North), i))
			{
				return true;
			}
			else if(hasRoadAtLocation(new EdgeLocation(h, EdgeDirection.NorthEast), i))
			{
				return true;
			}
			else if(hasRoadAtLocation(new EdgeLocation(h.getNeighborLoc(EdgeDirection.NorthEast), EdgeDirection.NorthWest), i))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}

	/**
	 * checks if the specified location on the map is available for that person to build a city on
	 * @param params 
	 * @param params
	 * @return
	 */
	public boolean canBuildCity(BuildCity_Input params)
	{
		return (settlements.get(params.getVertexLocation()).getIndex() == params.getPlayerIndex());
	}
	
	/**
	 * @return the hexes
	 */
	public Hex[] getHexes() {
		return hexes;
	}

	/**
	 * @param hexes the hexes to set
	 */
	public void setHexes(Hex[] hexes) {
		this.hexes = hexes;
	}

	/**
	 * @return the ports
	 */
	public Port[] getPorts() {
		return ports;
	}

	/**
	 * @param ports the ports to set
	 */
	public void setPorts(Port[] ports) {
		this.ports = ports;
	}

	/**
	 * @return the roads
	 */
	public HashMap<EdgeLocation, Player> getRoads() {
		return roads;
	}

	/**
	 * @param roads the roads to set
	 */
	public void setRoads(HashMap<EdgeLocation, Player> roads) {
		this.roads = roads;
	}

	/**
	 * @return the settlements
	 */
	public HashMap<VertexLocation, Player> getSettlements() {
		return settlements;
	}

	/**
	 * @param settlements the settlements to set
	 */
	public void setSettlements(HashMap<VertexLocation, Player> settlements) {
		this.settlements = settlements;
	}

	/**
	 * @return the cities
	 */
	public HashMap<VertexLocation, Player> getCities() {
		return cities;
	}

	/**
	 * @param cities the cities to set
	 */
	public void setCities(HashMap<VertexLocation, Player> cities) {
		this.cities = cities;
	}

	/**
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * @return the robber
	 */
	public HexLocation getRobber() {
		return robber;
	}

	/**
	 * @param robber the robber to set
	 */
	public void setRobber(HexLocation robber) {
		this.robber = robber;
	}
}
