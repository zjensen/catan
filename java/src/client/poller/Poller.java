package client.poller;

import java.util.Timer;
import java.util.TimerTask;

import shared.models.*;

/**
 * 
 * Poller class will set a timer to update the client model every second
 *
 */
public class Poller 
{
	private iServer server;
	private Timer timer;
	
	/**
	 * 
	 * @param server interface
	 */
	public Poller(iServer server)
	{
		this.server = server;
	}
	
	/**
	 * call upon server to update client model
	 */
	private void updateCLientModel()
	{
		server.updateModel();
	}
}
