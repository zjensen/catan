package server.command.games;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.games.JoinGame_Input;

public class Join_Command extends ServerCommand {

	private JoinGame_Input params = null;
	
	/**
	 * Command object for joining a game
	 * @param json
	 */
	public Join_Command(HttpExchange exchange)
	{
		super(exchange);
		//here we will deserialize the JSON into a JoinGame_Input object
	}

	@Override
	public JsonElement execute()
	{
		return ServerManager.instance().getGamesFacade().join(params);
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		// TODO Auto-generated method stub
		return null;
	}

}
