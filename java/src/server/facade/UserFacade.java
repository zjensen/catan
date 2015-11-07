package server.facade;

import shared.communication.user.Login_Input;
import shared.communication.user.Register_Input;
import com.google.gson.JsonElement;

//make all methods static
public class UserFacade implements IUserFacade{

	/**
	 * Handles a login request
	 * @param params
	 * @return "SUCCESS" if successful otherwise an error message in JSON
	 */
	@Override
	public JsonElement login(Login_Input params) {
		return null;
	}
	
	/**
	 * Handles a register new user request
	 * @param params
	 * @return "SUCCESS" if successful otherwise an error message in JSON
	 */
	@Override
	public JsonElement register(Register_Input params) {
		return null;
	}
}
