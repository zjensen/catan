package server.command.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;

import server.command.ExchangeWrapper;
import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.moves.FinishTurn_Input;

public class FinishTurn_Command extends ServerCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8431913533686189932L;
	private FinishTurn_Input params = null;
	
	/**
	 * Command object for finishing a turn
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public FinishTurn_Command(ExchangeWrapper exchange)
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
		if(!hasUserCookie)
		{
			return new JsonPrimitive("The catan.user HTTP cookie is missing.  You must login before calling this method.");
		}
		else if(!hasGameCookie)
		{
			return new JsonPrimitive("The catan.game HTTP cookie is missing.  You must join a game before calling this method.");
		}
		params = gson.fromJson(json, FinishTurn_Input.class);
		
		JsonElement result = ServerManager.instance().getMovesFacade().finishTurn(params, super.playerId, super.gameId);
		if(result == null)
		{
			result = new JsonPrimitive("Invalid Move");
		}
		else
		{
			super.addCommand();
		}
		return result;
	}

}
