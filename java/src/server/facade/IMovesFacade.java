package server.facade;

import com.google.gson.JsonElement;

import shared.communication.moves.AcceptTrade_Input;
import shared.communication.moves.BuildCity_Input;
import shared.communication.moves.BuildRoad_Input;
import shared.communication.moves.BuildSettlement_Input;
import shared.communication.moves.BuyDevCard_Input;
import shared.communication.moves.DiscardCards_Input;
import shared.communication.moves.FinishTurn_Input;
import shared.communication.moves.MaritimeTrade_Input;
import shared.communication.moves.Monument_Input;
import shared.communication.moves.OfferTrade_Input;
import shared.communication.moves.RoadBuilding_Input;
import shared.communication.moves.RobPlayer_Input;
import shared.communication.moves.RollNumber_Input;
import shared.communication.moves.SendChat_Input;
import shared.communication.moves.Soldier_Input;
import shared.communication.moves.YearOfPlenty_Input;

public interface IMovesFacade {

	public JsonElement acceptTrade(AcceptTrade_Input params, int playerID, int gameID);
	
	public JsonElement buildCity(BuildCity_Input params, int playerID, int gameID);
	
	public JsonElement buildRoad(BuildRoad_Input params, int playerID, int gameID);
	
	public JsonElement buildSettlement(BuildSettlement_Input params, int playerID, int gameID);
	
	public JsonElement buyDevCard(BuyDevCard_Input params, int playerID, int gameID);
	
	public JsonElement discardCards(DiscardCards_Input params, int playerID, int gameID);
	
	public JsonElement finishTurn(FinishTurn_Input params, int playerID, int gameID);
	
	public JsonElement maritimeTrade(MaritimeTrade_Input params, int playerID, int gameID);
	
	public JsonElement monument(Monument_Input params, int playerID, int gameID);
	
	public JsonElement offerTrade(OfferTrade_Input params, int playerID, int gameID);
	
	public JsonElement roadBuilding(RoadBuilding_Input params, int playerID, int gameID);
	
	public JsonElement robPlayer(RobPlayer_Input params, int playerID, int gameID);
	
	public JsonElement rollNumber(RollNumber_Input params, int playerID, int gameID);
	
	public JsonElement sendChat(SendChat_Input params, int playerID, int gameID);
	
	public JsonElement soldier(Soldier_Input params, int playerID, int gameID);
	
	public JsonElement yearOfPlenty(YearOfPlenty_Input params, int playerID, int gameID);
}
