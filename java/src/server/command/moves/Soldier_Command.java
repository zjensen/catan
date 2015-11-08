package server.command.moves;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.moves.Soldier_Input;

public class Soldier_Command extends ServerCommand {

	private Soldier_Input params = null;
	
	/**
	 * Command object for playing a knight card
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public Soldier_Command(HttpExchange exchange)
	{
		super(exchange);
		//here we will deserialize the JSON into a Soldier_Input object
	}

	@Override
	public JsonElement execute()
	{
		return ServerManager.instance().getMovesFacade().soldier(params, super.playerId, super.gameId);
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		// TODO Auto-generated method stub
		return null;
	}

}
