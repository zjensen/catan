package shared.models;

import shared.locations.HexLocation;

import shared.definitions.HexType;;

public class Hex 
{
	private HexLocation location; //location on map of this hex
	private HexType resource;
	private int number;
	
	/**
	 * Constructs a non-desert Hex
	 * @param location
	 * @param resource
	 * @param number
	 */
	public Hex(HexLocation location, HexType resource, int number) 
	{
		super();
		this.location = location;
		this.resource = resource;
		this.number = number;
	}
	
	/**
	 * constructs a desert hex
	 * @param location
	 */
	public Hex(HexLocation location) //for generating desert hex
	{
		super();
		this.location = location;
		resource = null;
		number = -1;
	}
	
	
	
	
	
	@Override
	public String toString() {
		return "Hex [" 
				+ "location = " + location 
				+ "  resource = " + resource 
				+ "  number = " + number + "\t]";
	}

	/**
	 * checks if this hex is a desert
	 * @return true if the hex is a desert, else false
	 */
	public boolean isDesert()
	{
		return (resource == HexType.DESERT);
	}

	/**
	 * @return the location
	 */
	public HexLocation getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(HexLocation location) {
		this.location = location;
	}

	/**
	 * @return the resource
	 */
	public HexType getResource() {
		return resource;
	}

	/**
	 * @param resource the resource to set
	 */
	public void setResource(HexType resource) {
		this.resource = resource;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}
	
	
}
