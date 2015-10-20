package client.points;

import java.util.Observable;
import java.util.Observer;

import client.base.*;
import client.data.PlayerInfo;
import client.session.SessionManager;


/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController, Observer {

	private IGameFinishedView finishedView;
	private int points;
	
	/**
	 * PointsController constructor
	 * 
	 * @param view Points view
	 * @param finishedView Game finished view, which is displayed when the game is over
	 */
	public PointsController(IPointsView view, IGameFinishedView finishedView) {
		
		super(view);
		
		setFinishedView(finishedView);
		
		this.points = 0;
		
		getPointsView().setPoints(this.points);
		
		SessionManager.instance().addObserver(this);
		
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		if(arg.equals("reset")) //are all the players here??
		{
			this.points = 0;
			getPointsView().setPoints(this.points);
			return;
		}
		
		int index = SessionManager.instance().getPlayerIndex();
		int pointsToCompare = SessionManager.instance().getClientFacade().getPoints(index);
		
		if(pointsToCompare != this.points)
		{
			this.points = pointsToCompare;
			getPointsView().setPoints(this.points);
		}
		
		PlayerInfo p = SessionManager.instance().getWinner();
		if(p != null)
		{
			getFinishedView().setWinner(p.getName(), p.getPlayerIndex() == SessionManager.instance().getPlayerIndex());
			getFinishedView().showModal();
		}
	}
	
	public IPointsView getPointsView() {
		
		return (IPointsView)super.getView();
	}
	
	public IGameFinishedView getFinishedView() {
		return finishedView;
	}
	public void setFinishedView(IGameFinishedView finishedView) {
		this.finishedView = finishedView;
	}
	
}

