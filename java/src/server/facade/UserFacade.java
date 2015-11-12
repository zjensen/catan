package server.facade;

import shared.communication.user.Login_Input;
import shared.communication.user.Register_Input;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;

//make all methods static
public class UserFacade implements IUserFacade{

	/**
	 * Handles a login request
	 * @param params
	 * @return JsonObject with the id, username, password
	 */
	@Override
	public JsonElement login(Login_Input params) throws ServerInvalidRequestException {
		if(!validateUser(params.getUsername(), params.getPassword())) {
			throw new ServerInvalidRequestException("Failed to validate user"); 
		}
		JsonObject result = new JsonObject();
		result.addProperty("id",getUserId(params.getUsername()));
		result.addProperty("username",params.getUsername());
		result.addProperty("password",params.getPassword());
		return result;
	}
	
	/**
	 * Handles a register new user request
	 * @param params
	 * @return JsonObject with the id, username, password
	 */
	@Override
	public JsonElement register(Register_Input params) throws ServerInvalidRequestException {
		if(userExists(params.getUsername())){
			throw new ServerInvalidRequestException("Failed to register user"); 
		}
		registerUser(params.getUsername(), params.getPassword());
		JsonObject result = new JsonObject();
		result.addProperty("id",getUserId(params.getUsername()));
		result.addProperty("username",params.getUsername());
		result.addProperty("password",params.getPassword());
		return result;
	}
	
	/**
	 * Uses the username and password to validate the user
	 * @param username
	 * @param password
	 * @return true if the user exists, otherwise false
	 */
	private boolean validateUser(String username, String password) {
		return ServerManager.instance().getUsersManager().validateUser(username, password);
	}
	
	/**
	 * Gets the user's ID from the UsersManager
	 * @param username
	 * @return the PlayerID of the user
	 */
	private int getUserId(String username) {
		return ServerManager.instance().getUsersManager().getUserByUserName(username).getPlayerID();
	}
	
	/**
	 * 
	 * @param username
	 * @return true if the user already exists, otherwise false
	 */
	private boolean userExists(String username) {
		return ServerManager.instance().getUsersManager().userExists(username);
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 */
	private void registerUser(String username, String password) {
		ServerManager.instance().getUsersManager().addNewUser(username, password);
	}
}
