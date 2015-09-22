package shared.models;

import shared.locations.VertexLocation;

public class VertexObject {
	
	private final Player owner;
	private final VertexLocation location;

	public VertexObject(Player owner, VertexLocation location) {
		this.owner = owner;
		this.location = location;
	}

	public int getOwner() {
		return owner.getPlayerID();
	}

	public VertexLocation getLocation() {
		return location;
	}

}
