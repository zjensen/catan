package client.join;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import client.base.*;
import client.data.PlayerInfo;
import client.misc.MessageView;
import client.session.SessionManager;
import shared.communication.game.AddAI_Input;
import shared.models.Player;


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
//		System.out.println("Starting PlayerWaiting Update");
		boolean updated = false;
		
		if(arg.equals("reset"))
		{
			return;
		}
		
		SessionManager session = SessionManager.instance();
		for(Player player : session.getClientModel().getPlayers()){ //iterates through all players
			if(player != null)
			{
				PlayerInfo newPlayer = new PlayerInfo();
				newPlayer.setColor(player.getCatanColor());
				newPlayer.setId(player.getPlayerID());
				newPlayer.setName(player.getName());
				newPlayer.setPlayerIndex(player.getIndex());
				
				for(PlayerInfo pi : session.getGameInfo().getPlayers()){ //checks all players already known about
					if(pi.getName().equals(player.getName())){ //if that player is already known, we don't need to add them
						newPlayer = null;
						if(!pi.getColor().equals(player.getCatanColor())){
							updated = true;
							pi.setColor(player.getCatanColor()); //in case colors have changed
						}
						break;
					}
				}
				if(newPlayer!=null){ //if the player wasn't found, add him/her to the game
					updated = true;
					session.getGameInfo().addPlayer(newPlayer);
				}
			}
		}
		updatePlayers();
		
		if(isFull()) 
		{
			OverlayView.closeAllModals();
			if(getView().isModalShowing())
				getView().closeModal();
			session.setupGame();
		} 
		else if (updated && !isFull())
		{
			if(getView().isModalShowing())
				getView().closeModal();
			if(!getView().isModalShowing())
				getView().showModal();
		}
	}

	@Override
	public IPlayerWaitingView getView() {

		return (IPlayerWaitingView)super.getView();
	}

	@Override
	public void start() 
	{
		SessionManager.instance().forceUpdate();
		updatePlayers();
		
		if (isFull()) 
		{
			OverlayView.closeAllModals();
			if(getView().isModalShowing())
				getView().closeModal();
			SessionManager.instance().setupGame();
			SessionManager.instance().forceUpdate();
		} 
		else 
		{
			if(!getView().isModalShowing())
				getView().showModal();
		}
	}

	@Override
	public void addAI() 
	{
		MessageView alertView = new MessageView();
		alertView.setTitle("No AI yet :(");
		alertView.setMessage("AI has not been implemented for this server yet");
		alertView.showModal();
		//TODO replace when AI is implemented
//		try 
//		{
//			AddAI_Input req = new AddAI_Input("LARGEST_ARMY"); //only type of AI supported by current server
//			SessionManager.instance().getServer().addAI(req);
//			SessionManager.instance().forceUpdate();
//		} 
//		catch (Exception e) 
//		{
//			MessageView alertView = new MessageView();
//			alertView.setTitle("Error");
//			alertView.setMessage(e.getLocalizedMessage());
//			alertView.showModal();
//		}
	}
	
	private boolean isFull()
	{
		if(SessionManager.instance().getGameInfo().getPlayers().size()>=4) //modal only closes if there are four or more players
			return true;
		return false;
	}
	
	private void updatePlayers() 
	{
		ArrayList<PlayerInfo> players =  new ArrayList<PlayerInfo>(SessionManager.instance().getGameInfo().getPlayers());
		PlayerInfo [] playerArray = players.toArray(new PlayerInfo[players.size()]);
		getView().setPlayers(playerArray);
	}
}