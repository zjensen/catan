package shared.communication.moves;

import java.io.Serializable;

import shared.locations.EdgeLocation;

public class BuildRoad_Input implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7151841449808130320L;
	private final String type = "buildRoad";
	private int playerIndex;
	private EdgeLocation roadLocation;
	private boolean free;
	
	public BuildRoad_Input(int playerIndex, EdgeLocation roadLocation,boolean free)
	{
		super();
		this.playerIndex = playerIndex;
		this.roadLocation = roadLocation;
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
	 * @return the roadLocation
	 */
	public EdgeLocation getRoadLocation()
	{
		return roadLocation;
	}

	/**
	 * @param roadLocation the roadLocation to set
	 */
	public void setRoadLocation(EdgeLocation roadLocation)
	{
		this.roadLocation = roadLocation;
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
	
//	{"type":"buildRoad","playerIndex":0,"roadLocation":{"hexLoc":{"x":2,"y":0},"dir":"NorthWest"},"free":true}
	
//	{"type":"buildRoad","playerIndex":0,"roadLocation":{"x":0,"y":3,"direction":"N"},"free":true}
	public String toJSON()
	{
		int x = roadLocation.getHexLoc().getX();
		int y = roadLocation.getHexLoc().getY();
		String dir = roadLocation.toServerFormattedString();
		String s = "{\"type\":\"buildRoad\",\"playerIndex\":"+playerIndex+",\"roadLocation\":{\"x\":"+x+",\"y\":"+y+",\"direction\":\""+dir+"\"},\"free\":"+free+"}";
		return s;
	}
}
