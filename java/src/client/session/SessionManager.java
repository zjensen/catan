package client.session;

import org.junit.experimental.theories.Theories;

import client.poller.Poller;
import client.server.*;
import server.facade.game.GameFacade;
import server.facade.games.GamesFacade;
import server.facade.moves.MovesFacade;
import server.facade.user.UserFacade;
import shared.communication.user.Login_Input;
import shared.communication.user.Login_Output;
import shared.communication.user.Register_Input;
import shared.communication.user.Register_Output;
import shared.models.ClientModel;

public class SessionManager {
	
	//Private Data Members
	private UserFacade userFacade = new UserFacade();
	private GameFacade gameFacade = new GameFacade();
	private GamesFacade gamesFacade = new GamesFacade();
	private MovesFacade movesFacade = new MovesFacade(this.clientModel);
	private ClientModel clientModel = new ClientModel();
	private Poller poller;
	private IServer server;
	//-----------------------
	
	//Singleton Setup
	private static SessionManager _instance;
	
	/**
	 * This sets up our SessionManager Singleton
	 * This class will the means of communication between the controllers, the facade, and the proxy
	 * There will be several methods created and implemented here in the future 
	 * @return
	 */
	public static SessionManager instance() {
		if (_instance == null) 
			_instance = new SessionManager();
		return _instance;
	}
	//-----------------------

	/**
	 * A test method will connect to the fake server for testing
	 */
	public void setServer() {
		this.server = new FakeServer();
		this.poller = new Poller(this.server);
	}
	
	/**
	 * Sets connects to the correct server given the host and port
	 * @param host
	 * @param port
	 */
	public void setServer(String host, String port) {
		this.server = new Server();//This will eventually pass in both the host and port
		this.poller = new Poller(this.server);
	}
	
	/**
	 * Sends the login information to the user facade
	 * @param loginInput
	 * @return
	 */
	public Login_Output loginIntoServer(Login_Input loginInput) {
		return this.userFacade.login(loginInput);
	}
	
	/**
	 * Sends the registration information to the user facade
	 * @param registerInput
	 * @return
	 */
	public Register_Output registerNewUser(Register_Input registerInput) {
		return this.userFacade.register(registerInput);
	}
	
}
