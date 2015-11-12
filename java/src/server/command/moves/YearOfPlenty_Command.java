package server.command.moves;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.moves.YearOfPlenty_Input;

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
		//here we will deserialize the JSON into a YearOfPlenty_Input object
	}

	@Override
	public JsonElement execute() throws ServerInvalidRequestException
	{
		return execute(super.json);
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		// TODO get params from json
		params = gson.fromJson(json, YearOfPlenty_Input.class);
		
		return ServerManager.instance().getMovesFacade().yearOfPlenty(params, super.playerId, super.gameId);
	}

}
