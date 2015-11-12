package server.command.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;

import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.moves.BuildRoad_Input;

public class BuildRoad_Command extends ServerCommand {

	private BuildRoad_Input params = null;
	
	/**
	 * Command object for building a road
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public BuildRoad_Command(HttpExchange exchange)
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
		params = gson.fromJson(json, BuildRoad_Input.class);
		
		JsonObject paramsJSON = (JsonObject) new JsonParser().parse(json);
		JsonObject location = (JsonObject) paramsJSON.get("roadLocation");
		params.setRoadLocation(extractEdgeLocation(location));
		
		return ServerManager.instance().getMovesFacade().buildRoad(params, super.playerId, super.gameId);
	}

}
