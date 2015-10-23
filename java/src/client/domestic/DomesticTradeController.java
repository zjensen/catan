package client.domestic;

import java.util.Observable;
import java.util.Observer;

import shared.communication.moves.BuyDevCard_Input;
import shared.definitions.*;
import shared.models.Player;
import shared.models.ResourceCards;
import client.base.*;
import client.data.PlayerInfo;
import client.misc.*;
import client.session.SessionManager;


/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends Controller implements IDomesticTradeController, Observer {

	private IDomesticTradeOverlay tradeOverlay;
	private IWaitView waitOverlay;
	private IAcceptTradeOverlay acceptOverlay;
	private PlayerInfo[] players;
	private boolean playersSet;
	private ResourceCards curOffer;
	private boolean receiveResources;

	/**
	 * DomesticTradeController constructor
	 * 
	 * @param tradeView Domestic trade view (i.e., view that contains the "Domestic Trade" button)
	 * @param tradeOverlay Domestic trade overlay (i.e., view that lets the user propose a domestic trade)
	 * @param waitOverlay Wait overlay used to notify the user they are waiting for another player to accept a trade
	 * @param acceptOverlay Accept trade overlay which lets the user accept or reject a proposed trade
	 */
	public DomesticTradeController(IDomesticTradeView tradeView, IDomesticTradeOverlay tradeOverlay,
            IWaitView waitOverlay, IAcceptTradeOverlay acceptOverlay) {

		super(tradeView);
		
		setTradeOverlay(tradeOverlay);
		setWaitOverlay(waitOverlay);
		setAcceptOverlay(acceptOverlay);
		
		playersSet = false;
		players = new PlayerInfo[3];
		
		curOffer = new ResourceCards();
		receiveResources = false;
		
				
		SessionManager.instance().addObserver(this);
	}
		
		
	/**
	* Creates PlayerInfo[] array to be used to display the players to trade with.
	* @return
	*/
	public PlayerInfo[] updatePlayerInfo()
	{
		Player[] playerList = SessionManager.instance().getClientModel().getPlayers();	
		int cur_player_index = SessionManager.instance().getPlayerIndex();
		int pos = 0;
		int count = 0;
		
		while (count < 4)
		{   
			if (cur_player_index != count)
			{
				PlayerInfo addPlayer = new PlayerInfo(playerList[count].getName(), playerList[count].getPlayerID(),
                        					playerList[count].getCatanColor(), playerList[count].getIndex());
				this.players[pos] = addPlayer;
				pos++;
			}
			count++;
		}
		return players;
	}

	public void printPlayers() 
	{
		System.out.println("Current Players");
		for (int a = 0; a < this.players.length; a++)
		{
			this.players[a].toString();
		}
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		// TODO Auto-generated method stub
	}
	
	public IDomesticTradeView getTradeView() {
		
		return (IDomesticTradeView)super.getView();
	}

	public IDomesticTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IDomesticTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	public IWaitView getWaitOverlay() {
		return waitOverlay;
	}

	public void setWaitOverlay(IWaitView waitView) {
		this.waitOverlay = waitView;
	}

	public IAcceptTradeOverlay getAcceptOverlay() {
		return acceptOverlay;
	}

	public void setAcceptOverlay(IAcceptTradeOverlay acceptOverlay) {
		this.acceptOverlay = acceptOverlay;
	}

	@Override
	public void startTrade() {
		
		curOffer.resetAllResourceValues();
		
		if (playersSet == false)
		{
			playersSet = true;
			updatePlayerInfo();
			this.tradeOverlay.setPlayers(players);
		}
		else
		{
			System.out.println("\nDomTradeCon.startTrade: Do nothing :)");
		}
		
		getTradeOverlay().reset();
		getTradeOverlay().showModal();
	}

	@Override
	public void decreaseResourceAmount(ResourceType resource) { 
		System.out.println("\n-->Mine DomTradeCon.decreaseResourceAmount: DONE??");
		System.out.println("Resource param=" + resource.toString());
		
		// Check to see if Send or Receive Button was pressed
		if (receiveResources == false)
		{
			Player[] playerList = SessionManager.instance().getClientModel().getPlayers();	
			int curPlayerIndex = SessionManager.instance().getPlayerIndex();
			
			int curPlayerAmount = playerList[curPlayerIndex].getResources().getResourceValue(resource);
			
			curOffer.addOne(resource);
			int newAmount = curOffer.getResourceValue(resource);
			
			System.out.println("CurPlayerAmount = " + curPlayerAmount);
			System.out.println("NewAmount       = " + newAmount);
			System.out.println("CurOffer " + curOffer.toString());
			
			if (Math.abs(newAmount) > 0)
			{
				System.out.println("D OPTION 1");
				if (Math.abs(newAmount) < curPlayerAmount)
				{
					System.out.println("\tIF 1");
					getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
				}
				else
				{
					System.out.println("\tIF 2");
					getTradeOverlay().setResourceAmountChangeEnabled(resource, false, true);
				}
			}
			else if (Math.abs(newAmount) == 0)
			{
				System.out.println("D OPTION 2");
				if (Math.abs(newAmount) < curPlayerAmount)
				{
					System.out.println("\tIF 1");
					getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
				}
				else
				{
					System.out.println("\tIF 2");
					getTradeOverlay().setResourceAmountChangeEnabled(resource, false, false);
				}
			}
		}
		else // This path is for when the receive option was selected
		{
			curOffer.subtractOne(resource);
			int newAmount = curOffer.getResourceValue(resource);
			
			System.out.println("NewAmount       = " + newAmount);
			System.out.println("CurOffer " + curOffer.toString());

			if (newAmount > 0)
			{
				System.out.println("RD OPTION 1");
				getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
				
			}
			else  if (newAmount == 0)
			{
				System.out.println("RD OPTION 2");
				getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
				
			}
		}
		
	}

	@Override
	public void increaseResourceAmount(ResourceType resource) { 
		System.out.println("\n-->Mine DomTradeCon.increaseResourceAmount: DONE??");
		System.out.println("Resource param=" + resource.toString());
		
		// Check to see if Send or Receive Button was pressed
		if (receiveResources == false)
		{
			Player[] playerList = SessionManager.instance().getClientModel().getPlayers();	
			int curPlayerIndex = SessionManager.instance().getPlayerIndex();
			
			int curPlayerAmount = playerList[curPlayerIndex].getResources().getResourceValue(resource);
			
			curOffer.subtractOne(resource);
			int newAmount = curOffer.getResourceValue(resource);
			
			System.out.println("CurPlayerAmount = " + curPlayerAmount);
			System.out.println("NewAmount       = " + newAmount);
			System.out.println("CurOffer " + curOffer.toString());

			if (Math.abs(newAmount) < curPlayerAmount)
			{
				System.out.println("I OPTION 1");
				if (Math.abs(newAmount) > 0)
				{
					System.out.println("\tIF 1");
					getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
				}
				else
				{
					System.out.println("\tIF 2");
					getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
				}
			}
			else  if (Math.abs(newAmount) == curPlayerAmount)
			{
				System.out.println("I OPTION 2");
				if (Math.abs(newAmount) > 0)
				{
					System.out.println("\tIF 1");
					getTradeOverlay().setResourceAmountChangeEnabled(resource, false, true);
				}
				else
				{
					System.out.println("\tIF 2");
					getTradeOverlay().setResourceAmountChangeEnabled(resource, false, false);
				}
			}
		}
		else // This path is for when the receive option was selected
		{
			curOffer.addOne(resource);
			int newAmount = curOffer.getResourceValue(resource);
			
			System.out.println("NewAmount       = " + newAmount);
			System.out.println("CurOffer " + curOffer.toString());

			if (newAmount > 0)
			{
				System.out.println("RI OPTION 1");
				getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
				
			}
			else  if (newAmount == 0)
			{
				System.out.println("RI OPTION 2");
				getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
				
			}
		}
	}

	@Override
	public void sendTradeOffer() { // DO WITH SOMEONE
		
		getTradeOverlay().closeModal();
//		getWaitOverlay().showModal();
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {
		System.out.println("\n-->Mine DomTradeCon.setPlayerToTradeWith: Player's button was clicked");
		System.out.println("Other player index = " + playerIndex);

	}

	@Override
	public void setResourceToReceive(ResourceType resource) {
		System.out.println("\nDomTradeCon.setResourceToReceive: DONE??");
		receiveResources = true;
		
		
		unsetResource(resource);
		curOffer.resetOneResourceValue(resource);
		
        getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
	}

	@Override
	public void setResourceToSend(ResourceType resource) { 
		System.out.println("\nDomTradeCon.setResourceToSend: DONE??");
		receiveResources = false;
		unsetResource(resource);
		curOffer.resetOneResourceValue(resource);
		
		Player[] playerList = SessionManager.instance().getClientModel().getPlayers();	
		int curPlayerIndex = SessionManager.instance().getPlayerIndex();
		
		if (playerList[curPlayerIndex].getResources().getResourceValue(resource) == 0)
		{
			getTradeOverlay().setResourceAmountChangeEnabled(resource, false, false);
		}
		else if (playerList[curPlayerIndex].getResources().getResourceValue(resource) > 0)
		{
			getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
		}
		else if (playerList[curPlayerIndex].getResources().getResourceValue(resource) == -1)
		{
			System.out.println("DomTradeCon.setResourceToSend: ERROR: -1 problem");
		}
	}

	@Override
	public void unsetResource(ResourceType resource) {
		System.out.println("\nDomTradeCon.unsetResource: Resets number to trade");		
		
		curOffer.resetOneResourceValue(resource);
		getTradeOverlay().setResourceAmount(resource, "0");
	}

	@Override
	public void cancelTrade() {
		System.out.println("\nDomTradeCon.cancelTrade: DONE??");
		
		getTradeOverlay().closeModal();
	}

	@Override
	public void acceptTrade(boolean willAccept) {
		
		System.out.println("DomTradeCon.acceptTrade: TODO");
		
		
		getAcceptOverlay().closeModal();
	}

}

