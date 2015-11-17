package server.facade;

import com.google.gson.JsonElement;

import server.manager.ServerManager;
import shared.communication.moves.*;
import shared.models.ClientModel;
import shared.utils.Interpreter;

public abstract class IMovesFacade {
	
	private ClientModel model = null;
	private Interpreter interpreter = new Interpreter();
	private String errorMessage = "";
	
	public ClientModel getModel()
	{
		return model;
	}

	public void setModel(ClientModel model)
	{
		this.model = model;
	}
	
	/** 
	 * serializes the current ClientModel and returns it
	 * @return model serialized as a JsonElement for sending back to client 
	 */
	public JsonElement serializeModel()
	{
		return interpreter.serialize(model);
	}

	/**
	 * Check to see if a gameID and playerID are valid, and if so sets the model
	 * @param gameID
	 * @param playerID
	 * @return true if game exists and player is in that game, else false
	 */
	protected boolean setModel(int playerID,int gameID)
	{
		this.model = null; //defaults global model to null
		ClientModel modelToValidate = ServerManager.instance().getGamesManager().getClientModelById(gameID); //gets model that matches param
		if(modelToValidate == null)
		{
			return false; //no model matches this gameID
		}
		else if(modelToValidate.getPlayerIndexByID(playerID) == -1)
		{
			return false; //user is not in this game
		}
		this.model = modelToValidate; //set the model
		return true; //we r ready 2 roll
	}
			
	public abstract JsonElement acceptTrade(AcceptTrade_Input params, int playerID, int gameID);
	
	public abstract JsonElement buildCity(BuildCity_Input params, int playerID, int gameID);
	
	public abstract JsonElement buildRoad(BuildRoad_Input params, int playerID, int gameID);
	
	public abstract JsonElement buildSettlement(BuildSettlement_Input params, int playerID, int gameID);
	
	public abstract JsonElement buyDevCard(BuyDevCard_Input params, int playerID, int gameID);
	
	public abstract JsonElement discardCards(DiscardCards_Input params, int playerID, int gameID);
	
	public abstract JsonElement finishTurn(FinishTurn_Input params, int playerID, int gameID);
	
	public abstract JsonElement maritimeTrade(MaritimeTrade_Input params, int playerID, int gameID);
	
	public abstract JsonElement monopoly(Monopoly_Input params, int playerID, int gameID);
	
	public abstract JsonElement monument(Monument_Input params, int playerID, int gameID);
	
	public abstract JsonElement offerTrade(OfferTrade_Input params, int playerID, int gameID);
	
	public abstract JsonElement roadBuilding(RoadBuilding_Input params, int playerID, int gameID);
	
	public abstract JsonElement robPlayer(RobPlayer_Input params, int playerID, int gameID);
	
	public abstract JsonElement rollNumber(RollNumber_Input params, int playerID, int gameID);
	
	public abstract JsonElement sendChat(SendChat_Input params, int playerID, int gameID);
	
	public abstract JsonElement soldier(Soldier_Input params, int playerID, int gameID);
	
	public abstract JsonElement yearOfPlenty(YearOfPlenty_Input params, int playerID, int gameID);
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	public boolean canAcceptTrade(AcceptTrade_Input params)
	{
		if(!checkStatus("playing"))
		{
			return false;
		}
		else if(this.getModel().getTradeOffer() == null) //must have trade going on
		{
			return false;
		}
		return(this.getModel().canAcceptTrade(params));
	}

	
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
		return (this.getModel().canBuildCity(params));
	}

	
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
		return (this.getModel().canBuildRoad(params));
	}

	
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
		return (this.getModel().canBuildSettlement(params));
	}

	
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
		return (this.getModel().canBuyDevCard(params));
	}

	
	public boolean canDiscardCards(DiscardCards_Input params)
	{
		if(!checkStatus("discarding"))
		{
			return false;
		}
		return(this.getModel().canDiscardCards(params));
	}

	
	public boolean canFinishTurn(FinishTurn_Input params)
	{
		if(!isPlayersTurn(params.getPlayerIndex()))
		{
			return false;
		}
		switch(this.getModel().getTurnTracker().getStatus().toLowerCase())
		{
			case "firstround":
				return this.getModel().canFinishTurnFirstRound(params.getPlayerIndex());
			case "secondround":
				return this.getModel().canFinishTurnSecondRound(params.getPlayerIndex());
			case "playing":
				return true;//this.getModel().getTradeOffer()==null; //ensures user does not end turn when trade offer is waiting
			case "robbing":
			case "rolling":
			case "discarding":
			default:
				return false;	
		}
	}

	
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
		return this.getModel().canMaritimeTrade(params);
	}
	
	
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
		return this.getModel().canMonopoly(params);
	}

	
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
		return this.getModel().canMonument(params);
	}

	
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
		else if(this.getModel().getTradeOffer() != null) //can't already have trade going
		{
			return false;
		}
		else if(params.getPlayerIndex()==params.getReceiver())
		{
			return false;
		}
		return(this.getModel().canOfferTrade(params));
	}

	
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
		return (this.getModel().canRoadBuilding(params));
	}

	
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
		return this.getModel().canRobPlayer(params);
	}

	
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

	
	public boolean canSendChat(SendChat_Input params)
	{
		return(params.getContent() != null && !params.getContent().isEmpty());
	}

	
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
		return(this.getModel().canSoldier(params));
	}

	
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
		return( this.getModel().canYearOfPlenty(params));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Can-Do Helpers
	
	private boolean isPlayersTurn(int playerIndex)
	{
		if(getModel().getTurnTracker().getCurrentTurn() != playerIndex)
		{
			return false;
		}
		return true;
	}
	
	private boolean checkStatus(String status)
	{
		if(!status.equalsIgnoreCase(getModel().getTurnTracker().getStatus()))
		{
			return false;
		}
		return true;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}
}
