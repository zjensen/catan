package server.command.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;

import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.moves.YearOfPlenty_Input;
import shared.definitions.ResourceType;

public class YearOfPlenty_Command extends ServerCommand {

	private YearOfPlenty_Input params = null;
	
	/**
	 * Command object for playing a year of plenty card
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public YearOfPlenty_Command(HttpExchange exchange)
	{
		super(exchange);
	}

	@Override
	public JsonElement execute() throws ServerInvalidRequestException
	{
		return execute(super.json);
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		params = gson.fromJson(json, YearOfPlenty_Input.class);
		JsonObject paramsJSON = (JsonObject) new JsonParser().parse(json);
		params.setResource(ResourceType.valueOf(paramsJSON.get("resource1").getAsString().toUpperCase()));
		params.setResource1(ResourceType.valueOf(paramsJSON.get("resource2").getAsString().toUpperCase()));
		return ServerManager.instance().getMovesFacade().yearOfPlenty(params, super.playerId, super.gameId);
	}

}
