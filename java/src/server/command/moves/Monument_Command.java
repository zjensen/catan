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
	public JsonElement execute()
	{
		return ServerManager.instance().getMovesFacade().monument(params, super.playerId, super.gameId);
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		// TODO Auto-generated method stub
		return null;
	}

}
