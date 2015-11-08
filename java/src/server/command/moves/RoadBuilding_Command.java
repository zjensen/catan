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
	public JsonElement execute()
	{
		return ServerManager.instance().getMovesFacade().roadBuilding(params, super.playerId, super.gameId);
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		// TODO Auto-generated method stub
		return null;
	}

}
