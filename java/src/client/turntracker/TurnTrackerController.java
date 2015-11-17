package client.turntracker;

import java.util.Observable;
import java.util.Observer;

import shared.communication.moves.FinishTurn_Input;
import shared.models.Player;
import client.base.*;
import client.session.SessionManager;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController, Observer {
	
	private boolean initiated = false;

	public TurnTrackerController(ITurnTrackerView view) {
		
		super(view);
		
		SessionManager.instance().addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		if(arg.equals("reset")) //are all the players here??
		{
			initiated = false;
			this.getView().reset();
			return;
		}
		if(arg.equals(false))
		{
			return;
		}
		if(!initiated)
		{
			initFromModel();
			initiated = true;
		}
		initFromModel();
		Player[] players = SessionManager.instance().getClientModel().getPlayers();
		int largestArmy = SessionManager.instance().getClientModel().getTurnTracker().getLargestArmy();
		int longestRoad = SessionManager.instance().getClientModel().getTurnTracker().getLongestRoad();
		int currentTurn = SessionManager.instance().getClientModel().getTurnTracker().getCurrentTurn();
		boolean gameOver = false;
		int winnerIndex = -1;
		
		for(Player p : players)
		{
			if(p==null || p.getIndex() == -1)
			{
				continue;
			}
			if(p.getVictoryPoints() >= 10)
			{
				gameOver = true;
				winnerIndex = p.getIndex();
			}
			int index = p.getIndex();
			getView().updatePlayer(index, p.getVictoryPoints(), index==currentTurn, index==largestArmy, index==longestRoad);
		}
		
		if(gameOver) 
		{
			SessionManager.instance().endGame(winnerIndex);
			getView().updateGameState("Game Over", false);
		}
		else if(SessionManager.instance().getClientFacade().canFinishTurn(new FinishTurn_Input(SessionManager.instance().getPlayerIndex())))
		{
			getView().updateGameState("Finish Turn", true);
		}
		else
		{
			getView().updateGameState("Waiting for Other Players", false);
		}
	}
	
	@Override
	public ITurnTrackerView getView() {
		
		return (ITurnTrackerView)super.getView();
	}

	@Override
	public void endTurn() {
		SessionManager.instance().getClientFacade().finishTurn(new FinishTurn_Input(SessionManager.instance().getPlayerIndex()));

	}
	
	private void initFromModel() {
		getView().setLocalPlayerColor(SessionManager.instance().getPlayerInfo().getColor());
		Player[] players = SessionManager.instance().getClientModel().getPlayers();
		
		for(Player p : players)
		{
			if(p == null)
				continue;
			getView().initializePlayer(p.getIndex(), p.getName(), p.getCatanColor());
		}
	}

}

