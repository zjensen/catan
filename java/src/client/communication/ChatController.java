package client.communication;

import java.util.Observable;
import java.util.Observer;

import client.base.*;
import client.session.SessionManager;


/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController, Observer {

	public ChatController(IChatView view) {
		
		super(view);
		
		SessionManager.instance().addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public IChatView getView() {
		return (IChatView)super.getView();
	}

	@Override
	public void sendMessage(String message) {
		
	}

}

