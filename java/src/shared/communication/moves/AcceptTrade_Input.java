package shared.communication.moves;

import java.io.Serializable;

import com.google.gson.Gson;

public class AcceptTrade_Input implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9043763126228271506L;
	private final String type = "acceptTrade";
	private int playerIndex;
	private boolean willAccept;
	
	public AcceptTrade_Input(int playerIndex, boolean willAccept)
	{
		super();
		this.playerIndex = playerIndex;
		this.willAccept = willAccept;
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
	 * @return the willAccept
	 */
	public boolean isWillAccept()
	{
		return willAccept;
	}

	/**
	 * @param willAccept the willAccept to set
	 */
	public void setWillAccept(boolean willAccept)
	{
		this.willAccept = willAccept;
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
