package server.command.games;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.command.ExchangeWrapper;
import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;

public class List_Command extends ServerCommand {

	/**
	 * Command object for listing all games
	 */
	public List_Command(ExchangeWrapper exchange)
	{
		super(exchange);
	}
	
	@Override
	public JsonElement execute()
	{
		return ServerManager.instance().getGamesFacade().list();
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		// TODO Auto-generated method stub
		return null;
	}

}
