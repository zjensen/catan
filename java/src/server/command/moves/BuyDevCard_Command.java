package server.command.moves;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.moves.BuyDevCard_Input;

public class BuyDevCard_Command extends ServerCommand {

	private BuyDevCard_Input params = null;
	
	/**
	 * Command object for buying a dev card
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public BuyDevCard_Command(HttpExchange exchange)
	{
		super(exchange);
	}

	@Override
	public JsonElement execute() throws ServerInvalidRequestException
	{
		return execute(super.json);
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		params = gson.fromJson(json, BuyDevCard_Input.class);
		
		return ServerManager.instance().getMovesFacade().buyDevCard(params, super.playerId, super.gameId);
	}

}
