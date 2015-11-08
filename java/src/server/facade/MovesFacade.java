package server.facade;

import server.manager.ServerManager;
import shared.communication.moves.*;
import shared.models.ClientModel;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

//make all methods static
public class MovesFacade implements IMovesFacade
{
	private ClientModel model = null;
	private String errorMessage = "";
	
	/**
	 * Converts a ClientModel to JSON for sending to client
	 * @param model
	 * @return clientModel serialized as a JSON object
	 */
	private JsonElement serializeModel()
	{
		Gson gson = new Gson();
		return gson.toJsonTree(this.model);
	}
	
	/**
	 * Check to see if a gameID and playerID are valid, and if so sets the model
	 * @param gameID
	 * @param playerID
	 * @return true if game exists and player is in that game, else false
	 */
	private boolean setModel(int gameID,int playerID)
	{
		this.model = null; //defaults global model to null
		this.errorMessage = ""; //reset error message
		ClientModel modelToValidate = ServerManager.instance().getGamesManager().getClientModelById(gameID); //gets model that matches param
		if(modelToValidate == null)
		{
			this.errorMessage = "Invalid Cookie - Game cookie is invalid";
			return false; //no model matches this gameID
		}
		else if(modelToValidate.getPlayerIndexByID(playerID) == -1)
		{
			this.errorMessage = "Invalid Cookie - playerID in user cookie is invalid";
			return false; //user is not in this game
		}
		this.model = modelToValidate; //set the model
		return true; //we r ready 2 roll
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
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
			return new JsonPrimitive(this.errorMessage);
		}
		else if(!canAcceptTrade(params))
		{
			return new JsonPrimitive(this.errorMessage);
		}
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
		if(!setModel(playerID,gameID))
		{
			return new JsonPrimitive(this.errorMessage);
		}
		else if(!canBuildCity(params))
		{
			return new JsonPrimitive(this.errorMessage);
		}
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
		if(!setModel(playerID,gameID))
		{
			return new JsonPrimitive(this.errorMessage);
		}
		else if(!canBuildRoad(params))
		{
			return new JsonPrimitive(this.errorMessage);
		}
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
		if(!setModel(playerID,gameID))
		{
			return new JsonPrimitive(this.errorMessage);
		}
		else if(!canBuildSettlement(params))
		{
			return new JsonPrimitive(this.errorMessage);
		}
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
		if(!setModel(playerID,gameID))
		{
			return new JsonPrimitive(this.errorMessage);
		}
		else if(!canBuyDevCard(params))
		{
			return new JsonPrimitive(this.errorMessage);
		}
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
		if(!setModel(playerID,gameID))
		{
			return new JsonPrimitive(this.errorMessage);
		}
		else if(!canDiscardCards(params))
		{
			return new JsonPrimitive(this.errorMessage);
		}
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
		if(!setModel(playerID,gameID))
		{
			return new JsonPrimitive(this.errorMessage);
		}
		else if(!canFinishTurn(params))
		{
			return new JsonPrimitive(this.errorMessage);
		}
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
		if(!setModel(playerID,gameID))
		{
			return new JsonPrimitive(this.errorMessage);
		}
		else if(!canMaritimeTrade(params))
		{
			return new JsonPrimitive(this.errorMessage);
		}
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
	public JsonElement monopoly(Monopoly_Input params, int playerID, int gameID)
	{
		if(!setModel(playerID,gameID))
		{
			return new JsonPrimitive(this.errorMessage);
		}
		else if(!canMonopoly(params))
		{
			return new JsonPrimitive(this.errorMessage);
		}
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
		if(!setModel(playerID,gameID))
		{
			return new JsonPrimitive(this.errorMessage);
		}
		else if(!canMonument(params))
		{
			return new JsonPrimitive(this.errorMessage);
		}
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
		if(!setModel(playerID,gameID))
		{
			return new JsonPrimitive(this.errorMessage);
		}
		else if(!canOfferTrade(params))
		{
			return new JsonPrimitive(this.errorMessage);
		}
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
		if(!setModel(playerID,gameID))
		{
			return new JsonPrimitive(this.errorMessage);
		}
		else if(!canRoadBuilding(params))
		{
			return new JsonPrimitive(this.errorMessage);
		}
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
		if(!setModel(playerID,gameID))
		{
			return new JsonPrimitive(this.errorMessage);
		}
		else if(!canRobPlayer(params))
		{
			return new JsonPrimitive(this.errorMessage);
		}
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
		if(!setModel(playerID,gameID))
		{
			return new JsonPrimitive(this.errorMessage);
		}
		else if(!canRollNumber(params))
		{
			return new JsonPrimitive(this.errorMessage);
		}
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
		if(!setModel(playerID,gameID))
		{
			return new JsonPrimitive(this.errorMessage);
		}
		else if(!canSendChat(params))
		{
			return new JsonPrimitive(this.errorMessage);
		}
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
		if(!setModel(playerID,gameID))
		{
			return new JsonPrimitive(this.errorMessage);
		}
		else if(!canSoldier(params))
		{
			return new JsonPrimitive(this.errorMessage);
		}
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
		if(!setModel(playerID,gameID))
		{
			return new JsonPrimitive(this.errorMessage);
		}
		else if(!canYearOfPlenty(params))
		{
			return new JsonPrimitive(this.errorMessage);
		}
		return null;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Can-Do Methods
	
	@Override
	public boolean canAcceptTrade(AcceptTrade_Input params)
	{
		if(!checkStatus("playing"))
		{
			return false;
		}
		else if(this.model.getTradeOffer() == null) //must have trade going on
		{
			return false;
		}
		return(this.model.canAcceptTrade(params));
	}

	@Override
	public boolean canBuildCity(BuildCity_Input params)
	{
		if(!isPlayersTurn(params.getPlayerIndex()))
		{
			return false;
		}
		else if(!checkStatus("playing"))
		{
			return false;
		}
		return (this.model.canBuildCity(params));
	}

	@Override
	public boolean canBuildRoad(BuildRoad_Input params)
	{
		if(!isPlayersTurn(params.getPlayerIndex()))
		{
			return false;
		}
		else if(!checkStatus("playing") && !checkStatus("firstround") && !checkStatus("secondround"))
		{
			return false;
		}
		return (this.model.canBuildRoad(params));
	}

	@Override
	public boolean canBuildSettlement(BuildSettlement_Input params)
	{
		if(!isPlayersTurn(params.getPlayerIndex()))
		{
			return false;
		}
		else if(!checkStatus("playing") && !checkStatus("firstround") && !checkStatus("secondround"))
		{
			return false;
		}
		return (this.model.canBuildSettlement(params));
	}

	@Override
	public boolean canBuyDevCard(BuyDevCard_Input params)
	{
		if(!isPlayersTurn(params.getPlayerIndex()))
		{
			return false;
		}
		else if(!checkStatus("playing"))
		{
			return false;
		}
		return (this.model.canBuyDevCard(params));
	}

	@Override
	public boolean canDiscardCards(DiscardCards_Input params)
	{
		if(!checkStatus("discarding"))
		{
			return false;
		}
		return(this.model.canDiscardCards(params));
	}

	@Override
	public boolean canFinishTurn(FinishTurn_Input params)
	{
		if(!isPlayersTurn(params.getPlayerIndex()))
		{
			return false;
		}
		switch(this.model.getTurnTracker().getStatus().toLowerCase())
		{
			case "firstround":
				return this.model.canFinishTurnFirstRound(params.getPlayerIndex());
			case "secondround":
				return this.model.canFinishTurnSecondRound(params.getPlayerIndex());
			case "playing":
				return this.model.getTradeOffer()==null; //ensures user does not end turn when trade offer is waiting
			case "robbing":
			case "rolling":
			case "discarding":
			default:
				return false;	
		}
	}

	@Override
	public boolean canMaritimeTrade(MaritimeTrade_Input params)
	{
		if(!isPlayersTurn(params.getPlayerIndex()))
		{
			return false;
		}
		else if(!checkStatus("playing"))
		{
			return false;
		}
		return this.model.canMaritimeTrade(params);
	}
	
	@Override
	public boolean canMonopoly(Monopoly_Input params)
	{
		if(!isPlayersTurn(params.getPlayerIndex()))
		{
			return false;
		}
		else if(!checkStatus("playing"))
		{
			return false;
		}
		return this.model.canMonopoly(params);
	}

	@Override
	public boolean canMonument(Monument_Input params)
	{
		if(!isPlayersTurn(params.getPlayerIndex()))
		{
			return false;
		}
		else if(!checkStatus("playing"))
		{
			return false;
		}
		return this.model.canMonument(params);
	}

	@Override
	public boolean canOfferTrade(OfferTrade_Input params)
	{
		if(!isPlayersTurn(params.getPlayerIndex()))
		{
			return false;
		}
		else if(!checkStatus("playing"))
		{
			return false;
		}
		else if(this.model.getTradeOffer() != null) //can't already have trade going
		{
			return false;
		}
		else if(params.getPlayerIndex()==params.getReceiver())
		{
			return false;
		}
		return(this.model.canOfferTrade(params));
	}

	@Override
	public boolean canRoadBuilding(RoadBuilding_Input params)
	{
		if(!isPlayersTurn(params.getPlayerIndex()))
		{
			return false;
		}
		else if(!checkStatus("playing"))
		{
			return false;
		}
		return (this.model.canRoadBuilding(params));
	}

	@Override
	public boolean canRobPlayer(RobPlayer_Input params)
	{
		if(!isPlayersTurn(params.getPlayerIndex()))
		{
			return false;
		}
		else if(!checkStatus("robbing"))
		{
			return false;
		}
		else if(params.getPlayerIndex()==params.getVictimIndex())
		{
			return false;
		}
		return this.model.canRobPlayer(params);
	}

	@Override
	public boolean canRollNumber(RollNumber_Input params)
	{
		if(!isPlayersTurn(params.getPlayerIndex()))
		{
			return false;
		}
		else if(!checkStatus("rolling"))
		{
			return false;
		}
		return (params.getNumber() <= 12 && params.getNumber() >= 2);
	}

	@Override
	public boolean canSendChat(SendChat_Input params)
	{
		return(params.getContent() != null && !params.getContent().isEmpty());
	}

	@Override
	public boolean canSoldier(Soldier_Input params)
	{
		if(!isPlayersTurn(params.getPlayerIndex()))
		{
			return false;
		}
		else if(!checkStatus("playing"))
		{
			return false;
		}
		return(this.model.canSoldier(params));
	}

	@Override
	public boolean canYearOfPlenty(YearOfPlenty_Input params)
	{
		if(!isPlayersTurn(params.getPlayerIndex()))
		{
			return false;
		}
		else if(!checkStatus("playing"))
		{
			return false;
		}
		return( this.model.canYearOfPlenty(params));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Can-Do Helpers
	
	private boolean isPlayersTurn(int playerIndex)
	{
		this.errorMessage = "";
		if(model.getTurnTracker().getCurrentTurn() != playerIndex)
		{
			this.errorMessage = "Invalid Move - Not player's turn";
			return false;
		}
		return true;
	}
	
	private boolean checkStatus(String status)
	{
		this.errorMessage = "";
		
		if(!status.equalsIgnoreCase(model.getTurnTracker().getStatus()))
		{
			this.errorMessage = "Invalid Move - Move cannot be made at this point in round";
			return false;
		}
		return true;
	}
	
}
