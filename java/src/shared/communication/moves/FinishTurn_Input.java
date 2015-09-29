package shared.communication.moves;

import com.google.gson.Gson;

public class FinishTurn_Input 
{
	private final String type = "finishTurn";
	private int playerIndex;
	
	public FinishTurn_Input(int playerIndex)
	{
		super();
		this.playerIndex = playerIndex;
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
