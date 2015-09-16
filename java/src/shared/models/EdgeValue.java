package shared.models;

import shared.locations.EdgeLocation;

public class EdgeValue {

	private final EdgeLocation location;
	private final int owner;
	
	public EdgeValue(int playerIndex, EdgeLocation location) {
		this.location = location;
		this.owner = playerIndex;
	}

	public int getOwner() {
		return owner;
	}

	public EdgeLocation getLocation() {
		return location;
	}
	
}
