package shared.models;

import shared.communication.moves.DiscardCards_Input;
import shared.communication.moves.MaritimeTrade_Input;
import shared.communication.moves.RoadBuilding_Input;

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

	/**
	 * @param resources
	 * @return true if player has at least the given resources
	 */
	public boolean hasResources(ResourceCards r)
	{
		return this.resources.hasResources(r);
	}
	
	/**
	 * checks if player has cards to offer
	 * In an offer all negative ints represent how many the player is planning to give up
	 * @param offer
	 * @return
	 */
	public boolean canOfferCards(ResourceCards offer)
	{
		//checks if the player is offering a resource and if so whether they have enough of that resource to offer
		if(offer.getBrick() < 0 && Math.abs(offer.getBrick()) > resources.getBrick())
			return false;
		else if (offer.getOre() < 0 && Math.abs(offer.getOre()) > resources.getOre())
			return false;
		else if (offer.getSheep() < 0 && Math.abs(offer.getSheep()) > resources.getSheep())
			return false;
		else if (offer.getWheat() < 0 && Math.abs(offer.getWheat()) > resources.getWheat())
			return false;
		else if (offer.getWood() < 0 && Math.abs(offer.getWood()) > resources.getWood())
			return false;
		return true;
	}
	
	/**
	 * verifies player has enough resources to trade
	 * @param params
	 * @return
	 */
	public boolean canMaritimeTrade(MaritimeTrade_Input params)
	{
		switch(params.getInputResource())
		{
			case BRICK:
				return resources.getBrick() >= params.getRatio();
			case ORE:
				return resources.getOre() >= params.getRatio();
			case SHEEP:
				return resources.getSheep() >= params.getRatio();
			case WHEAT:
				return resources.getWheat() >= params.getRatio();
			case WOOD:
				return resources.getWood() >= params.getRatio();
			default:
				return false;
		}
	}
	
	/**
	 * determines if player has the resources to buy a dev card
	 * @return
	 */
	public boolean canBuyDevCard()
	{
		//true if player has at least 1 sheep, 1 ore, and 1 wheat
		return ((resources.getSheep() > 0 && resources.getOre()>0) && resources.getWheat() > 0);
	}
	
	public boolean canYearOfPlenty()
	{
		return (oldDevCards.getYearOfPlenty() > 0 && canPlayDevCard());
	}
	
	public boolean canMonument()
	{
		boolean gameChanger = (victoryPoints + oldDevCards.getMonument() + newDevCards.getMonument() >= 10);
		return ((oldDevCards.getMonument() > 0 || newDevCards.getMonument() > 0) && (gameChanger || canPlayDevCard()));
	}
	
	public boolean canMonopoly()
	{
		return (oldDevCards.getMonopoly() > 0 && canPlayDevCard());
	}
	
	public boolean canSoldier()
	{
		return (oldDevCards.getSoldier() > 0 && canPlayDevCard());
	}
	
	public boolean canRoadBuilding(RoadBuilding_Input params) 
	{
		if(oldDevCards.getRoadBuilding() < 1 || !canPlayDevCard())
		{
			return false;
		}
		
		if(params.getSpot1() != null && params.getSpot2() !=null)
		{
			return roads >= 2; //does this player have 2 roads to play
		}
		else if(params.getSpot1() == null || params.getSpot2() == null) //only trying to build one road
		{
			return roads >= 1; //does this player have 1 road to play
		}
		
		return false;
	}

	public boolean canDiscardCards(DiscardCards_Input params)
	{
		//has not discarded cards yet, and has all the cards
		return (resources.hasResources(params.getDiscardedCards()) && !discarded);
	}
	
	/**
	 * Determines whether or not this player has
	 * -enough roads remaining to build another road
	 * -sufficient resources to build a road
	 * @return
	 */
	public boolean canBuildRoad() 
	{
		//at least 1 brick and 1 wood
		return ((resources.getBrick() > 0 && resources.getWood()>0) && roads > 0);
	}
	
	public int getAvailableRoads()
	{
		return roads;
	}
	
	/**
	 * Determines whether or not this player has
	 * -enough settlements remaining to build another settlement
	 * -sufficient resources to build a settlement
	 * @return
	 */
	public boolean canBuildSettlement() 
	{
		//at least 1 brick, at least 1 wheat, at least 1 sheep, at least 1 wood, and at least 1 settlement
		if(resources.getBrick() > 0 && resources.getWheat() > 0 && resources.getSheep() > 0 && resources.getWood() > 0 && settlements > 0)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Determines whether or not this player has
	 * -enough cities remaining to build a city
	 * -sufficient resources to build a city
	 * @return
	 */
	public boolean canBuildCity() 
	{
		//at least 2 wheat, at least 3 ore, at least 1 city
		if(resources.getWheat() >= 2 && resources.getOre() >=3 && cities > 0)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Determines whether or not this player has
	 * 1) any DevCards to play
	 * 2) if that card can be played this turn
	 * @return
	 */
	public boolean canPlayDevCard() {
		return !playedDevCard;
	}
	
	
	
	
	
	
	@Override
	public String toString() {
		return "Player [" 
				+ "\n\tname=" + name + ", color=" + color + ", playerID=" + playerID 
				+ "\n\tindex=" + index + ", discarded=" + discarded + ", playedDevCard=" + playedDevCard 
				+ "\n\tnewDevCards=" + newDevCards + "\n\toldDevCards=" + oldDevCards + "\n\tresources=" + resources 
				+ "\n\troads=" + roads + ", settlements=" + settlements + ", cities=" + cities
				+ "\n\tsoldiers=" + soldiers + ", monuments=" + monuments + ", longestRoad=" + longestRoad 
				+ "\n\tvictoryPoints=" + victoryPoints + "   ]";
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
