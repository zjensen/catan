package shared.models;

import java.util.ArrayList;
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
	
	/**
	 * check if player has a building at a port with these parameters
	 * @param params
	 * @return
	 */
	public boolean canMaritimeTrade(MaritimeTrade_Input params)
	{
		ArrayList<VertexLocation> buildings = new ArrayList<VertexLocation>(); //list of this player's buildings
		
		for (Entry<VertexLocation, Player> entry : settlements.entrySet()) //find this person's settlements
		{
		    if(entry.getValue().getIndex() == params.getPlayerIndex()) //settlement belongs to player
		    {
		    	buildings.add(entry.getKey().getNormalizedLocation());
		    }
		}
		for (Entry<VertexLocation, Player> entry : cities.entrySet()) //find this person's cities
		{
		    if(entry.getValue().getIndex() == params.getPlayerIndex()) //city belongs to player
		    {
		    	buildings.add(entry.getKey().getNormalizedLocation());
		    }
		}
		
		if(buildings.isEmpty()) //should never occur
		{
			return false;
		}
		
		return onPort(params, buildings);
	}
	
	/**
	 * Determines if a player has a building on the type of port they are trying to make a trade through
	 * @param params
	 * @param buildings - all of a users buildings
	 * @return
	 */
	public boolean onPort(MaritimeTrade_Input params, ArrayList<VertexLocation> buildings)
	{
		//get list of all edges this user has buildings on
		ArrayList<EdgeLocation> edges = new ArrayList<EdgeLocation>();
		for(VertexLocation v : buildings)
		{
			VertexDirection d = v.getDir();
			HexLocation h = v.getHexLoc();
			
			if(d == VertexDirection.NorthWest)
			{	
				edges.add(new EdgeLocation(h, EdgeDirection.North));
				edges.add(new EdgeLocation(h, EdgeDirection.NorthWest));
				edges.add(new EdgeLocation(h.getNeighborLoc(EdgeDirection.NorthWest), EdgeDirection.NorthEast));
			}
			else //if(d == VertexDirection.NorthEast)
			{			
				edges.add(new EdgeLocation(h, EdgeDirection.North));
				edges.add(new EdgeLocation(h, EdgeDirection.NorthEast));
				edges.add(new EdgeLocation(h.getNeighborLoc(EdgeDirection.NorthEast), EdgeDirection.NorthWest));
			}
		}

		for(Port p : ports)
		{
			EdgeLocation pe = new EdgeLocation(p.getLocation(), p.getDirection()); //the ports edge
			for(EdgeLocation e : edges)
			{
				if(e.equals(pe)) //one of the edges the player has a building on IS A PORT
				{
					if(p.getRatio() == params.getRatio() && p.getResourceType() == params.getInputResource())
					{
						//this is the type of port the player is attempting to use
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * determines if hex is an ocean hex or not
	 * @param h
	 * @return
	 */
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
		for (Entry<VertexLocation, Player> entry : settlements.entrySet()) //loop through each settlement
		{
			if(entry.getValue().getIndex() == params.getVictimIndex()) //does the victim have a settlement at the location?
			{
				if(isOnHex(entry.getKey(), params.getLocation()))
				{
					return true; //settlement is on the location
				}
			}
		}
		for (Entry<VertexLocation, Player> entry : cities.entrySet()) //loop through each city
		{
			if(entry.getValue().getIndex() == params.getVictimIndex()) //does the victim have a city at the location?
			{
				if(isOnHex(entry.getKey(), params.getLocation()))
				{
					return true; //city is on the location
				}
			}
		}
		
		return false;
	}
	
	public boolean canRoadBuilding(RoadBuilding_Input params)
	{
		EdgeLocation r1 = params.getSpot1().getNormalizedLocation();
		EdgeLocation r2 = params.getSpot2().getNormalizedLocation();
		
		if(r1 != null)
		{
			if(!roads.containsKey(r1) || !roadIsOnMap(r1))
			{
				return false;
			}
		}
		
		if(r2 != null)
		{
			if(!roads.containsKey(r2) || !roadIsOnMap(r2))
			{
				return false;
			}
		}
		
		return true;
	}
	
	public boolean canSoldier(Soldier_Input params)
	{
		if(params.getLocation() == robber) //the location is not the same as the previous
		{
			return false;
		}
		else
		{
			for (Entry<VertexLocation, Player> entry : settlements.entrySet()) //loop through each settlement
			{
				if(entry.getValue().getIndex() == params.getVictimIndex()) //does the victim have a settlement at the location?
				{
					if(isOnHex(entry.getKey(), params.getLocation()))
					{
						return true; //settlement is on the location
					}
				}
			}
			for (Entry<VertexLocation, Player> entry : cities.entrySet()) //loop through each city
			{
				if(entry.getValue().getIndex() == params.getVictimIndex()) //does the victim have a city at the location?
				{
					if(isOnHex(entry.getKey(), params.getLocation()))
					{
						return true; //city is on the location
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Checks if a vertex location is on a hex
	 * each vertex location touches 3 hexes
	 * @param v
	 * @param h
	 * @return
	 */
	public boolean isOnHex(VertexLocation v,HexLocation h)
	{
		v = v.getNormalizedLocation();
		HexLocation vh = v.getHexLoc();
		VertexDirection vd= v.getDir();
		
		if(vh == h) //building is at top of this hex
		{
			return true;
		}
		else if(vh.getNeighborLoc(EdgeDirection.North) == h) //building is at bottom of the hex
		{
			return true;
		}
		else if(vd == VertexDirection.NorthWest) //building is on east corner of the hex
		{
			return (vh.getNeighborLoc(EdgeDirection.NorthWest) == h);
		}
		else if(vd == VertexDirection.NorthEast) //building is on west corner of the hex
		{
			return (vh.getNeighborLoc(EdgeDirection.NorthEast) == h);
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
