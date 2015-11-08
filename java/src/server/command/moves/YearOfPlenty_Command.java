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
	public JsonElement execute()
	{
		return ServerManager.instance().getMovesFacade().yearOfPlenty(params, super.playerId, super.gameId);
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		// TODO Auto-generated method stub
		return null;
	}

}
