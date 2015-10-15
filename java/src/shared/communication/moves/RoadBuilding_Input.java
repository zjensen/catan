package shared.communication.moves;

import shared.locations.EdgeLocation;

public class RoadBuilding_Input 
{
	private final String type = "Road_Building";
	private int playerIndex;
	private EdgeLocation spot1;
	private EdgeLocation spot2;
	
	public RoadBuilding_Input(int playerIndex, EdgeLocation spot1, EdgeLocation spot2)
	{
		super();
		this.playerIndex = playerIndex;
		this.spot1 = spot1;
		this.spot2 = spot2;
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
	 * @return the spot1
	 */
	public EdgeLocation getSpot1()
	{
		return spot1;
	}

	/**
	 * @param spot1 the spot1 to set
	 */
	public void setSpot1(EdgeLocation spot1)
	{
		this.spot1 = spot1;
	}

	/**
	 * @return the spot2
	 */
	public EdgeLocation getSpot2()
	{
		return spot2;
	}

	/**
	 * @param spot2 the spot2 to set
	 */
	public void setSpot2(EdgeLocation spot2)
	{
		this.spot2 = spot2;
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
		int x1 = spot1.getHexLoc().getX();
		int y1 = spot1.getHexLoc().getY();
		String dir1 = spot1.getNormalizedLocation().toServerFormattedString();
		
		int x2 = spot1.getHexLoc().getX();
		int y2 = spot1.getHexLoc().getY();
		String dir2 = spot1.getNormalizedLocation().toServerFormattedString();
		
		return "{\"type\":\"Road_Building\",\"playerIndex\":"+playerIndex+",\"spot1\":{\"x\":"+x1+",\"y\":"+y1+",\"direction\":\""+dir1+"\"},\"spot2\":{\"x\":"+x2+",\"y\":"+y2+",\"direction\":\""+dir2+"\"}}";
	}
}
