package server.command.moves;

import com.google.gson.JsonElement;
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
		//here we will deserialize the JSON into a SendChat_Input object
	}

	@Override
	public JsonElement execute()
	{
		return ServerManager.instance().getMovesFacade().sendChat(params, super.playerId, super.gameId);
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		// TODO Auto-generated method stub
		return null;
	}

}