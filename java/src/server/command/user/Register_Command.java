package server.command.user;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.user.Register_Input;

public class Register_Command extends ServerCommand {

	private Register_Input params = null;
	
	/**
	 * Command object for registering a new user
	 * @param json
	 */
	public Register_Command(HttpExchange exchange)
	{
		super(exchange);
		//here we will deserialize the JSON into a Register_Input object
	}

	@Override
	public JsonElement execute()
	{
		return ServerManager.instance().getUserFacade().register(params);
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		// TODO Auto-generated method stub
		return null;
	}

}
