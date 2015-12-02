package shared.communication.moves;

import java.io.Serializable;

import com.google.gson.Gson;

public class BuyDevCard_Input implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4387513443085187989L;
	private final String type = "buyDevCard";
	private int playerIndex;
	public BuyDevCard_Input(int playerIndex)
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
