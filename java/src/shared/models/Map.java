package shared.models;

import shared.locations.HexLocation;

public class Map {
	private Hex[] hexes;
	private Port[] ports;
	private EdgeValue[] roads;
	private VertexObject[] settlements;
	private VertexObject[] cities;
	private int radius;
	private HexLocation robber;
	
	public Map(Hex[] hexes, Port[] ports, EdgeValue[] roads,
			VertexObject[] settlements, VertexObject[] cities, int radius,
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
	public EdgeValue[] getRoads() {
		return roads;
	}

	/**
	 * @param roads the roads to set
	 */
	public void setRoads(EdgeValue[] roads) {
		this.roads = roads;
	}

	/**
	 * @return the settlements
	 */
	public VertexObject[] getSettlements() {
		return settlements;
	}

	/**
	 * @param settlements the settlements to set
	 */
	public void setSettlements(VertexObject[] settlements) {
		this.settlements = settlements;
	}

	/**
	 * @return the cities
	 */
	public VertexObject[] getCities() {
		return cities;
	}

	/**
	 * @param cities the cities to set
	 */
	public void setCities(VertexObject[] cities) {
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
