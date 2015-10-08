package client.session;

import java.util.Observable;

import client.facade.ClientFacade;
import client.interpreter.Interpreter;
import client.poller.Poller;
import client.server.*;
import shared.models.ClientModel;

public class SessionManager extends Observable{
	
	//Private Data Members
	private ClientFacade clientFacade = new ClientFacade(this.clientModel);
	public ClientModel clientModel = new ClientModel();
	private Poller poller;
	private Interpreter interpreter = new Interpreter();
	private IServer server;
	//--------------------------------------------------------------------------------------------------
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
	//--------------------------------------------------------------------------------------------------
	//Session Manager Methods

	/**
	 * A test method will connect to the fake server for testing
	 */
	public void setServer() {
		this.server = new FakeServer();
		this.poller = new Poller();
	}
	
	/**
	 * Sets connects to the correct server given the host and port
	 * @param host
	 * @param port
	 */
	public void setServer(String host, String port) {
		this.server = new Server();//This will eventually pass in both the host and port
		this.poller = new Poller();
	}
	
	/**
	 * Updates the all client models to the latest version
	 * <pre>
	 * A newer version of the client model has been generated
	 * </pre>
	 * @param newClientModel
	 */
	public void updateClientModels(ClientModel newClientModel) {
		this.clientModel = newClientModel;
		this.clientFacade.setClientModel(newClientModel);
		this.poller.setClientModel(newClientModel);
		this.setChanged();
		this.notifyObservers();
	}
	
	//--------------------------------------------------------------------------------------------------
	//Getter Methods
	
	public ClientFacade getClientFacade() {
		return clientFacade;
	}

	public Poller getPoller() {
		return poller;
	}

	public IServer getServer() {
		return server;
	}

	public Interpreter getInterpreter() {
		return interpreter;
	}
	
	public static SessionManager get_instance() {
		return _instance;
	}
	
	public ClientModel getClientModel()
	{
		return clientModel;
	}
	
	
}