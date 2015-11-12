package server.command.moves;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.moves.DiscardCards_Input;

public class DiscardCards_Command extends ServerCommand {

	private DiscardCards_Input params = null;
	
	/**
	 * Command object for discarding cards
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public DiscardCards_Command(HttpExchange exchange)
	{
		super(exchange);
		//here we will deserialize the JSON into a DiscardCards_Input object
	}

	@Override
	public JsonElement execute() throws ServerInvalidRequestException
	{
		return execute(super.json);
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		//TODO get params from json
		params = gson.fromJson(json, DiscardCards_Input.class);
		
		return ServerManager.instance().getMovesFacade().discardCards(params, super.playerId, super.gameId);
	}

}
