package shared.communication.moves;

import java.io.Serializable;

import shared.definitions.ResourceType;

public class MaritimeTrade_Input implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -527491220154377042L;
	private final String type = "maritimeTrade";
	private int playerIndex;
	private int ratio;
	private ResourceType inputResource;
	private ResourceType outputResource;
	
	public MaritimeTrade_Input(int playerIndex, int ratio, ResourceType inputResource, ResourceType outputResource)
	{
		super();
		this.playerIndex = playerIndex;
		this.ratio = ratio;
		this.inputResource = inputResource;
		this.outputResource = outputResource;
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
	 * @return the ratio
	 */
	public int getRatio()
	{
		return ratio;
	}

	/**
	 * @param ratio the ratio to set
	 */
	public void setRatio(int ratio)
	{
		this.ratio = ratio;
	}

	/**
	 * @return the inputResource
	 */
	public ResourceType getInputResource()
	{
		return inputResource;
	}

	/**
	 * @param inputResource the inputResource to set
	 */
	public void setInputResource(ResourceType inputResource)
	{
		this.inputResource = inputResource;
	}

	/**
	 * @return the outputResource
	 */
	public ResourceType getOutputResource()
	{
		return outputResource;
	}

	/**
	 * @param outputResource the outputResource to set
	 */
	public void setOutputResource(ResourceType outputResource)
	{
		this.outputResource = outputResource;
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
		return "{\"type\":\"maritimeTrade\",\"playerIndex\":"+playerIndex+",\"ratio\":"+ratio+",\"inputResource\":\""+inputResource.toString().toLowerCase()+"\",\"outputResource\":\""+outputResource.toString().toLowerCase()+"\"}";
	}
}
