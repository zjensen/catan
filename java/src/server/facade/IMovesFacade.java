package server.facade;

import com.google.gson.JsonElement;

import shared.communication.moves.*;

public interface IMovesFacade {
		
	public boolean canAcceptTrade(AcceptTrade_Input params);
	
	public boolean canBuildCity(BuildCity_Input params);
	
	public boolean canBuildRoad(BuildRoad_Input params);
	
	public boolean canBuildSettlement(BuildSettlement_Input params);
	
	public boolean canBuyDevCard(BuyDevCard_Input params);
	
	public boolean canDiscardCards(DiscardCards_Input params);
	
	public boolean canFinishTurn(FinishTurn_Input params);
	
	public boolean canMaritimeTrade(MaritimeTrade_Input params);
	
	public boolean canMonument(Monument_Input params);
	
	public boolean canMonopoly(Monopoly_Input params);
	
	public boolean canOfferTrade(OfferTrade_Input params);
	
	public boolean canRoadBuilding(RoadBuilding_Input params);
	
	public boolean canRobPlayer(RobPlayer_Input params);
	
	public boolean canRollNumber(RollNumber_Input params);
	
	public boolean canSendChat(SendChat_Input params);
	
	public boolean canSoldier(Soldier_Input params);
	
	public boolean canYearOfPlenty(YearOfPlenty_Input params);
	
	public JsonElement acceptTrade(AcceptTrade_Input params, int playerID, int gameID);
	
	public JsonElement buildCity(BuildCity_Input params, int playerID, int gameID);
	
	public JsonElement buildRoad(BuildRoad_Input params, int playerID, int gameID);
	
	public JsonElement buildSettlement(BuildSettlement_Input params, int playerID, int gameID);
	
	public JsonElement buyDevCard(BuyDevCard_Input params, int playerID, int gameID);
	
	public JsonElement discardCards(DiscardCards_Input params, int playerID, int gameID);
	
	public JsonElement finishTurn(FinishTurn_Input params, int playerID, int gameID);
	
	public JsonElement maritimeTrade(MaritimeTrade_Input params, int playerID, int gameID);
	
	public JsonElement monopoly(Monopoly_Input params, int playerID, int gameID);
	
	public JsonElement monument(Monument_Input params, int playerID, int gameID);
	
	public JsonElement offerTrade(OfferTrade_Input params, int playerID, int gameID);
	
	public JsonElement roadBuilding(RoadBuilding_Input params, int playerID, int gameID);
	
	public JsonElement robPlayer(RobPlayer_Input params, int playerID, int gameID);
	
	public JsonElement rollNumber(RollNumber_Input params, int playerID, int gameID);
	
	public JsonElement sendChat(SendChat_Input params, int playerID, int gameID);
	
	public JsonElement soldier(Soldier_Input params, int playerID, int gameID);
	
	public JsonElement yearOfPlenty(YearOfPlenty_Input params, int playerID, int gameID);
	
	public default int testFunction(int i)
	{
		return i*5;
	}
}
