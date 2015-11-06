package server.command.user;

import com.google.gson.JsonElement;

import server.command.ICommand;
import server.facade.MovesFacade;
import server.facade.UserFacade;
import shared.communication.user.Login_Input;

public class Login_Command implements ICommand{
	
	private Login_Input params = null;
	
	/**
	 * Command object for logging in
	 * @param json
	 */
	public Login_Command(String json)
	{
		//here we will deserialize the JSON into a Login_Input object
	}

	@Override
	public JsonElement execute()
	{
		return UserFacade.login(params);
	}
}
