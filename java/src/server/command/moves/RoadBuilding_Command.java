package server.command.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;

import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.moves.RoadBuilding_Input;

public class RoadBuilding_Command extends ServerCommand {

	private RoadBuilding_Input params = null;
	
	/**
	 * Command object for road building card
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public RoadBuilding_Command(HttpExchange exchange)
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
		params = gson.fromJson(json, RoadBuilding_Input.class);
		JsonObject paramsJSON = (JsonObject) new JsonParser().parse(json);
		JsonObject spot1 = (JsonObject) paramsJSON.get("spot1");
		JsonObject spot2 = (JsonObject) paramsJSON.get("spot2");
		params.setSpot1(extractEdgeLocation(spot1));
		params.setSpot2(extractEdgeLocation(spot2));
		return ServerManager.instance().getMovesFacade().roadBuilding(params, super.playerId, super.gameId);
	}

}
