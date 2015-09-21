package client.server;

import shared.models.*;
import shared.locations.*;

public interface IServer {
	
	/**
	 * builds road on given edgeLocation
	 * player uses required resource cards
	 * 
	 * @param player
	 * @param edgeLocation
	 */
	public void buildRoad(Player player, EdgeLocation edgeLocation);
	
	/**
	 * builds settlement on given vertexLocation
	 * players uses required resource cards
	 * 
	 * @param player
	 * @param vertexLocation
	 */
	public void buildSettlement(Player player, VertexLocation vertexLocation);
	
	/**
	 * upgrades previous settlement to a city
	 * players uses required resource cards
	 * 
	 * @param player
	 * @param vertexLocation
	 */
	public void upgradeSettlement(Player player, VertexLocation vertexLocation);
	
	/**
	 * sends tradeOffer to designated player
	 * 
	 * @param tradeOffer
	 */
	public void sendTradeOffer(TradeOffer tradeOffer);
	
	/**
	 * accepts tradeOffer, both players involved update ResourceCardList
	 * 
	 * @param tradeOffer
	 */
	public void acceptTradeOffer(TradeOffer tradeOffer);
	
	/**
	 * player uses required resource cards to gain a development card
	 * 
	 * @param player
	 */
	public void purchaseDevelopmentCard(Player player);
	
	/**
	 * player uses development card
	 * 
	 * @param player
	 * @param cardType
	 */
	public void playDevelopmentCard(Player player, int cardType);
	
	/**
	 * all players gain resource cards based on roll
	 * 
	 * @param rollValue
	 */
	public void produce(int rollValue);
	
	/**
	 * robber is moved to hexLocation
	 * a player is robbed of a resource card
	 * 
	 * @param hexLocation
	 */
	public void moveRobber(HexLocation hexLocation);
	
	/**
	 * message is added to appropriate MessageList
	 * 
	 * @param messageLine
	 */
	public void sendMessage(MessageLine messageLine);
	
	/**
	 * checks if client has most recent version of the ClientModel
	 * 
	 * @param version
	 * @return server response
	 */
	public String checkClientVersion(int version);
}
