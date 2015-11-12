package server.command.moves;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.moves.OfferTrade_Input;

public class OfferTrade_Command extends ServerCommand {

	private OfferTrade_Input params = null;
	
	/**
	 * Command object for offering a trade
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public OfferTrade_Command(HttpExchange exchange)
	{
		super(exchange);
		//here we will deserialize the JSON into a OfferTrade_Input object
	}

	@Override
	public JsonElement execute() throws ServerInvalidRequestException
	{
		return execute(super.json);
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		// TODO get params from json
		params = gson.fromJson(json, OfferTrade_Input.class);
		
		return ServerManager.instance().getMovesFacade().offerTrade(params, super.playerId, super.gameId);
	}

}
