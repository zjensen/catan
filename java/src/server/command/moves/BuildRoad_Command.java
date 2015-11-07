package server.command.moves;

import com.google.gson.JsonElement;
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
		//here we will deserialize the JSON into a BuildRoad_Input object
	}

	@Override
	public JsonElement execute()
	{
		return ServerManager.instance().getMovesFacade().buildRoad(params, super.playerId, super.gameId);
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		// TODO Auto-generated method stub
		return null;
	}

}
