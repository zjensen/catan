package client.poller;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import client.server.*;
import client.session.SessionManager;
import shared.communication.game.GameModel_Input;
import shared.communication.game.GameModel_Output;
import shared.models.*;

/**
 * 
 * Poller class will set a timer to update the client model every second
 *
 */
public class Poller 
{
	private Timer timer;
	private ClientModel clientModel;
	
	/**
	 * 
	 * @param server interface
	 */
	public Poller()
	{
		this.clientModel = new ClientModel();
		this.timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask(){
			@Override
			public void run(){
				GameModel_Input input = new GameModel_Input();
				input.setVersion(clientModel.getVersion());
				GameModel_Output output = SessionManager.get_instance().getServer().getModel(input);
				if (!output.getResponse().equals("true")) {
					//this.clientModel = SessionManager.get_instance().getInterpreter().deserialize(output.getResponse());
					SessionManager.get_instance().updateClientModels(clientModel);
				}
			}
		}, new Date(), 2*1000);
	}
	
	/**
	 * call upon server to update client model
	 */
	private void updateClientModel()
	{
		clientModel.updateClient("Temp");
		//
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
