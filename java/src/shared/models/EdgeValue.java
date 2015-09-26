package shared.models;

import shared.locations.EdgeLocation;

public class EdgeValue {

	private final EdgeLocation location;
	private final Player owner;
	
	public EdgeValue(Player playerIndex, EdgeLocation location) {
		this.location = location;
		this.owner = playerIndex;
	}

	public int getOwner() {
		return owner.getPlayerID();
	}

	public EdgeLocation getLocation() {
		return location;
	}
	
}
