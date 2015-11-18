package server.command.games;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;

import server.command.ExchangeWrapper;
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
	public Create_Command(ExchangeWrapper exchange)
	{
		super(exchange);
		//here we will deserialize the JSON into a CreateGame_Input object
	}

	@Override
	public JsonElement execute() throws ServerInvalidRequestException
	{
		if(!hasUserCookie)
		{
			return new JsonPrimitive("The catan.user HTTP cookie is missing.  You must login before calling this method.");
		}
		try
		{
			params = gson.fromJson(json, CreateGame_Input.class);
			return ServerManager.instance().getGamesFacade().create(params);
		} 
		catch(Exception e)
		{
			throw new ServerInvalidRequestException("Malformed JSON");
		}
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		// TODO Auto-generated method stub
		return null;
	}

}
