package shared.communication.moves;

import java.io.Serializable;

import com.google.gson.Gson;

public class Monument_Input implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7555688633806781206L;
	private final String type = "Monument";
	private int playerIndex;
	
	public Monument_Input(int playerIndex)
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
