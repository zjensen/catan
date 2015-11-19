package server.facade;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import shared.communication.moves.AcceptTrade_Input;
import shared.communication.moves.BuildCity_Input;
import shared.communication.moves.BuildRoad_Input;
import shared.communication.moves.BuildSettlement_Input;
import shared.communication.moves.BuyDevCard_Input;
import shared.communication.moves.DiscardCards_Input;
import shared.communication.moves.FinishTurn_Input;
import shared.communication.moves.MaritimeTrade_Input;
import shared.communication.moves.Monopoly_Input;
import shared.communication.moves.Monument_Input;
import shared.communication.moves.OfferTrade_Input;
import shared.communication.moves.RoadBuilding_Input;
import shared.communication.moves.RobPlayer_Input;
import shared.communication.moves.RollNumber_Input;
import shared.communication.moves.SendChat_Input;
import shared.communication.moves.Soldier_Input;
import shared.communication.moves.YearOfPlenty_Input;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class FakeMovesFacade extends IMovesFacade {

	JsonElement gameModel;

	public FakeMovesFacade() {
		StringBuilder result = new StringBuilder("");
		File file = new File("java/MovesFacadeTestJSON.txt");

		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}
			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		String jsonString = result.toString();

		gameModel = new JsonParser().parse(jsonString).getAsJsonObject();
	}

	@Override
	public JsonElement acceptTrade(AcceptTrade_Input params, int playerID,
			int gameID) {
		if (params.getType().equals("acceptTrade"))
			if (params.isWillAccept())
				if (0 <= params.getPlayerIndex()
						&& params.getPlayerIndex() <= 3)
					return gameModel;
		return new JsonPrimitive("Invalid request");
	}

	@Override
	public JsonElement buildCity(BuildCity_Input params, int playerID,
			int gameID) {
		if (params.getType().equals("buildCity"))
			if (0 <= params.getPlayerIndex() && params.getPlayerIndex() <= 3)
				return gameModel;
		return new JsonPrimitive("Invalid request");
	}

	@Override
	public JsonElement buildRoad(BuildRoad_Input params, int playerID,
			int gameID) {
		if (params.getType().equals("buildRoad"))
			if (0 <= params.getPlayerIndex() && params.getPlayerIndex() <= 3)
				return gameModel;
		return new JsonPrimitive("Invalid request");
	}

	@Override
	public JsonElement buildSettlement(BuildSettlement_Input params,
			int playerID, int gameID) {
		if (params.getType().equals("buildSettlement"))
			if (0 <= params.getPlayerIndex() && params.getPlayerIndex() <= 3)
				return gameModel;
		return new JsonPrimitive("Invalid request");
	}

	@Override
	public JsonElement buyDevCard(BuyDevCard_Input params, int playerID,
			int gameID) {
		if (params.getType().equals("buyDevCard"))
			if (0 <= params.getPlayerIndex() && params.getPlayerIndex() <= 3)
				return gameModel;
		return new JsonPrimitive("Invalid request");
	}

	@Override
	public JsonElement discardCards(DiscardCards_Input params, int playerID,
			int gameID) {
		if (params.getType().equals("discardCards"))
			if (0 <= params.getPlayerIndex() && params.getPlayerIndex() <= 3)
				return gameModel;
		return new JsonPrimitive("Invalid request");
	}

	@Override
	public JsonElement finishTurn(FinishTurn_Input params, int playerID,
			int gameID) {
		if (params.getType().equals("finishTurn"))
			if (0 <= params.getPlayerIndex() && params.getPlayerIndex() <= 3)
				return gameModel;
		return new JsonPrimitive("Invalid request");
	}

	@Override
	public JsonElement maritimeTrade(MaritimeTrade_Input params, int playerID,
			int gameID) {
		if (params.getType().equals("maritimeTrade"))
			if (0 <= params.getPlayerIndex() && params.getPlayerIndex() <= 3)
				return gameModel;
		return new JsonPrimitive("Invalid request");
	}

	@Override
	public JsonElement monopoly(Monopoly_Input params, int playerID, int gameID) {
		if (params.getType().equals("monopoly"))
			if (0 <= params.getPlayerIndex() && params.getPlayerIndex() <= 3)
				return gameModel;
		return new JsonPrimitive("Invalid request");
	}

	@Override
	public JsonElement monument(Monument_Input params, int playerID, int gameID) {
		if (params.getType().equals("monument"))
			if (0 <= params.getPlayerIndex() && params.getPlayerIndex() <= 3)
				return gameModel;
		return new JsonPrimitive("Invalid request");
	}

	@Override
	public JsonElement offerTrade(OfferTrade_Input params, int playerID,
			int gameID) {
		if (params.getType().equals("offerTrade"))
			if (0 <= params.getPlayerIndex() && params.getPlayerIndex() <= 3)
				return gameModel;
		return new JsonPrimitive("Invalid request");
	}

	@Override
	public JsonElement roadBuilding(RoadBuilding_Input params, int playerID,
			int gameID) {
		if (params.getType().equals("roadBuilding"))
			if (0 <= params.getPlayerIndex() && params.getPlayerIndex() <= 3)
				return gameModel;
		return new JsonPrimitive("Invalid request");
	}

	@Override
	public JsonElement robPlayer(RobPlayer_Input params, int playerID,
			int gameID) {
		if (params.getType().equals("robPlayer"))
			if (0 <= params.getPlayerIndex() && params.getPlayerIndex() <= 3)
				if (params.getVictimIndex() != params.getPlayerIndex())
					return gameModel;
		return new JsonPrimitive("Invalid request");
	}

	@Override
	public JsonElement rollNumber(RollNumber_Input params, int playerID,
			int gameID) {
		if (params.getType().equals("rollNumber"))
			if (0 <= params.getPlayerIndex() && params.getPlayerIndex() <= 3)
				if (params.getNumber() > 0 && params.getNumber() < 13)
					return gameModel;
		return new JsonPrimitive("Invalid request");
	}

	@Override
	public JsonElement sendChat(SendChat_Input params, int playerID, int gameID) {
		if (params.getType().equals("sendChat"))
			if (0 <= params.getPlayerIndex() && params.getPlayerIndex() <= 3)
				return gameModel;
		return new JsonPrimitive("Invalid request");
	}

	@Override
	public JsonElement soldier(Soldier_Input params, int playerID, int gameID) {
		if (params.getType().equals("soldier"))
			if (0 <= params.getPlayerIndex() && params.getPlayerIndex() <= 3)
				if (params.getVictimIndex() != params.getPlayerIndex())
					return gameModel;
		return new JsonPrimitive("Invalid request");
	}

	@Override
	public JsonElement yearOfPlenty(YearOfPlenty_Input params, int playerID,
			int gameID) {
		if (params.getType().equals("yearOfPlenty"))
			if (0 <= params.getPlayerIndex() && params.getPlayerIndex() <= 3)
				return gameModel;
		return new JsonPrimitive("Invalid request");
	}

}
