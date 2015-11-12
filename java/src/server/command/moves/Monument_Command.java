package server.command.moves;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.moves.Monument_Input;

public class Monument_Command extends ServerCommand {

	private Monument_Input params = null;
	
	/**
	 * Command object for placing a monument
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public Monument_Command(HttpExchange exchange)
	{
		super(exchange);
		//here we will deserialize the JSON into a Monument_Input object
	}

	@Override
	public JsonElement execute() throws ServerInvalidRequestException
	{
		return execute(super.json);
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		// TODO get params from json
		params = gson.fromJson(json, Monument_Input.class);
		
		return ServerManager.instance().getMovesFacade().monument(params, super.playerId, super.gameId);
	}

}
