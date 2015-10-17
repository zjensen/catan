package client.join;

import java.util.Observable;
import java.util.Observer;

import client.base.*;
import client.data.PlayerInfo;
import client.session.SessionManager;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController, Observer {

	public PlayerWaitingController(IPlayerWaitingView view) {

		super(view);
		
		SessionManager.instance().addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public IPlayerWaitingView getView() {

		return (IPlayerWaitingView)super.getView();
	}

	@Override
	public void start() 
	{
		getView().showModal();
		
		getView().setPlayers(SessionManager.instance().getGameInfo().getPlayersArray());
		
		getView().closeModal();
	}

	@Override
	public void addAI() {

		// TEMPORARY
		getView().closeModal();
	}

}

