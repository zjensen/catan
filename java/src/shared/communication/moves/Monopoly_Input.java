package shared.communication.moves;

import java.io.Serializable;

import shared.definitions.ResourceType;

public class Monopoly_Input implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3010726303675176803L;
	private final String type = "Monopoly";
	private int playerIndex;
	private ResourceType resource;
	
	public Monopoly_Input(int playerIndex, ResourceType resource)
	{
		super();
		this.playerIndex = playerIndex;
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
		return "{\"type\":\"Monopoly\",\"resource\":\""+resource.toString().toLowerCase()+"\",\"playerIndex\":"+playerIndex+"}";
	}
}
