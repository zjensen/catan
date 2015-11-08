package server.command.game;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.game.GameModel_Input;

public class Model_Command extends ServerCommand {

	private GameModel_Input params = null;
	
	/**
	 * Command object for getting the client model
	 * @param version
	 */
	public Model_Command(HttpExchange exchange)
	{
		super(exchange);
		//here we will deserialize the JSON into a GameModel_Input object
	}

	@Override
	public JsonElement execute()
	{
		return ServerManager.instance().getGameFacade().currentModel(params);
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		// TODO Auto-generated method stub
		return null;
	}

}
