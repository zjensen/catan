package shared.models;

public class Player {
	
	private String name;
	private String color;
	private int playerID;
	private int index; //In the JSON says it's an "Index" data type?
	private boolean discarded;
	private boolean playedDevCard;
	private DevCardList newDevCards;
	private DevCardList oldDevCards;
	private ResourceList resources;
	private int roads;
	private int settlements;
	private int cities;
	private int soldiers;
	private int monuments;
	private int longestRoad;
	private int victoryPoints;
	
	public Player() {
		this.name = null;
		this.color = null;
		this.playerID = 0;
		this.index = 0;
		this.discarded = false;
		this.playedDevCard = false;
		this.newDevCards = new DevCardList();
		this.oldDevCards = new DevCardList();
		this.resources = new ResourceList();
		this.roads = 15;
		this.settlements = 5;
		this.cities = 4;
		this.soldiers = 0;
		this.monuments = 0;
		this.longestRoad = 0;
		this.victoryPoints = 0;
	}
	
	public void buildRoad() { //could be a bool function
		
	}
	
	public void buildSettlement() { //could be a bool function
		
	}
	
	public void buildCity() { //could be a bool function
		
	}
	
	public void buyDevCard() { //could be a bool function
		
	}
	
	public void useDevCard() { //could be a bool function
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isDiscarded() {
		return discarded;
	}

	public void setDiscarded(boolean discarded) {
		this.discarded = discarded;
	}

	public boolean isPlayedDevCard() {
		return playedDevCard;
	}

	public void setPlayedDevCard(boolean playedDevCard) {
		this.playedDevCard = playedDevCard;
	}

	public DevCardList getNewDevCards() {
		return newDevCards;
	}

	public void setNewDevCards(DevCardList newDevCards) {
		this.newDevCards = newDevCards;
	}

	public DevCardList getOldDevCards() {
		return oldDevCards;
	}

	public void setOldDevCards(DevCardList oldDevCards) {
		this.oldDevCards = oldDevCards;
	}

	public ResourceList getResources() {
		return resources;
	}

	public void setResources(ResourceList resources) {
		this.resources = resources;
	}

	public int getRoads() {
		return roads;
	}

	public void setRoads(int roads) {
		this.roads = roads;
	}

	public int getSettlements() {
		return settlements;
	}

	public void setSettlements(int settlements) {
		this.settlements = settlements;
	}

	public int getCities() {
		return cities;
	}

	public void setCities(int cities) {
		this.cities = cities;
	}

	public int getSoldiers() {
		return soldiers;
	}

	public void setSoldiers(int soldiers) {
		this.soldiers = soldiers;
	}

	public int getMonuments() {
		return monuments;
	}

	public void setMonuments(int monuments) {
		this.monuments = monuments;
	}

	public int getLongestRoad() {
		return longestRoad;
	}

	public void setLongestRoad(int longestRoad) {
		this.longestRoad = longestRoad;
	}

	public int getVictoryPoints() {
		return victoryPoints;
	}

	public void setVictoryPoints(int victoryPoints) {
		this.victoryPoints = victoryPoints;
	}
	
	
}
