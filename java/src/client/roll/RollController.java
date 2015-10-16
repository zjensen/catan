package client.roll;

import java.util.Observable;
import java.util.Observer;

import shared.communication.moves.RollNumber_Input;
import client.base.*;
import client.session.SessionManager;


/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController, Observer {

	private IRollResultView resultView;

	/**
	 * RollController constructor
	 * 
	 * @param view Roll view
	 * @param resultView Roll result view
	 */
	public RollController(IRollView view, IRollResultView resultView) {

		super(view);
		
		setResultView(resultView);
		
		SessionManager.instance().addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		int index = SessionManager.instance().getPlayerIndex();
		if(SessionManager.instance().getClientFacade().canRoll(index))
		{
			getRollView().showModal();
		}
	}
	
	public IRollResultView getResultView() {
		return resultView;
	}
	public void setResultView(IRollResultView resultView) {
		this.resultView = resultView;
	}

	public IRollView getRollView() {
		return (IRollView)getView();
	}
	
	@Override
	public void rollDice() {
		int die1 = (int)(Math.random()*6) + 1;
        int die2 = (int)(Math.random()*6) + 1;
        int rollValue = die1+die2;
        RollNumber_Input params = new RollNumber_Input(SessionManager.instance().getPlayerIndex(), rollValue);
        if(SessionManager.instance().getClientFacade().canRollNumber(params))
        {
        	SessionManager.instance().getClientFacade().rollNumber(params);
        	getResultView().setRollValue(rollValue);
    		getResultView().showModal();
        }
	}

}

