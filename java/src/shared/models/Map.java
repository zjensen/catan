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
		if(roads.containsKey(params.getRoadLocation())) //already a road here man
		{
			return false;
		}
		for (Entry<EdgeLocation, Player> entry : roads.entrySet()) //loop through the roads
		{
			if(entry.getValue().getIndex() == params.getPlayerIndex()) //this road belongs to the player
		    {
		    	if(params.getRoadLocation().areNeighbors(entry.getKey())) //is this road connected to the road the player wants to build?
		    	{
		    		return true;
		    	}
		    }
		}
		return false;
	}
	
	/**
	 * checks if the specified location on the map is available 
	 * @param params
	 * @return
	 */
	public boolean canBuildFreeRoad(BuildRoad_Input params)
	{
		return !roads.containsKey(params.getRoadLocation()); //is there already a road built here?
	}
	
	/**
	 * checks if the specified location on the map is available for that person to build a settlement on
	 * @param params 
	 * @param params
	 * @return
	 */
	public boolean canBuildSettlement(BuildSettlement_Input params) //todo
	{
		return false;
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
