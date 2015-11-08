package shared.models;

import shared.communication.moves.*;
import shared.definitions.DevCardType;

public class ClientModel {
	
	private ResourceCards bank;
	private DevCards deck;
	private MessageList chat;
	private MessageList log;
	private Map map;
	private Player[] players;
	private TradeOffer tradeOffer;
	private TurnTracker turnTracker;
	private int version;
	private int winner;
	
	public ClientModel() {
		this.bank = null;
		this.deck = null;
		this.chat = null;
		this.log = null;
		this.map = null;
		this.players = null;
		this.tradeOffer = null;
		this.turnTracker = null;
		this.version = -1;
		this.winner = -1;
	}
	
	/**
	 * parses json to update member variables
	 * 
	 * @param json -> json with the new client model information
	 */
	public void updateClient(String json)
	{

	}
	
	/**
	 * grabs player with the specified index
	 * @param index
	 * @return Player with the specified index
	 */
	public Player getPlayerByIndex(int index)
	{
		for(Player p : players)
		{
			if(p.getIndex()==index)
			{
				return p;
			}
		}
		return null;
	}
	
	/**
	 * Gets a player's index in this model
	 * @param playerID
	 * @return
	 */
	public int getPlayerIndexByID(int playerID)
	{
		for(Player p : players)
		{
			if(p.getPlayerID()==playerID)
			{
				return p.getIndex();
			}
		}
		return -1;
	}
	
	/**
	 * can we play road building dev card with these params?
	 * @param params
	 * @return
	 */
	public boolean canRoadBuilding(RoadBuilding_Input params) //todo
	{
		Player p = getPlayerByIndex(params.getPlayerIndex());
		if(params.getSpot1()!=null && params.getSpot2()!=null)
		{
			if(params.getSpot1().equals(params.getSpot2()))
			{
				return false;
			}
		}
		
		return p.canRoadBuilding(params) && map.canRoadBuilding(params);
	}

	/**
	 * make sure player has the cards they are attempting to offer
	 * @param params
	 * @return
	 */
	public boolean canOfferTrade(OfferTrade_Input params)
	{
		Player sender = getPlayerByIndex(params.getPlayerIndex());
//		Player receiver = getPlayerByIndex(params.getReceiver());
		return (sender.canOfferCards(params.getOffer())); //&& receiver.hasResources(params.getOffer()));
	}

	/**
	 * Checks if the player has the cards needed to trade
	 * @param params
	 * @return
	 */
	public boolean canAcceptTrade(AcceptTrade_Input params)
	{
		Player receiver = getPlayerByIndex(params.getPlayerIndex());
		
		if(params.getPlayerIndex() != this.getTradeOffer().getReceiver()) //this user is incorrect
		{
			return false;
		}
		if(!params.isWillAccept()) //user is declining, so we don't need to check if they have the cards
		{
			return true;
		}
		
		return receiver.canReceiveCards(this.getTradeOffer().getOffer());
	}

	public boolean canMaritimeTrade(MaritimeTrade_Input params) //todo
	{
		Player p = getPlayerByIndex(params.getPlayerIndex());
		if(!p.canMaritimeTrade(params))
		{
			return false;
		}
		else if(!map.canMaritimeTrade(params)) //todo
		{
			return false;
		}
		return true;
	}
	
	public boolean canSoldier(Soldier_Input params)
	{
		boolean can = true;
		int playerIndex = params.getPlayerIndex();
		if(!getPlayerByIndex(playerIndex).canSoldier()) //the player is able to build the road
		{
			can = false;
		}
		else if(!map.canSoldier(params))
		{
			can = false;
		}
		return can;
	}
	
	public boolean canBuyDevCard(BuyDevCard_Input params)
	{
		return getPlayerByIndex(params.getPlayerIndex()).canBuyDevCard();
	}
	
	public boolean canRobPlayer(RobPlayer_Input params)
	{
		if(params.getVictimIndex() != -1 && getPlayerByIndex(params.getVictimIndex()).getNumberOfCards() == 0)
		{
			return false;
		}
		return map.canRobPlayer(params);
	}
	
	public boolean canYearOfPlenty(YearOfPlenty_Input params)
	{
		return getPlayerByIndex(params.getPlayerIndex()).canYearOfPlenty();
	}
	
	public boolean canMonopoly(Monopoly_Input params)
	{
		return getPlayerByIndex(params.getPlayerIndex()).canMonopoly();
	}
	
	public boolean canMonument(Monument_Input params)
	{
		return getPlayerByIndex(params.getPlayerIndex()).canMonument();
	}

	public boolean canDiscardCards(DiscardCards_Input params)
	{
		return getPlayerByIndex(params.getPlayerIndex()).canDiscardCards(params);
	}
	
	/**
	 * Checks the player and the map to see if a road can be built as requested
	 * @param params
	 * @return
	 */
	public boolean canBuildRoad(BuildRoad_Input params)
	{
		boolean can = true;
		int playerIndex = params.getPlayerIndex();
		
		if(params.isFree()) //no resources require, roads don't have to be neighbors
		{
			if(!map.canBuildFreeRoad(params))
			{
				can = false;
			}
			else if(getPlayerByIndex(playerIndex).getAvailableRoads() == 0)
			{
				can = false;
			}
		}
		else
		{
			if(!getPlayerByIndex(playerIndex).canBuildRoad()) //the player is able to build the road
			{
				can = false;
			}
			else if(!map.canBuildRoad(params))
			{
				can = false;
			}
		}
		
		return can;
	}
	
	/**
	 * Checks the player and the map to see if a city can be built as requested
	 * @param params
	 * @return
	 */
	public boolean canBuildCity(BuildCity_Input params)
	{
		boolean can = true;
		int playerIndex = params.getPlayerIndex();
		if(!getPlayerByIndex(playerIndex).canBuildCity()) //the player is able to build the road
		{
			can = false;
		}
		else if(!map.canBuildCity(params))
		{
			can = false;
		}
		return can;
	}
	
	/**
	 * Checks the player and the map to see if a settlement can be built as requested
	 * @param params
	 * @return
	 */
	public boolean canBuildSettlement(BuildSettlement_Input params)
	{
		boolean can = true;
		int playerIndex = params.getPlayerIndex();
		if(!params.isFree()) //player needs resources
		{
			if(!getPlayerByIndex(playerIndex).canBuildSettlement()) //the player has resources to build the road
			{
				can = false;
			}
		}
		
		if(!map.canBuildSettlement(params)) //check if this location is available to this user to build on
		{
			can = false;
		}
		return can;
	}
	
	public ResourceCards getBank() {
		return bank;
	}
	public void setBank(ResourceCards bank) {
		this.bank = bank;
	}
	public MessageList getChat() {
		return chat;
	}
	public void setChat(MessageList chat) {
		this.chat = chat;
	}
	public MessageList getLog() {
		return log;
	}
	public void setLog(MessageList log) {
		this.log = log;
	}
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	public Player[] getPlayers() {
		return players;
	}
	public void setPlayers(Player[] players) {
		this.players = players;
	}
	public TradeOffer getTradeOffer() {
		return tradeOffer;
	}
	public void setTradeOffer(TradeOffer tradeOffer) {
		this.tradeOffer = tradeOffer;
	}
	public TurnTracker getTurnTracker() {
		return turnTracker;
	}
	public void setTurnTracker(TurnTracker turnTracker) {
		this.turnTracker = turnTracker;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Integer getWinner() {
		return winner;
	}
	public void setWinner(Integer winner) {
		this.winner = winner;
	}
	public DevCards getDeck() {
		return deck;
	}
	public void setDeck(DevCards deck) {
		this.deck = deck;
	}

	public boolean canBuildFirstRoad(BuildRoad_Input params)
	{
		Player p = getPlayerByIndex(params.getPlayerIndex());
		return map.canBuildInitialRoad(params) && p.getRoadsPlayed() == 0;
	}
	
	public boolean canBuildSecondRoad(BuildRoad_Input params)
	{
		Player p = getPlayerByIndex(params.getPlayerIndex());
		return map.canBuildInitialRoad(params) && p.getRoadsPlayed() == 1;
	}

	public boolean canFinishTurnFirstRound(int playerIndex)
	{
		Player p = getPlayerByIndex(playerIndex);
		return (p.getRoadsPlayed() == 1) && (p.getSettlementsPlayed() == 1);
	}
	
	public boolean canFinishTurnSecondRound(int playerIndex)
	{
		Player p = getPlayerByIndex(playerIndex);
		return (p.getRoadsPlayed() == 2) && (p.getSettlementsPlayed() == 2);
	}

	public boolean needsToDiscard(int index)
	{
		Player p = getPlayerByIndex(index);
		return (p.getNumberOfCards() > 7) && !p.isDiscarded();
	}
	
	public int cardsToDiscard(int index)
	{
		Player p = getPlayerByIndex(index);
		return (p.getNumberOfCards() / 2);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public void acceptTrade(AcceptTrade_Input params)
	{
		Player receiver = getPlayerByIndex(params.getPlayerIndex());
		receiver.receiveCards(tradeOffer.getOffer());
		Player sender = getPlayerByIndex(tradeOffer.getSender());
		sender.sendCards(tradeOffer.getOffer());
		tradeOffer = null;
	}
		
	public void buildCity(BuildCity_Input params)
	{
		Player p = getPlayerByIndex(params.getPlayerIndex());
		p.buildCity();
		map.getCities().put(params.getVertexLocation(), p);
	}

	public void buildRoad(BuildRoad_Input params)
	{
		Player p = getPlayerByIndex(params.getPlayerIndex());
		p.buildRoad();
		
		if(p.getRoadsPlayed() >= 5)
		{
			int mostRoads = 0;
			for(Player p2 : players)
			{
				if(p2.getIndex() != p.getIndex() && p2.getRoadsPlayed() > mostRoads)
				{
					mostRoads = p2.getRoadsPlayed();
				}
			}
			if(p.getRoadsPlayed()>mostRoads)
			{
				getPlayerByIndex(turnTracker.getLongestRoad()).subtract2VictoryPoints();
				p.add2VictoryPoints();
				turnTracker.setLongestRoad(p.getIndex());
			}
		}
		
		map.getRoads().put(params.getRoadLocation(), p);
	}

	public void buildSettlement(BuildSettlement_Input params)
	{
		Player p = getPlayerByIndex(params.getPlayerIndex());
		p.buildSettlement();
		map.getSettlements().put(params.getVertexLocation(), p);
	}

	public void buyDevCard(BuyDevCard_Input params)
	{
		getPlayerByIndex(params.getPlayerIndex()).getNewDevCards().addDevCard(deck.getRandomCard());
	}

	public void discardCards(DiscardCards_Input params)
	{
		//todo
	}

	public void finishTurn(FinishTurn_Input params)
	{
		Player p = getPlayerByIndex(params.getPlayerIndex()); //player ending turn
		p.getOldDevCards().addCards(p.getNewDevCards()); //adds new devCards to old cards
		p.setNewDevCards(new DevCards()); //resets new cards
		turnTracker.endTurn();
	}

	public void maritimeTrade(MaritimeTrade_Input params)
	{
		getPlayerByIndex(params.getPlayerIndex()).maritimeTrade(params);
	}

	public void monopoly(Monopoly_Input params)
	{
		Player receiver = getPlayerByIndex(params.getPlayerIndex());
		receiver.getOldDevCards().subtractDevCard(DevCardType.MONOPOLY);
		receiver.setPlayedDevCard(true);
		for(Player p : players)
		{
			if(p.getIndex()==params.getPlayerIndex())
			{
				continue;
			}
			int num = 0;
			switch (params.getResource()) 
			{
				case WOOD:
					num = p.getResources().getWood();
					if(num > 0)
					{
						p.getResources().changeWood(-num);
						receiver.getResources().changeWood(num);
					}
					break;
				case BRICK:
					num = p.getResources().getBrick();
					if(num > 0)
					{
						p.getResources().changeBrick(-num);
						receiver.getResources().changeBrick(num);
					}
					break;
				case SHEEP:
					num = p.getResources().getSheep();
					if(num > 0)
					{
						p.getResources().changeSheep(-num);
						receiver.getResources().changeSheep(num);
					}
					break;
				case WHEAT:
					num = p.getResources().getWheat();
					if(num > 0)
					{
						p.getResources().changeWheat(-num);
						receiver.getResources().changeWheat(num);
					}
					break;
				case ORE:
					num = p.getResources().getOre();
					if(num > 0)
					{
						p.getResources().changeOre(-num);
						receiver.getResources().changeOre(num);
					}
					break;
			}
		}
	}

	public void monument(Monument_Input params)
	{
		getPlayerByIndex(params.getPlayerIndex()).addVictoryPoint();
	}

	public void offerTrade(OfferTrade_Input params)
	{
		tradeOffer = new TradeOffer(params.getPlayerIndex(),params.getReceiver(),params.getOffer());
	}

	public void roadBuilding(RoadBuilding_Input params)
	{
		Player p = getPlayerByIndex(params.getPlayerIndex());
		p.getOldDevCards().subtractDevCard(DevCardType.ROAD_BUILD);
		p.setPlayedDevCard(true);
		map.getRoads().put(params.getSpot1().getNormalizedLocation(), p);
		map.getRoads().put(params.getSpot2().getNormalizedLocation(), p);
		if(p.getRoadsPlayed() >= 5)
		{
			int mostRoads = 0;
			for(Player p2 : players)
			{
				if(p2.getIndex() != p.getIndex() && p2.getRoadsPlayed() > mostRoads)
				{
					mostRoads = p2.getRoadsPlayed();
				}
			}
			if(p.getRoadsPlayed()>mostRoads)
			{
				turnTracker.setLongestRoad(p.getIndex());
			}
		}
	}

	public void robPlayer(RobPlayer_Input params)
	{
		//todo
		turnTracker.setStatus("playing");
	}

	public void rollNumber(RollNumber_Input params)
	{
		if(params.getNumber()==7)
		{
			turnTracker.setStatus("discarding");
			//todo
		}
		else
		{
			//todo
			turnTracker.setStatus("playing");
		}
	}

	public void sendChat(SendChat_Input params)
	{
		chat.addLine(getPlayerByIndex(params.getPlayerIndex()).getName(),params.getContent());
	}

	public void soldier(Soldier_Input params)
	{
		//todo
	}

	public void yearOfPlenty(YearOfPlenty_Input params)
	{
		//todo
	}
}
