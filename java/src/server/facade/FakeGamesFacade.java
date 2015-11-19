package server.facade;

import server.main.ServerInvalidRequestException;
import shared.communication.games.CreateGame_Input;
import shared.communication.games.JoinGame_Input;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class FakeGamesFacade implements IGamesFacade {

	private final String gamesListJSON = "[{\"title\": \"Default Game\",\"id\": 0,\"players\": [{\"color\": \"blue\",\"name\": \"Sam\",\"id\": 0},{\"color\": \"blue\",\"name\": \"Brooke\",\"id\": 1},{\"color\": \"red\",\"name\": \"Pete\",\"id\": 10},{\"color\": \"green\",\"name\": \"Mark\",\"id\": 11}]}]";

	@Override
	public JsonElement create(CreateGame_Input params) {
		String newGameTitle = params.getName();
		String response = String.format(
				"{\"title\": \"%s\",\"id\": 1,\"players\": [{},{},{},{}]}",
				newGameTitle);
		return new JsonParser().parse(response).getAsJsonObject();
	}

	@Override
	public JsonElement join(JoinGame_Input params, int playerId) throws ServerInvalidRequestException {
		if (params.getColor().equals("blue"))
			if (params.getId() == 0 || params.getId() == 2)
				return new JsonPrimitive("Success");

		return new JsonPrimitive(
				"The player could not be added to the specified game.");
	}

	@Override
	public JsonElement list() {
		JsonObject obj = new JsonObject();
//		obj.add(property, value);
		return new JsonParser().parse(gamesListJSON).getAsJsonObject();
	}

}
