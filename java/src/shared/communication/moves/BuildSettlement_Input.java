package shared.communication.moves;

import java.io.Serializable;

import shared.locations.VertexLocation;

public class BuildSettlement_Input implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4966174067304345085L;
	private final String type = "buildSettlement";
	private int playerIndex;
	private VertexLocation vertexLocation;
	private boolean free;
	
	public BuildSettlement_Input(int playerIndex,VertexLocation vertexLocation, boolean free)
	{
		super();
		this.playerIndex = playerIndex;
		this.vertexLocation = vertexLocation;
		this.free = free;
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
	 * @return the free
	 */
	public boolean isFree()
	{
		return free;
	}

	/**
	 * @param free the free to set
	 */
	public void setFree(boolean free)
	{
		this.free = free;
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
		String s = "{\"type\":\"buildSettlement\",\"playerIndex\":"+playerIndex+",\"vertexLocation\":{\"x\":"+x+",\"y\":"+y+",\"direction\":\""+dir+"\"},\"free\":"+free+"}";
		return s;
	}
}
