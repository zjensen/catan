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
	public JsonElement execute() throws ServerInvalidRequestException
	{
		return execute(this.json);
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		//TODO get params from json
		params = gson.fromJson(json, RollNumber_Input.class);
		
		return ServerManager.instance().getMovesFacade().rollNumber(params, super.playerId, super.gameId);
	}

}
