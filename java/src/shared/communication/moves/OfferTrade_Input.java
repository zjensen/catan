package shared.communication.moves;

import shared.models.ResourceCards;

public class OfferTrade_Input 
{
	private final String type = "offerTrade";
	private int playerIndex;
	private ResourceCards offer;
	private int receiver;
	
	public OfferTrade_Input(int playerIndex, ResourceCards offer, int receiver)
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
	public ResourceCards getOffer()
	{
		return offer;
	}

	/**
	 * @param offer the offer to set
	 */
	public void setOffer(ResourceCards offer)
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
	
	public String toJSON()
	{
		return "{\"type\":\"offerTrade\",\"playerIndex\":"+playerIndex+",\"offer\":"+offer.toJsonString()+",\"receiver\":"+receiver+"}";
	}
}
