package server.command.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;

import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.moves.MaritimeTrade_Input;
import shared.definitions.ResourceType;

public class MaritimeTrade_Command extends ServerCommand {

	private MaritimeTrade_Input params = null;
	
	/**
	 * Command object for maritime trade
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public MaritimeTrade_Command(HttpExchange exchange)
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
		params = gson.fromJson(json, MaritimeTrade_Input.class);
		JsonObject paramsJSON = (JsonObject) new JsonParser().parse(json);
		params.setInputResource(ResourceType.valueOf(paramsJSON.get("inputResource").getAsString().toUpperCase()));
		params.setOutputResource(ResourceType.valueOf(paramsJSON.get("outputResource").getAsString().toUpperCase()));
		return ServerManager.instance().getMovesFacade().maritimeTrade(params, super.playerId, super.gameId);
	}

}
