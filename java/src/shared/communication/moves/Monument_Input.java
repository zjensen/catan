package shared.communication.moves;


public class Monument_Input 
{
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
	
	
}
