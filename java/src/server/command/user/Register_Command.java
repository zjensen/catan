package server.command.user;

import com.google.gson.JsonElement;

import server.command.ICommand;
import server.facade.UserFacade;
import shared.communication.user.Register_Input;

public class Register_Command implements ICommand {

	private Register_Input params = null;
	
	/**
	 * Command object for registering a new user
	 * @param json
	 */
	public Register_Command(String json)
	{
		//here we will deserialize the JSON into a Register_Input object
	}

	@Override
	public JsonElement execute()
	{
		return UserFacade.register(params);
	}

}
