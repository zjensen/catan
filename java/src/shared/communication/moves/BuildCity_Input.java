package shared.communication.moves;

import java.io.Serializable;

import shared.locations.VertexLocation;

public class BuildCity_Input implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4534690491682673624L;
	private final String type = "buildCity";
	private int playerIndex;
	private VertexLocation vertexLocation;
	
	public BuildCity_Input(int playerIndex, VertexLocation vertexLocation)
	{
		super();
		this.playerIndex = playerIndex;
		this.vertexLocation = vertexLocation;
	}

	/**
	 * @return the playerIndex
	 */
	public int getPlayerIndex()
	{
		return playerIndex;
	}

	/**
	 * @param playerIndex the playerIndex to set
	 */
	public void setPlayerIndex(int playerIndex)
	{
		this.playerIndex = playerIndex;
	}

	/**
	 * @return the vertexLocation
	 */
	public VertexLocation getVertexLocation()
	{
		return vertexLocation;
	}

	/**
	 * @param vertexLocation the vertexLocation to set
	 */
	public void setVertexLocation(VertexLocation vertexLocation)
	{
		this.vertexLocation = vertexLocation;
	}

	/**
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}
	
	public String toJSON()
	{
		int x = vertexLocation.getNormalizedLocation().getHexLoc().getX();
		int y = vertexLocation.getNormalizedLocation().getHexLoc().getY();
		String dir = vertexLocation.getNormalizedLocation().toServerFormattedString();
		String s = "{\"type\":\"buildCity\",\"playerIndex\":"+playerIndex+",\"vertexLocation\":{\"x\":"+x+",\"y\":"+y+",\"direction\":\""+dir+"\"}}";
		return s;
	}
}
