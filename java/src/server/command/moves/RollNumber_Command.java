package server.command.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;

import server.command.ExchangeWrapper;
import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.moves.RollNumber_Input;

public class RollNumber_Command extends ServerCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4998310085372441932L;
	private RollNumber_Input params = null;
	
	/**
	 * Command object for rolling the dice
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public RollNumber_Command(ExchangeWrapper exchange)
	{
		super(exchange);
	}

	@Override
	public JsonElement execute() throws ServerInvalidRequestException
	{
		return execute(this.json);
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
		params = gson.fromJson(json, RollNumber_Input.class);
		
		JsonElement result = ServerManager.instance().getMovesFacade().rollNumber(params, super.playerId, super.gameId);
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
