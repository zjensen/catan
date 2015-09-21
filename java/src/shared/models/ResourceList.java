package shared.models;

public class ResourceList {

	private int sheep;
	private int wheat;
	private int wood;
	private int brick;
	private int ore;
	
	public ResourceList() {
		this.sheep = 0;
		this.wheat = 0;
		this.wood = 0;
		this.brick = 0;
		this.ore = 0;
	}
	
	/**
	 * 
	 * @param cardTotals
	 * 
	 * This function will be called to update the ResourceCardList object.
	 * The cardTotals is an array containing a number for each of the card types held in the ResourceCardList
	 * With each number held in cardTotals, it will correspond to a specific card type in order:
	 * sheep
	 * wheat
	 * wood
	 * brick
	 * ore
	 * 
	 */
	public void updateResources(int[] cardTotals) {
		
	}

	public int getSheep() {
		return sheep;
	}

	public void setSheep(int sheep) {
		this.sheep = sheep;
	}

	public int getWheat() {
		return wheat;
	}

	public void setWheat(int wheat) {
		this.wheat = wheat;
	}

	public int getWood() {
		return wood;
	}

	public void setWood(int wood) {
		this.wood = wood;
	}

	public int getBrick() {
		return brick;
	}

	public void setBrick(int brick) {
		this.brick = brick;
	}

	public int getOre() {
		return ore;
	}

	public void setOre(int ore) {
		this.ore = ore;
	}
	
}
