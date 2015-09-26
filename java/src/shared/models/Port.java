package shared.models;

import shared.locations.HexLocation;

public class Port {

	private String resourceType;
	private HexLocation location;
	private String direction;
	private int ratio;
	
	public Port() {
		this.resourceType = ""; // If it is omitted then it means it is for any resource
		this.location = new HexLocation(0, 0);
		this.direction = "N";
		this.ratio = 2; // ie 2:1		
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

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public HexLocation getLocation() {
		return location;
	}

	public void setLocation(HexLocation location) {
		this.location = location;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public int getRatio() {
		return ratio;
	}

	public void setRatio(int ratio) {
		this.ratio = ratio;
	}
	
}
