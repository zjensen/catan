package server.command.moves;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.moves.RollNumber_Input;

public class RollNumber_Command extends ServerCommand {

	private RollNumber_Input params = null;
	
	/**
	 * Command object for rolling the dice
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public RollNumber_Command(HttpExchange exchange)
	{
		super(exchange);
		//here we will deserialize the JSON into a RollNumber_Input object
	}

	@Override
	public JsonElement execute()
	{
		return ServerManager.instance().getMovesFacade().rollNumber(params, super.playerId, super.gameId);
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		// TODO Auto-generated method stub
		return null;
	}

}
