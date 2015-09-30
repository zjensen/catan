package client.session;

import client.facade.ClientFacade;
import client.poller.Poller;
import client.server.*;
import shared.models.ClientModel;

public class SessionManager {
	
	//Private Data Members
	private ClientFacade clientFacade = new ClientFacade(this.clientModel);
	private ClientModel clientModel = new ClientModel();
	private Poller poller;
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
	 * Updates the all client models to the latest version
	 * <pre>
	 * A newer version of the client model has been generated
	 * </pre>
	 * @param newClientModel
	 */
	public void updateClientModels(ClientModel newClientModel) {
		this.clientModel = newClientModel;
		this.clientFacade.setClientModel(newClientModel);
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

	public static SessionManager get_instance() {
		return _instance;
	}
}