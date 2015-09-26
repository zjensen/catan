package shared.communication.moves;

public class SendChat_Input 
{
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
}
