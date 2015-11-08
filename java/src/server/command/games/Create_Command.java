package server.command.games;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.games.CreateGame_Input;

public class Create_Command extends ServerCommand {

	private CreateGame_Input params = null;
	
	/**
	 * Command object for creating a game
	 * @param json
	 */
	public Create_Command(HttpExchange exchange)
	{
		super(exchange);
		//here we will deserialize the JSON into a CreateGame_Input object
	}

	@Override
	public JsonElement execute()
	{
		return ServerManager.instance().getGamesFacade().create(params);
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		// TODO Auto-generated method stub
		return null;
	}

}
