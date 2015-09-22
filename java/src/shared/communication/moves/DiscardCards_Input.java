package shared.communication.moves;

import shared.models.ResourceList;

public class DiscardCards_Input 
{
	private final String type = "discardCards";
	private int playerIndex;
	private ResourceList discardedCards;
	
	public DiscardCards_Input(int playerIndex, ResourceList discardedCards)
	{
		super();
		this.playerIndex = playerIndex;
		this.discardedCards = discardedCards;
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
	 * @return the discardedCards
	 */
	public ResourceList getDiscardedCards()
	{
		return discardedCards;
	}

	/**
	 * @param discardedCards the discardedCards to set
	 */
	public void setDiscardedCards(ResourceList discardedCards)
	{
		this.discardedCards = discardedCards;
	}

	/**
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}
	
}