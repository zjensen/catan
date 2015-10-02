package shared.models;

import shared.communication.moves.*;

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
	 * can we play road building dev card with these params?
	 * @param params
	 * @return
	 */
	public boolean canRoadBuilding(RoadBuilding_Input params) //todo
	{
		Player p = getPlayerByIndex(params.getPlayerIndex());
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
		
		return receiver.hasResources(this.getTradeOffer().getOffer());
	}

	public boolean canMaritimeTrade(MaritimeTrade_Input params) //todo
	{
		Player p = getPlayerByIndex(params.getPlayerIndex());
		if(!p.canMaritimeTrade(params))
		{
			return false;
		}
		else if(map.canMaritimeTrade(params)) //todo
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
		if(!getPlayerByIndex(playerIndex).canBuildSettlement()) //the player has resources to build the road
		{
			can = false;
		}
		else if(!map.canBuildSettlement(params)) //check if this location is available to this user to build on
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
}
