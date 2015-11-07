package server.facade;

import shared.communication.moves.*;
import shared.models.ClientModel;

import com.google.gson.JsonElement;

//make all methods static
public class MovesFacade implements IMovesFacade
{
	/**
	 * Converts a ClientModel to JSON for sending to client
	 * @param model
	 * @return clientModel serialized as a JSON object
	 */
	private JsonElement serializeModel(ClientModel model)
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
	@Override
	public JsonElement acceptTrade(AcceptTrade_Input params, int playerID, int gameID)
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
	@Override
	public JsonElement buildCity(BuildCity_Input params, int playerID, int gameID)
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
	@Override
	public JsonElement buildRoad(BuildRoad_Input params, int playerID, int gameID)
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
	@Override
	public JsonElement buildSettlement(BuildSettlement_Input params, int playerID, int gameID)
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
	@Override
	public JsonElement buyDevCard(BuyDevCard_Input params, int playerID, int gameID)
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
	@Override
	public JsonElement discardCards(DiscardCards_Input params, int playerID, int gameID)
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
	@Override
	public JsonElement finishTurn(FinishTurn_Input params, int playerID, int gameID)
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
	@Override
	public JsonElement maritimeTrade(MaritimeTrade_Input params, int playerID, int gameID)
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
	@Override
	public JsonElement monument(Monument_Input params, int playerID, int gameID)
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
	@Override
	public JsonElement offerTrade(OfferTrade_Input params, int playerID, int gameID)
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
	@Override
	public JsonElement roadBuilding(RoadBuilding_Input params, int playerID, int gameID)
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
	@Override
	public JsonElement robPlayer(RobPlayer_Input params, int playerID, int gameID)
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
	@Override
	public JsonElement rollNumber(RollNumber_Input params, int playerID, int gameID)
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
	@Override
	public JsonElement sendChat(SendChat_Input params, int playerID, int gameID)
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
	@Override
	public JsonElement soldier(Soldier_Input params, int playerID, int gameID)
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
	@Override
	public JsonElement yearOfPlenty(YearOfPlenty_Input params, int playerID, int gameID)
	{
		return null;
	}
	
}
