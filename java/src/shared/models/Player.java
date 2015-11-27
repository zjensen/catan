package shared.models;

import java.util.ArrayList;

import shared.communication.moves.DiscardCards_Input;
import shared.communication.moves.MaritimeTrade_Input;
import shared.communication.moves.RoadBuilding_Input;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;

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
	private ArrayList<EdgeValue> builtRoads;
	
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
		this.setBuiltRoads(new ArrayList<EdgeValue>());
	}
	
	public Player(String name, String color, int playerId, int index) {
		this.name = name;
		this.color = color;
		this.playerID = playerId;
		this.index = index;
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
		this.setBuiltRoads(new ArrayList<EdgeValue>());
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
		if(offer.getBrick() > 0 && Math.abs(offer.getBrick()) > resources.getBrick())
			return false;
		else if (offer.getOre() > 0 && Math.abs(offer.getOre()) > resources.getOre())
			return false;
		else if (offer.getSheep() > 0 && Math.abs(offer.getSheep()) > resources.getSheep())
			return false;
		else if (offer.getWheat() > 0 && Math.abs(offer.getWheat()) > resources.getWheat())
			return false;
		else if (offer.getWood() > 0 && Math.abs(offer.getWood()) > resources.getWood())
			return false;
		return true;
	}
	
	public boolean canReceiveCards(ResourceCards offer)
	{
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
		return ((oldDevCards.getMonument() > 0 || newDevCards.getMonument() > 0) && gameChanger);
	}
	
	public boolean canMonopoly()
	{
		return (oldDevCards.getMonopoly() > 0 && canPlayDevCard());
	}
	
	public boolean canSoldier()
	{
		return (oldDevCards.getSoldier() > 0 && canPlayDevCard());
	}
	
	public boolean canRoadBuilding()
	{
		if(oldDevCards.getRoadBuilding() < 1 || !canPlayDevCard() || roads < 2)
		{
			return false;
		}
		return true;
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
	
	public int getAvailableSettlements()
	{
		return settlements;
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
	
	public int getAvailableCities()
	{
		return cities;
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
	
	public int getTotalSoldierCards() {
		
		int total = oldDevCards.getSoldier() + newDevCards.getSoldier();
		return total;
	}
	
	public int getTotalYearOfPlentyCards() {
		int total = oldDevCards.getYearOfPlenty() + newDevCards.getYearOfPlenty();
		return total;
	}
	
	public int getTotalMonopolyCards() {
		int total = oldDevCards.getMonopoly() + newDevCards.getMonopoly();
		return total;
	}
	
	public int getTotalRoadBuildingCards() {
		int total = oldDevCards.getRoadBuilding() + newDevCards.getRoadBuilding();
		return total;
	}

	public int getTotalMonumentCards() {
		int total = oldDevCards.getMonument() + newDevCards.getMonument();
		return total;
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
	
	/**
	 * converts a color string to CatanColor enum
	 * @return
	 */
	public CatanColor getCatanColor() {
		
		CatanColor catanColor;
		switch (color) {
		case "red":
			catanColor = CatanColor.RED;
			break;
		case "orange":
			catanColor = CatanColor.ORANGE;
			break;
		case "yellow":
			catanColor = CatanColor.YELLOW;
			break;
		case "blue":
			catanColor = CatanColor.BLUE;
			break;
		case "green":
			catanColor = CatanColor.GREEN;
			break;
		case "purple":
			catanColor = CatanColor.PURPLE;
			break;
		case "puce":
			catanColor = CatanColor.PUCE;
			break;
		case "white":
			catanColor = CatanColor.WHITE;
			break;
		case "brown":
			catanColor = CatanColor.BROWN;
			break;
		default:
			catanColor = null;
			break;
		}

		return catanColor;
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
	
	public int getRoadsPlayed() {
		return 15 - roads;
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

	public int getNumberOfCards()
	{
		return resources.getTotal();
	}

	public int getSettlementsPlayed()
	{
		return 5-settlements;
	}
	
	public int getOldDevCardsCount()
	{
		return oldDevCards.cardCount();
	}

	public void buildCity()
	{
		cities--;
		victoryPoints++;
		settlements++;
		resources.setOre(resources.getOre()-3);
		resources.setWheat(resources.getWheat()-2);
	}

	public void buildRoad()
	{
		roads--;
		resources.setBrick(resources.getBrick()-1);
		resources.setWood(resources.getWood()-1);
	}
	public void buildFreeRoad()
	{
		roads--;
	}

	public void buildSettlement()
	{
		settlements--;
		victoryPoints++;
		resources.setBrick(resources.getBrick()-1);
		resources.setWood(resources.getWood()-1);
		resources.setSheep(resources.getSheep()-1);
		resources.setWheat(resources.getWheat()-1);
	}
	public void buildFreeSettlement()
	{
		settlements--;
		victoryPoints++;
	}

	public void addVictoryPoint()
	{
		victoryPoints++;
	}
	
	public void subtract2VictoryPoints()
	{
		victoryPoints-=2;
	}
	public void add2VictoryPoints()
	{
		victoryPoints+=2;
	}

	public void maritimeTrade(MaritimeTrade_Input params)
	{
		switch(params.getInputResource()) //subtract resource they're trading
		{
			case BRICK:
				resources.changeBrick(-params.getRatio());
				break;
			case ORE:
				resources.changeOre(-params.getRatio());
				break;
			case SHEEP:
				resources.changeSheep(-params.getRatio());
				break;
			case WHEAT:
				resources.changeWheat(-params.getRatio());
				break;
			case WOOD:
				resources.changeWood(-params.getRatio());
				break;
		}
		switch(params.getOutputResource()) //add resource they're trading for
		{
			case BRICK:
				resources.changeBrick(1);
				break;
			case ORE:
				resources.changeOre(1);
				break;
			case SHEEP:
				resources.changeSheep(1);
				break;
			case WHEAT:
				resources.changeWheat(1);
				break;
			case WOOD:
				resources.changeWood(1);
				break;
		}
	}

	public void receiveCards(ResourceCards offer)
	{
		if(offer.getBrick() != 0)
			resources.changeBrick(offer.getBrick());
		if (offer.getOre() != 0)
			resources.changeOre(offer.getOre());
		if (offer.getSheep() != 0)
			resources.changeSheep(offer.getSheep());
		if (offer.getWheat() != 0)
			resources.changeWheat(offer.getWheat());
		if (offer.getWood() != 0)
			resources.changeWood(offer.getWood());
	}

	public void sendCards(ResourceCards offer)
	{
		if(offer.getBrick() != 0)
			resources.changeBrick(-offer.getBrick());
		if (offer.getOre() != 0)
			resources.changeOre(-offer.getOre());
		if (offer.getSheep() != 0)
			resources.changeSheep(-offer.getSheep());
		if (offer.getWheat() != 0)
			resources.changeWheat(-offer.getWheat());
		if (offer.getWood() != 0)
			resources.changeWood(-offer.getWood());
	}

	public void playMonumentCard()
	{
		addVictoryPoint(); //award a victory point
		if(oldDevCards.getMonument() > 0)
		{
			oldDevCards.subtractDevCard(DevCardType.MONUMENT);
		}
		else if(newDevCards.getMonument() > 0)
		{
			newDevCards.subtractDevCard(DevCardType.MONUMENT);
		}
		setPlayedDevCard(true);
	}

	public void addCard(ResourceType r)
	{
		if(r==null)
		{
			return;
		}
		
		switch(r) //add resource they're trading for
		{
			case BRICK:
				resources.changeBrick(1);
				break;
			case ORE:
				resources.changeOre(1);
				break;
			case SHEEP:
				resources.changeSheep(1);
				break;
			case WHEAT:
				resources.changeWheat(1);
				break;
			case WOOD:
				resources.changeWood(1);
				break;
		}
	}

	public ArrayList<EdgeValue> getBuiltRoads() {
		return builtRoads;
	}

	public void setBuiltRoads(ArrayList<EdgeValue> builtRoads) {
		this.builtRoads = builtRoads;
	}
	
	public void addBuiltRoad(EdgeValue value)
	{
		this.builtRoads.add(value);
	}
}
