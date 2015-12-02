package shared.communication.moves;

import java.io.Serializable;

import com.google.gson.Gson;

public class SendChat_Input implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8109207815789113775L;
	private final String type = "sendChat";
	private int playerIndex;
	private String content;
	
	public SendChat_Input(int playerIndex, String content)
	{
		super();
		this.playerIndex = playerIndex;
		this.content = content;
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
	 * @return the content
	 */
	public String getContent()
	{
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content)
	{
		this.content = content;
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
