package shared.models;

import shared.definitions.HexType;
import shared.locations.EdgeDirection;
import shared.locations.HexLocation;

public class Port {

	private HexType resource;
	private HexLocation location;
	private EdgeDirection direction;
	private int ratio;
	
	public Port(HexType hexType, HexLocation hexLoc, EdgeDirection dir, int ratio) {
		setResource(hexType);
		setLocation(hexLoc);
		setDirection(dir);
		setRatio(ratio);		
	}
	
	public Port(HexLocation hexLoc, EdgeDirection dir, int ratio) {
		setLocation(hexLoc);
		setDirection(dir);
		setRatio(ratio);
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


	@Override
	public String toString() {
		return "Port [resource=" + resource 
				+ ", location=" + location 
				+ ", direction=" + direction 
				+ ", ratio=" + ratio + "   ]";
	}

	public HexType getResource() {
		return resource;
	}

	public void setResource(HexType resource) {
		this.resource = resource;
	}

	public HexLocation getLocation() {
		return location;
	}
 
	public void setLocation(HexLocation location) {
		this.location = location;
	}

	public EdgeDirection getDirection() {
		return direction;
	}

	public void setDirection(EdgeDirection direction) {
		this.direction = direction;
	}

	public int getRatio() {
		return ratio;
	}

	public void setRatio(int ratio) {
		this.ratio = ratio;
	}

	
	
}
