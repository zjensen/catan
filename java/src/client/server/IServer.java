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
	 * 
	 * @return json or false
	 */
	public String buildRoad(Player player, EdgeLocation edgeLocation);
	
	/**
	 * builds settlement on given vertexLocation
	 * players uses required resource cards
	 * 
	 * @param player
	 * @param vertexLocation
	 * 
	 * @return json or false
	 */
	public String buildSettlement(Player player, VertexLocation vertexLocation);
	
	/**
	 * upgrades previous settlement to a city
	 * player uses required resource cards
	 * 
	 * @param player
	 * @param vertexLocation
	 * 
	 * @return json or false
	 */
	public String upgradeSettlement(Player player, VertexLocation vertexLocation);
	
	/**
	 * sends tradeOffer to designated player or port
	 * 
	 * @param tradeOffer
	 * 
	 * @return json or false
	 */
	public String sendTradeOffer(TradeOffer tradeOffer);
	
	/**
	 * accepts tradeOffer, both parties involved update ResourceCardList
	 * 
	 * @param tradeOffer
	 * 
	 * @return json or false
	 */
	public String acceptTradeOffer(TradeOffer tradeOffer);
	
	/**
	 * player uses required resource cards to gain a development card
	 * 
	 * @param player
	 * 
	 * @return json or false
	 */
	public String purchaseDevelopmentCard(Player player);
	
	/**
	 * player uses development card
	 * 
	 * @param player
	 * @param cardType
	 * 
	 * @return json or false
	 */
	public String playDevelopmentCard(Player player, int cardType);
	
	/**
	 * all players gain resource cards based on roll
	 * robber's location is accounted for
	 * 
	 * @param rollValue
	 * 
	 * @return json or false
	 */
	public String produce(int rollValue);
	
	/**
	 * robber is moved to hexLocation
	 * a player is robbed of a resource card
	 * 
	 * @param hexLocation
	 * 
	 * @return json or false
	 */
	public String moveRobber(HexLocation hexLocation);
	
	/**
	 * message is added to appropriate MessageList
	 * 
	 * @param messageLine
	 * 
	 * @return json or false
	 */
	public String sendMessage(MessageLine messageLine);
	
	/**
	 * checks if client has most recent version of the ClientModel
	 * 
	 * @param version
	 * 
	 * @return new model or true
	 */
	public String checkClientVersion(int version);
}
