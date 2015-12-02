package shared.communication.moves;

import java.io.Serializable;

import shared.definitions.ResourceType;

public class YearOfPlenty_Input implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5989252473325469003L;
	private final String type = "Year_Of_Plenty";
	private int playerIndex;
	private ResourceType resource1;
	private ResourceType resource;
	
	public YearOfPlenty_Input(int playerIndex, ResourceType resource1,ResourceType resource)
	{
		super();
		this.playerIndex = playerIndex;
		this.resource1 = resource1;
		this.resource = resource;
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
	 * @return the resource1
	 */
	public ResourceType getResource1()
	{
		return resource1;
	}

	/**
	 * @param resource1 the resource1 to set
	 */
	public void setResource1(ResourceType resource1)
	{
		this.resource1 = resource1;
	}

	/**
	 * @return the resource
	 */
	public ResourceType getResource()
	{
		return resource;
	}

	/**
	 * @param resource the resource to set
	 */
	public void setResource(ResourceType resource)
	{
		this.resource = resource;
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
		return "{\"type\":\"Year_of_Plenty\",\"playerIndex\":"+playerIndex+",\"resource1\":\""+resource1.toString().toLowerCase()+"\",\"resource2\":\""+resource.toString().toLowerCase()+"\"}";
	}
}
