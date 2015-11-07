package server.command.moves;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.moves.MaritimeTrade_Input;

public class MaritimeTrade_Command extends ServerCommand {

	private MaritimeTrade_Input params = null;
	
	/**
	 * Command object for maritime trade
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public MaritimeTrade_Command(HttpExchange exchange)
	{
		super(exchange);
		//here we will deserialize the JSON into a MaritimeTrade_Input object
	}

	@Override
	public JsonElement execute()
	{
		return ServerManager.instance().getMovesFacade().maritimeTrade(params, super.playerId, super.gameId);
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		// TODO Auto-generated method stub
		return null;
	}

}
