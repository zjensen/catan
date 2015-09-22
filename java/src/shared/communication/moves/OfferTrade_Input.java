package shared.communication.moves;

import shared.models.ResourceList;

public class OfferTrade_Input 
{
	private final String type = "offerTrade";
	private int playerIndex;
	private ResourceList offer;
	private int receiver;
	
	public OfferTrade_Input(int playerIndex, ResourceList offer, int receiver)
	{
		super();
		this.playerIndex = playerIndex;
		this.offer = offer;
		this.receiver = receiver;
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
	 * @return the offer
	 */
	public ResourceList getOffer()
	{
		return offer;
	}

	/**
	 * @param offer the offer to set
	 */
	public void setOffer(ResourceList offer)
	{
		this.offer = offer;
	}

	/**
	 * @return the receiver
	 */
	public int getReceiver()
	{
		return receiver;
	}

	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(int receiver)
	{
		this.receiver = receiver;
	}

	/**
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}
	
	
}
