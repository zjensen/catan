package server.facade.user;

import client.server.IServer;
import shared.communication.user.*;
/**
 * 
 * UserFacade handles the requests for when users try to login or create a new user
 *
 */
public class UserFacade {

	private IServer server;
	
	public UserFacade() {
		this.server = null;
	}
	/**
	 * Logs into the server with the provided credentials
	 * 
	 * @param params
	 * @return Login_Output
	 */
	public Login_Output login(Login_Input params){
		
		return null;
	}
	
	/**
	 * Registers a user with the provided credentials
	 * 
	 * @param params
	 * @return Register_Output
	 */
	public Register_Output register(Register_Input params){
		
		return null;
	}
	
	public IServer getServer() {
		return server;
	}
	
	public void setServer(IServer server) {
		this.server = server;
	}
	
}
