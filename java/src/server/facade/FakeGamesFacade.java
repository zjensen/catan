package server.facade;

import shared.communication.games.CreateGame_Input;
import shared.communication.games.JoinGame_Input;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class FakeGamesFacade implements IGamesFacade {

	private final String gamesListJSON = "[{\"title\": \"Default Game\",\"id\": 0,\"players\": [{\"color\": \"blue\",\"name\": \"Sam\",\"id\": 0},{\"color\": \"blue\",\"name\": \"Brooke\",\"id\": 1},{\"color\": \"red\",\"name\": \"Pete\",\"id\": 10},{\"color\": \"green\",\"name\": \"Mark\",\"id\": 11}]},"
			+ "{\"title\": \"AI Game\",\"id\": 1,\"players\": [{\"color\": \"orange\",\"name\": \"Pete\",\"id\": 10},{\"color\": \"green\",\"name\": \"Quinn\",\"id\": -2},{\"color\": \"white\",\"name\": \"Hannah\",\"id\": -3},{\"color\": \"red\",\"name\": \"Ken\",\"id\": -4}]},"
			+ "{\"title\": \"Empty Game\",\"id\": 2,\"players\": [{\"color\": \"red\",\"name\": \"Sam\",\"id\": 0},{\"color\": \"blue\",\"name\": \"Brooke\",\"id\": 1},{\"color\": \"red\",\"name\": \"Pete\",\"id\": 10},{\"color\": \"green\",\"name\": \"Mark\",\"id\": 11}]}]";

	@Override
	public JsonElement create(CreateGame_Input params) {
		String newGameTitle = params.getName();
		String response = String.format(
				"{\"title\": \"%s\",\"id\": 1,\"players\": [{},{},{},{}]}",
				newGameTitle);
		return new JsonParser().parse(response).getAsJsonObject();
	}

	@Override
	public JsonElement join(JoinGame_Input params) {
		if (params.getColor().equals("blue"))
			if (params.getId() == 0 || params.getId() == 2)
				return new JsonPrimitive("Success");

		return new JsonPrimitive(
				"The player could not be added to the specified game.");
	}

	@Override
	public JsonElement list() {
		return new JsonParser().parse(gamesListJSON).getAsJsonObject();
	}

}
