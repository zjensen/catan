package server.command.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;

import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.moves.Monopoly_Input;
import shared.definitions.ResourceType;

public class Monopoly_Command extends ServerCommand {
	
	private Monopoly_Input params;

	public Monopoly_Command(HttpExchange exchange) {
		super(exchange);
	}

	@Override
	public JsonElement execute() throws ServerInvalidRequestException 
	{
		return execute(super.json);
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		params = gson.fromJson(json, Monopoly_Input.class);
		JsonObject paramsJSON = (JsonObject) new JsonParser().parse(json);
		params.setResource(ResourceType.valueOf(paramsJSON.get("resource").getAsString().toUpperCase()));
		return ServerManager.instance().getMovesFacade().monopoly(params, super.playerId, super.gameId);
	}

}
