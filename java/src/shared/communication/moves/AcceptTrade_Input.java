package shared.communication.moves;

public class AcceptTrade_Input 
{
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
	
	
	
}
