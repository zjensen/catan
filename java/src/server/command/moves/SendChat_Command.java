package server.command.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;

import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.moves.SendChat_Input;

public class SendChat_Command extends ServerCommand{
	
	private SendChat_Input params = null;

	/**
	 * Command object for sending a chat
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public SendChat_Command(HttpExchange exchange)
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
		params = gson.fromJson(json, SendChat_Input.class);
		return ServerManager.instance().getMovesFacade().sendChat(params, super.playerId, super.gameId);
	}

}
