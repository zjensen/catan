package shared.models;

public class DevCards {
	
	private int soldier;
	private int monopoly;
	private int roadBuilding;
	private int yearOfPlenty;
	private int monument;
	
	public DevCards() {
		this.soldier = 0;
		this.monopoly = 0;
		this.roadBuilding = 0;
		this.yearOfPlenty = 0;
		this.monument = 0;
	}

	/**
	 * 
	 * @param cardTotals
	 * 
	 * This function will be called to update the DevCardList object.
	 * The cardTotals is an array containing a number for each of the card types held in the DevCardList
	 * With each number held in cardTotals, it will correspond to a specific card type in order:
	 * soldier
	 * monopoly
	 * roadBuilding
	 * yearOfPlenty
	 * monument
	 * 
	 */
	public void updateCards(int[] cardTotals) {
		
	}
	
	
	
	
	
	@Override
	public String toString() {
		return "DevCards [" 
				+ "\n\tsoldier      = " + soldier 
				+ "\n\tmonopoly     = " + monopoly 
				+ "\n\troadBuilding = " + roadBuilding
				+ "\n\tyearOfPlenty = " + yearOfPlenty 
				+ "\n\tmonument     = " + monument + "  ]";
	}

	public int getSoldier() {
		return soldier;
	}

	public void setSoldier(int soldier) {
		this.soldier = soldier;
	}

	public int getMonopoly() {
		return monopoly;
	}

	public void setMonopoly(int monopoly) {
		this.monopoly = monopoly;
	}

	public int getRoadBuilding() {
		return roadBuilding;
	}

	public void setRoadBuilding(int roadBuilding) {
		this.roadBuilding = roadBuilding;
	}

	public int getYearOfPlenty() {
		return yearOfPlenty;
	}

	public void setYearOfPlenty(int yearOfPlenty) {
		this.yearOfPlenty = yearOfPlenty;
	}

	public int getMonument() {
		return monument;
	}

	public void setMonument(int monument) {
		this.monument = monument;
	}
	
	public int cardCount() {
		return (this.monopoly + this.monument + this.roadBuilding + this.soldier + this.yearOfPlenty);
	}

	public String toJsonString()
	{
		return "{\"yearOfPlenty\":"+yearOfPlenty+",\"monopoly\":"+monopoly+",\"soldier\":"+soldier+",\"roadBuilding\":"+roadBuilding+",\"monument\":"+monument+"}";
	}
	
}
