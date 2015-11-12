package server.command.moves;

import com.google.gson.JsonElement;
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
		//here we will deserialize the JSON into a RoadBuilding_Input object
	}

	@Override
	public JsonElement execute() throws ServerInvalidRequestException
	{
		return execute(super.json);
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		// TODO get params from json
		params = gson.fromJson(json, RoadBuilding_Input.class);
		
		return ServerManager.instance().getMovesFacade().roadBuilding(params, super.playerId, super.gameId);
	}

}
