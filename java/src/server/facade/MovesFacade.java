package server.facade;

import com.google.gson.JsonElement;

import shared.communication.moves.*;

//make all methods static
public class MovesFacade extends IMovesFacade
{
	public MovesFacade()
	{
		super();
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
		if(!setModel(playerID,gameID))
		{
			return null;
		}
		else if(!canAcceptTrade(params))
		{
			return null;
		}
		super.getModel().acceptTrade(params);
		return super.serializeModel();
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
		if(!setModel(playerID,gameID))
		{
			return null;
		}
		else if(!canBuildCity(params))
		{
			return null;
		}
		super.getModel().buildCity(params);
		return super.serializeModel();
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
		if(!setModel(playerID,gameID))
		{
			return null;
		}
		else if(!canBuildRoad(params))
		{
			return null;
		}
		super.getModel().buildRoad(params);
		return super.serializeModel();
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
		if(!setModel(playerID,gameID))
		{
			return null;
		}
		else if(!canBuildSettlement(params))
		{
			return null;
		}
		super.getModel().buildSettlement(params);
		return super.serializeModel();
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
		if(!setModel(playerID,gameID))
		{
			return null;
		}
		else if(!canBuyDevCard(params))
		{
			return null;
		}
		super.getModel().buyDevCard(params);
		return super.serializeModel();
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
		if(!setModel(playerID,gameID))
		{
			return null;
		}
		else if(!canDiscardCards(params))
		{
			return null;
		}
		super.getModel().discardCards(params);
		return super.serializeModel();
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
		if(!setModel(playerID,gameID))
		{
			return null;
		}
		else if(!canFinishTurn(params))
		{
			return null;
		}
		super.getModel().finishTurn(params);
		return super.serializeModel();
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
		if(!setModel(playerID,gameID))
		{
			return null;
		}
		else if(!canMaritimeTrade(params))
		{
			return null;
		}
		super.getModel().maritimeTrade(params);
		return super.serializeModel();
	}
	
	/**
	 * Handles requests for playing a monument dev card. Ensures move is valid, and if so updates the model
	 * @param params
	 * @param playerID
	 * @param gameID
	 * @return updated clientModel in JSON form if successful, else error message in JSON
	 */
	@Override
	public JsonElement monopoly(Monopoly_Input params, int playerID, int gameID)
	{
		if(!setModel(playerID,gameID))
		{
			return null;
		}
		else if(!canMonopoly(params))
		{
			return null;
		}
		super.getModel().monopoly(params);
		return super.serializeModel();
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
		if(!setModel(playerID,gameID))
		{
			return null;
		}
		else if(!canMonument(params))
		{
			return null;
		}
		super.getModel().monument(params);
		return super.serializeModel();
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
		if(!setModel(playerID,gameID))
		{
			return null;
		}
		else if(!canOfferTrade(params))
		{
			return null;
		}
		super.getModel().offerTrade(params);
		return super.serializeModel();
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
		if(!setModel(playerID,gameID))
		{
			return null;
		}
		else if(!canRoadBuilding(params))
		{
			return null;
		}
		super.getModel().roadBuilding(params);
		return super.serializeModel();
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
		if(!setModel(playerID,gameID))
		{
			return null;
		}
		else if(!canRobPlayer(params))
		{
			return null;
		}
		super.getModel().robPlayer(params);
		return super.serializeModel();
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
		if(!setModel(playerID,gameID))
		{
			return null;
		}
		else if(!canRollNumber(params))
		{
			return null;
		}
		super.getModel().rollNumber(params);
		return super.serializeModel();
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
		if(!setModel(playerID,gameID))
		{
			return null;
		}
		else if(!canSendChat(params))
		{
			return null;
		}
		super.getModel().sendChat(params);
		return super.serializeModel();
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
		if(!setModel(playerID,gameID))
		{
			return null;
		}
		else if(!canSoldier(params))
		{
			return null;
		}
		super.getModel().soldier(params);
		return super.serializeModel();
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
		if(!setModel(playerID,gameID))
		{
			return null;
		}
		else if(!canYearOfPlenty(params))
		{
			return null;
		}
		super.getModel().yearOfPlenty(params);
		return super.serializeModel();
	}
}
