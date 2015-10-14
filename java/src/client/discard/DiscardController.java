package client.discard;

import java.util.Observable;
import java.util.Observer;

import shared.definitions.*;
import client.base.*;
import client.misc.*;
import client.session.SessionManager;


/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController, Observer {

	private IWaitView waitView;
	private boolean showing = false;
	
	/**
	 * DiscardController constructor
	 * 
	 * @param view View displayed to let the user select cards to discard
	 * @param waitView View displayed to notify the user that they are waiting for other players to discard
	 */
	public DiscardController(IDiscardView view, IWaitView waitView) {
		
		super(view);
		
		this.waitView = waitView;
		
		SessionManager.instance().addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		if(SessionManager.instance().getClientModel().getTurnTracker().getStatus().equalsIgnoreCase("discarding"))
		{
			if(SessionManager.instance().getClientFacade().needsToDiscard(SessionManager.instance().getPlayerIndex()))
			{
				getDiscardView().showModal();
				showing = true;
			}
			else
			{
				if(showing)
				{
					getDiscardView().closeModal();
				}
				getWaitView().showModal();
				showing = true;
			}
		}
		else if(showing)
		{
			getDiscardView().closeModal();
			getWaitView().closeModal();
			showing = false;
		}
	}

	public IDiscardView getDiscardView() {
		return (IDiscardView)super.getView();
	}
	
	public IWaitView getWaitView() {
		return waitView;
	}

	@Override
	public void increaseAmount(ResourceType resource) {
		
	}

	@Override
	public void decreaseAmount(ResourceType resource) {
		
	}

	@Override
	public void discard() {
		
		getDiscardView().closeModal();
	}

}

