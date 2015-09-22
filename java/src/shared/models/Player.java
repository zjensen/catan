package shared.models;

public class Player {
	
	private String name;
	private String color;
	private int playerID;
	private int index; //In the JSON says it's an "Index" data type?
	private boolean discarded;
	private boolean playedDevCard;
	private DevCards newDevCards;
	private DevCards oldDevCards;
	private ResourceCards resources;
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
		this.newDevCards = new DevCards();
		this.oldDevCards = new DevCards();
		this.resources = new ResourceCards();
		this.roads = 15;
		this.settlements = 5;
		this.cities = 4;
		this.soldiers = 0;
		this.monuments = 0;
		this.longestRoad = 0;
		this.victoryPoints = 0;
	}
	
	/**
	 * 
	 * This function will be called on the Player after the ClientModel has received its new version
	 * All the objects in the player will be updated based on the changes received from the client model
	 * Parameters for this function are not yet defined
	 * 
	 */
	public void updatePlayer(/* Params need to be defined */) { //could be a bool function
		
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

	public DevCards getNewDevCards() {
		return newDevCards;
	}

	public void setNewDevCards(DevCards newDevCards) {
		this.newDevCards = newDevCards;
	}

	public DevCards getOldDevCards() {
		return oldDevCards;
	}

	public void setOldDevCards(DevCards oldDevCards) {
		this.oldDevCards = oldDevCards;
	}

	public ResourceCards getResources() {
		return resources;
	}

	public void setResources(ResourceCards resources) {
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
