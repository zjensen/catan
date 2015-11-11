package server.command.moves;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.moves.AcceptTrade_Input;

public class AcceptTrade_Command extends ServerCommand {

	private AcceptTrade_Input params = null;
	
	/**
	 * Command object for accepting a trade
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public AcceptTrade_Command(HttpExchange exchange)
	{
		super(exchange);
		//here we will deserialize the JSON into a AcceptTrade_Input object
	}

	@Override
	public JsonElement execute() throws ServerInvalidRequestException
	{
		return execute(super.json);
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		//TODO get params from json
		params = gson.fromJson(json, AcceptTrade_Input.class);
		
		return ServerManager.instance().getMovesFacade().acceptTrade(params, super.playerId, super.gameId);
	}

}
