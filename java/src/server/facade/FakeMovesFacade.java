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
import shared.models.ClientModel;

public class FakeMovesFacade implements IMovesFacade {

	private JsonElement serializeModel(ClientModel model) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public JsonElement acceptTrade(AcceptTrade_Input params, int playerID, int gameID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonElement buildCity(BuildCity_Input params, int playerID, int gameID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonElement buildRoad(BuildRoad_Input params, int playerID, int gameID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonElement buildSettlement(BuildSettlement_Input params, int playerID, int gameID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonElement buyDevCard(BuyDevCard_Input params, int playerID, int gameID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonElement discardCards(DiscardCards_Input params, int playerID, int gameID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonElement finishTurn(FinishTurn_Input params, int playerID, int gameID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonElement maritimeTrade(MaritimeTrade_Input params, int playerID, int gameID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonElement monument(Monument_Input params, int playerID, int gameID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonElement offerTrade(OfferTrade_Input params, int playerID, int gameID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonElement roadBuilding(RoadBuilding_Input params, int playerID, int gameID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonElement robPlayer(RobPlayer_Input params, int playerID, int gameID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonElement rollNumber(RollNumber_Input params, int playerID, int gameID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonElement sendChat(SendChat_Input params, int playerID, int gameID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonElement soldier(Soldier_Input params, int playerID, int gameID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonElement yearOfPlenty(YearOfPlenty_Input params, int playerID, int gameID) {
		// TODO Auto-generated method stub
		return null;
	}

}
