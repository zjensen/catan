package client.poller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

//import java.util.Date;
//import java.util.Timer;
//import java.util.TimerTask;


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
//	public Poller()
//	{
//		this.clientModel = new ClientModel();
//		this.timer = new Timer();
//		timer.scheduleAtFixedRate(new TimerTask(){
//			@Override
//			public void run(){
//				GameModel_Input input = new GameModel_Input();
//				input.setVersion(clientModel.getVersion());
//				GameModel_Output output = SessionManager.instance().getServer().getModel(input);
//				if (!output.getResponse().equals("\"true\"")) {
//					clientModel = SessionManager.instance().getInterpreter().deserialize(output.getResponse());
//					SessionManager.instance().updateClientModels(clientModel);
//				}
//			}
//		}, new Date(), 3*1000);
//	}
	
	public Poller()
	{
		this.clientModel = new ClientModel();
		ActionListener task = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GameModel_Input input = new GameModel_Input();
				input.setVersion(clientModel.getVersion());
				GameModel_Output output = SessionManager.instance().getServer().getModel(input);
				if (!output.getResponse().equals("\"true\"")) {
					clientModel = SessionManager.instance().getInterpreter().deserialize(output.getResponse());
					SessionManager.instance().updateClientModels(clientModel);
				}
			}
		};
		this.timer = new Timer(3*1000, task);
		this.timer.start();
	}
	
	public void stopTimer()
	{
		timer.stop();
		//timer.cancel();
	}
	
	/**
	 * call upon server to update client model
	 */
	private void updateClientModel()
	{
		clientModel.updateClient("Temp");
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
