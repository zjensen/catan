package shared.models;

import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.HexLocation;

public class Port {

	private ResourceType resourceType;
	private HexLocation location;
	private EdgeDirection direction;
	private int ratio;
	
	public Port(ResourceType resourceType, HexLocation location, EdgeDirection direction, int ratio)
	{
		super();
		this.resourceType = resourceType;
		this.location = location;
		this.direction = direction;
		this.ratio = ratio;	
	}
	
	public Port(ResourceType resourceType, HexLocation location, EdgeDirection direction)
	{
		super();
		this.resourceType = resourceType;
		this.location = location;
		this.direction = direction;
		this.ratio = 2;	
	}
	
	
	public Port(HexLocation location, EdgeDirection direction, int ratio)
	{
		super();
		this.resourceType = null;
		this.location = location;
		this.direction = direction;
		this.ratio = ratio;
	}

	/**
	 * check to see what ratio the player's port has
	 * @return ratio
	 */
	public int usePort() { // could be used when a player needs to trade resources
		
		return ratio; // or something else. 
	}

	/**
	 *
	 * check to see if a port is usable by giving us the distance from the settlement
	 * returns int of how far it is from the settlement
	 * @return
	 */
	public int distanceFromSettlement() { // lets us know if the port can be used by anybody
		return 0;
	}

	/**
	 * @return the resourceType
	 */
	public ResourceType getResourceType()
	{
		return resourceType;
	}

	/**
	 * @param resourceType the resourceType to set
	 */
	public void setResourceType(ResourceType resourceType)
	{
		this.resourceType = resourceType;
	}

	@Override
	public String toString() {
		return "Port [resource=" + resourceType 
				+ ", location=" + location 
				+ ", direction=" + direction 
				+ ", ratio=" + ratio + "   ]";
	}


	/**
	 * @return the location
	 */
	public HexLocation getLocation()
	{
		return location;
	}
	
	public void SetLocation(HexLocation location)
	{
		this.location = location;
	}


	/**
	 * @return the direction
	 */
	public EdgeDirection getDirection()
	{
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(EdgeDirection direction)
	{
		this.direction = direction;
	}

	/**
	 * @return the ratio
	 */
	public int getRatio()
	{
		return ratio;
	}

	/**
	 * @param ratio the ratio to set
	 */
	public void setRatio(int ratio)
	{
		this.ratio = ratio;
	}
}
