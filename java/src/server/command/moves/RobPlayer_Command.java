package server.command.moves;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.moves.RobPlayer_Input;

public class RobPlayer_Command extends ServerCommand {

	private RobPlayer_Input params = null;
	
	/**
	 * Command object for robbing a player
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public RobPlayer_Command(HttpExchange exchange)
	{
		super(exchange);
		//here we will deserialize the JSON into a RobPlayer_Input object
	}

	@Override
	public JsonElement execute() throws ServerInvalidRequestException
	{
		return execute(super.json);
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		// TODO get params from json
		params = gson.fromJson(json, RobPlayer_Input.class);
		
		return ServerManager.instance().getMovesFacade().robPlayer(params, super.playerId, super.gameId);
	}

}
