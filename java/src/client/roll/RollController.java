package client.roll;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Timer;

import shared.communication.moves.RollNumber_Input;
import client.base.*;
import client.session.SessionManager;


/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController, Observer {

	private IRollResultView resultView;
	private Timer countdown;

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
		if(arg.equals("reset") || arg.equals(false)) //are all the players here??
		{
			return;
		}
		int index = SessionManager.instance().getPlayerIndex();
		if(SessionManager.instance().getClientFacade().canRoll(index))
		{
			if(!getRollView().isModalShowing())
			{
				getRollView().showModal();
				countdown();
			}
		}
	}
	
	public void countdown()
	{
		if(countdown != null && countdown.isRunning())
		{
			return;
		}
		ActionListener task = new ActionListener() 
		{
			int elapsedSeconds = 5;
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				elapsedSeconds--;
		        getRollView().setMessage("Rolling automatically in. . . " + elapsedSeconds+" seconds");
		        if(elapsedSeconds <= 0)
		        {
		            rollDice();
		        }
			}
		};
		getRollView().setMessage("Rolling automatically in. . . 5 seconds");
		countdown = new Timer(1000, task);
		countdown.start();
	}
	
	public void automaticRoll()
	{
		if(countdown != null)
		{
			countdown.stop();
		}
		rollDice();
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
		if(countdown!=null && countdown.isRunning())
		{
			countdown.stop();
		}
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

