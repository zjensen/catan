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
	
	public void roadCost() {
		--this.wood;
		--this.brick;
	}
	
	public void settlementCost() {
		--this.sheep;
		--this.wheat;
		--this.wood;
		--this.brick;
	}
	
	public void cityCost() {
		this.wheat = this.wheat - 2;
		this.ore = this.ore - 3;		
	}
	
	public void devCardCost() {
		--this.sheep;
		--this.wheat;
		--this.ore;
	}
	
	public void discardCards(int sheepLost, int wheatLost, int woodLost, int brickLost, int oreLost) {
		this.sheep = this.sheep - sheepLost;
		this.wheat = this.wheat - wheatLost;
		this.wood = this.wood - woodLost;
		this.brick = this.brick - brickLost;
		this.ore = this.ore - oreLost;
	}
	
	//not sure about this one
	public void stolenCard(String type) {
		
	}

	public int getSheep() {
		return sheep;
	}

	public int getWheat() {
		return wheat;
	}

	public int getWood() {
		return wood;
	}

	public int getBrick() {
		return brick;
	}

	public int getOre() {
		return ore;
	}
	
	
}
