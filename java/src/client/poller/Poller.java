package client.poller;

import java.util.Timer;
import java.util.TimerTask;
import client.server.*;

import shared.models.*;

/**
 * 
 * Poller class will set a timer to update the client model every second
 *
 */
public class Poller 
{
	private IServer server;
	private Timer timer;
	private ClientModel clientModel;
	
	/**
	 * 
	 * @param server interface
	 */
	public Poller(IServer server)
	{
		this.server = server;
	}
	
	/**
	 * call upon server to update client model
	 */
	private void updateClientModel()
	{
		clientModel.updateClient("Temp");
	}

	public IServer getServer() {
		return server;
	}

	public void setServer(IServer server) {
		this.server = server;
	}

	private Timer getTimer() {
		return timer;
	}

	private void setTimer(Timer timer) {
		this.timer = timer;
	}

	public ClientModel getClientModel() {
		return clientModel;
	}

	public void setClientModel(ClientModel clientModel) {
		this.clientModel = clientModel;
	}
}
