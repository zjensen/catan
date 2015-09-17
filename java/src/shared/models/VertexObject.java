package shared.models;

import shared.locations.VertexLocation;

public class VertexObject {
	
	private final int owner;
	private final VertexLocation location;

	public VertexObject(int owner, VertexLocation location) {
		this.owner = owner;
		this.location = location;
	}

	public int getOwner() {
		return owner;
	}

	public VertexLocation getLocation() {
		return location;
	}

}
