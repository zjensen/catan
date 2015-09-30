package shared.communication.moves;

import com.google.gson.Gson;

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
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
