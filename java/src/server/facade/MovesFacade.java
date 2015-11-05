package server.facade;

import shared.communication.moves.*;
import shared.models.ClientModel;

import com.google.gson.JsonElement;

//make all methods static
public class MovesFacade 
{
	/**
	 * Converts a ClientModel to JSON for sending to client
	 * @param model
	 * @return clientModel serialized as a JSON object
	 */
	private static JsonElement serializeModel(ClientModel model)
	{
		return null;
	}
	
	/**
	 * Handles acceptTrade requests. Ensures move is valid, and if so updates the model
	 * @param params
	 * @param playerID
	 * @param gameID
	 * @return updated clientModel in JSON form if successful, else error message in JSON
	 */
	public static JsonElement acceptTrade(AcceptTrade_Input params, int playerID, int gameID)
	{
		return null;
	}
	
	/**
	 * Handles buildCity requests. Ensures move is valid, and if so updates the model
	 * @param params
	 * @param playerID
	 * @param gameID
	 * @return updated clientModel in JSON form if successful, else error message in JSON
	 */
	public static JsonElement buildCity(BuildCity_Input params, int playerID, int gameID)
	{
		return null;
	}
	
	/**
	 * Handles buildRoad requests. Ensures move is valid, and if so updates the model
	 * @param params
	 * @param playerID
	 * @param gameID
	 * @return updated clientModel in JSON form if successful, else error message in JSON
	 */
	public static JsonElement buildRoad(BuildRoad_Input params, int playerID, int gameID)
	{
		return null;
	}
	
	/**
	 * Handles buildSettlement requests. Ensures move is valid, and if so updates the model
	 * @param params
	 * @param playerID
	 * @param gameID
	 * @return updated clientModel in JSON form if successful, else error message in JSON
	 */
	public static JsonElement buildSettlement(BuildSettlement_Input params, int playerID, int gameID)
	{
		return null;
	}
	
	/**
	 * Handles requests for buying dev cards. Ensures move is valid, and if so updates the model
	 * @param params
	 * @param playerID
	 * @param gameID
	 * @return updated clientModel in JSON form if successful, else error message in JSON
	 */
	public static JsonElement buyDevCard(BuyDevCard_Input params, int playerID, int gameID)
	{
		return null;
	}
	
	/**
	 * Handles requests for discarding cards. Ensures move is valid, and if so updates the model
	 * @param params
	 * @param playerID
	 * @param gameID
	 * @return updated clientModel in JSON form if successful, else error message in JSON
	 */
	public static JsonElement discardCards(DiscardCards_Input params, int playerID, int gameID)
	{
		return null;
	}
	
	/**
	 * Handles requests for finishing a turn. Ensures move is valid, and if so updates the model
	 * @param params
	 * @param playerID
	 * @param gameID
	 * @return updated clientModel in JSON form if successful, else error message in JSON
	 */
	public static JsonElement finishTurn(FinishTurn_Input params, int playerID, int gameID)
	{
		return null;
	}
	
	/**
	 * Handles requests for maritime trade. Ensures move is valid, and if so updates the model
	 * @param params
	 * @param playerID
	 * @param gameID
	 * @return updated clientModel in JSON form if successful, else error message in JSON
	 */
	public static JsonElement maritimeTrade(MaritimeTrade_Input params, int playerID, int gameID)
	{
		return null;
	}
	
	/**
	 * Handles requests for playing a monument dev card. Ensures move is valid, and if so updates the model
	 * @param params
	 * @param playerID
	 * @param gameID
	 * @return updated clientModel in JSON form if successful, else error message in JSON
	 */
	public static JsonElement monument(Monument_Input params, int playerID, int gameID)
	{
		return null;
	}
	
	/**
	 * Handles requests for offering a domestic trade. Ensures move is valid, and if so updates the model
	 * @param params
	 * @param playerID
	 * @param gameID
	 * @return updated clientModel in JSON form if successful, else error message in JSON
	 */
	public static JsonElement offerTrade(OfferTrade_Input params, int playerID, int gameID)
	{
		return null;
	}
	
	/**
	 * Handles requests for playing road building dev card. Ensures move is valid, and if so updates the model
	 * @param params
	 * @param playerID
	 * @param gameID
	 * @return updated clientModel in JSON form if successful, else error message in JSON
	 */
	public static JsonElement roadBuilding(RoadBuilding_Input params, int playerID, int gameID)
	{
		return null;
	}
	
	/**
	 * Handles requests for robbing a player. Ensures move is valid, and if so updates the model
	 * @param params
	 * @param playerID
	 * @param gameID
	 * @return updated clientModel in JSON form if successful, else error message in JSON
	 */
	public static JsonElement robPlayer(RobPlayer_Input params, int playerID, int gameID)
	{
		return null;
	}
	
	/**
	 * Handles requests for rolling the dice. Ensures move is valid, and if so updates the model
	 * @param params
	 * @param playerID
	 * @param gameID
	 * @return updated clientModel in JSON form if successful, else error message in JSON
	 */
	public static JsonElement rollNumber(RollNumber_Input params, int playerID, int gameID)
	{
		return null;
	}
	
	/**
	 * Handles requests for sending a chat message. Ensures move is valid, and if so updates the model
	 * @param params
	 * @param playerID
	 * @param gameID
	 * @return updated clientModel in JSON form if successful, else error message in JSON
	 */
	public static JsonElement sendChat(SendChat_Input params, int playerID, int gameID)
	{
		return null;
	}
	
	/**
	 * Handles requests for playing a soldier development card. Ensures move is valid, and if so updates the model
	 * @param params
	 * @param playerID
	 * @param gameID
	 * @return updated clientModel in JSON form if successful, else error message in JSON
	 */
	public static JsonElement soldier(Soldier_Input params, int playerID, int gameID)
	{
		return null;
	}
	
	/**
	 * Handles requests for playing a year-of-plenty dev card. Ensures move is valid, and if so updates the model
	 * @param params
	 * @param playerID
	 * @param gameID
	 * @return updated clientModel in JSON form if successful, else error message in JSON
	 */
	public static JsonElement yearOfPlenty(YearOfPlenty_Input params, int playerID, int gameID)
	{
		return null;
	}
	
}
