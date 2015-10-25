package client.domestic;

import java.util.Observable;
import java.util.Observer;

import shared.communication.moves.AcceptTrade_Input;
import shared.communication.moves.BuyDevCard_Input;
import shared.communication.moves.OfferTrade_Input;
import shared.definitions.*;
import shared.models.Player;
import shared.models.ResourceCards;
import shared.models.TradeOffer;
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
	private int curPlayerToTradeTo;

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
		curPlayerToTradeTo = -1;
		
				
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
	
	public void fixResource(TradeOffer receivedTrade, ResourceType resource)
	{
		int res_amount = receivedTrade.getOffer().getResourceValue(resource);
		
		if (res_amount > 0)
		{
			getAcceptOverlay().addGiveResource(resource, res_amount);
		}
		else if (res_amount < 0)
		{
			getAcceptOverlay().addGetResource(resource, Math.abs(res_amount));
		}
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		if(arg.equals("reset")) //are all the players here??
		{
			return;
		}
		if(SessionManager.instance().getClientModel().getTurnTracker().getStatus().equalsIgnoreCase("playing"))
		{
			if (SessionManager.instance().getClientModel().getTradeOffer() == null)
			{
				System.out.println("NO trade to check. Returning");
				return;
			}
			
			System.out.println("YES trade to check. proceed with caution");
			
			int cur_player_index = SessionManager.instance().getPlayerIndex();
			int receiver = cur_player_index;
			receiver = SessionManager.instance().getClientModel().getTradeOffer().getReceiver();
			System.out.println("\tCurPlayer = " + cur_player_index);
			System.out.println("\tReceiver  = " + receiver);
			
			if (cur_player_index == receiver)
			{
				System.out.println("This player needs to accept/deny trade");
				
				TradeOffer receivedTrade = new TradeOffer();
				receivedTrade.setSender(SessionManager.instance().getClientModel().getTradeOffer().getSender());
				receivedTrade.setOffer(SessionManager.instance().getClientModel().getTradeOffer().getOffer());
				receivedTrade.setReceiver(SessionManager.instance().getClientModel().getTradeOffer().getReceiver());


				// FIX ACCEPT MODAL
				getAcceptOverlay().reset();
				
				//getAcceptOverlay().addGetResource(resource, amount); // 4 wheat
				
				//getAcceptOverlay().addGiveResource(resource, amount); // 2 wood
				
			// Fix resources 
				fixResource(receivedTrade, ResourceType.WOOD);
				fixResource(receivedTrade, ResourceType.BRICK);
				fixResource(receivedTrade, ResourceType.SHEEP);
				fixResource(receivedTrade, ResourceType.WHEAT);
				fixResource(receivedTrade, ResourceType.ORE);
			
				Player[] playerList = SessionManager.instance().getClientModel().getPlayers();	
				
				getAcceptOverlay().setPlayerName(playerList[receivedTrade.getSender()].getName());
			
				if (!getAcceptOverlay().isModalShowing())
				{
					System.out.println("\tAccept Modal isn\'t showing. TURN ON");
					getAcceptOverlay().showModal();

				}
				else
				{
					System.out.println("\tAccept Modal is showing. TURN OFF");
				}
				
			}
			else
			{
				System.out.println("YOU sent trade");
			}
			

		}
		else
		{
			System.out.println("\tNot playing");
		}
		
		
	}
	
	public void setupTrade() {
		
		System.out.println("DomTradeCon.setupTrade: ???");
		
		
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
	
	public void tradeCheck()
	{
		if (curOffer.sendCheck() && curOffer.receiveCheck() && curPlayerToTradeTo != -1)
		{
			getTradeOverlay().setStateMessage(curPlayerToTradeTo + " T: TRADE!");
			getTradeOverlay().setTradeEnabled(true);
		}
		else
		{
			getTradeOverlay().setStateMessage(curPlayerToTradeTo + " T: CAN\'T TRADE!");
			getTradeOverlay().setTradeEnabled(false);
		}
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
		getTradeOverlay().setStateMessage("CHEAT YOUR NEIGHBOR!!");
		getTradeOverlay().showModal();
	}

	@Override
	public void decreaseResourceAmount(ResourceType resource) { 
//		System.out.println("\n-->Mine DomTradeCon.decreaseResourceAmount: DONE??");
//		System.out.println("Resource param=" + resource.toString());
		
		// Check to see if Send or Receive Button was pressed
		if (receiveResources == false)
		{
			Player[] playerList = SessionManager.instance().getClientModel().getPlayers();	
			int curPlayerIndex = SessionManager.instance().getPlayerIndex();
			
			int curPlayerAmount = playerList[curPlayerIndex].getResources().getResourceValue(resource);
			
			curOffer.addOne(resource);
			int newAmount = curOffer.getResourceValue(resource);
			
//			System.out.println("CurPlayerAmount = " + curPlayerAmount);
//			System.out.println("NewAmount       = " + newAmount);
//			System.out.println("CurOffer " + curOffer.toString());
			
			if (Math.abs(newAmount) > 0)
			{
//				System.out.println("D OPTION 1");
				if (Math.abs(newAmount) < curPlayerAmount)
				{
					//System.out.println("\tIF 1");
					getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
				}
				else
				{
					//System.out.println("\tIF 2");
					getTradeOverlay().setResourceAmountChangeEnabled(resource, false, true);
				}
			}
			else if (Math.abs(newAmount) == 0)
			{
				//System.out.println("D OPTION 2");
				if (Math.abs(newAmount) < curPlayerAmount)
				{
					//System.out.println("\tIF 1");
					getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
				}
				else
				{
					//System.out.println("\tIF 2");
					getTradeOverlay().setResourceAmountChangeEnabled(resource, false, false);
				}
			}
		}
		else // This path is for when the receive option was selected
		{
			curOffer.subtractOne(resource);
			int newAmount = curOffer.getResourceValue(resource);
			
//			System.out.println("NewAmount       = " + newAmount);
//			System.out.println("CurOffer " + curOffer.toString());

			if (newAmount > 0)
			{
				//System.out.println("RD OPTION 1");
				getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
				
			}
			else  if (newAmount == 0)
			{
				//System.out.println("RD OPTION 2");
				getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
				
			}
		}
		
		tradeCheck();
	}

	@Override
	public void increaseResourceAmount(ResourceType resource) { 
//		System.out.println("\n-->Mine DomTradeCon.increaseResourceAmount: DONE??");
//		System.out.println("Resource param=" + resource.toString());
		
		// Check to see if Send or Receive Button was pressed
		if (receiveResources == false)
		{
			Player[] playerList = SessionManager.instance().getClientModel().getPlayers();	
			int curPlayerIndex = SessionManager.instance().getPlayerIndex();
			
			int curPlayerAmount = playerList[curPlayerIndex].getResources().getResourceValue(resource);
			
			curOffer.subtractOne(resource);
			int newAmount = curOffer.getResourceValue(resource);
			
//			System.out.println("CurPlayerAmount = " + curPlayerAmount);
//			System.out.println("NewAmount       = " + newAmount);
//			System.out.println("CurOffer " + curOffer.toString());

			if (Math.abs(newAmount) < curPlayerAmount)
			{
				//System.out.println("I OPTION 1");
				if (Math.abs(newAmount) > 0)
				{
					//System.out.println("\tIF 1");
					getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
				}
				else
				{
					//System.out.println("\tIF 2");
					getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
				}
			}
			else  if (Math.abs(newAmount) == curPlayerAmount)
			{
				//System.out.println("I OPTION 2");
				if (Math.abs(newAmount) > 0)
				{
					//System.out.println("\tIF 1");
					getTradeOverlay().setResourceAmountChangeEnabled(resource, false, true);
				}
				else
				{
					//System.out.println("\tIF 2");
					getTradeOverlay().setResourceAmountChangeEnabled(resource, false, false);
				}
			}
		}
		else // This path is for when the receive option was selected
		{
			curOffer.addOne(resource);
			int newAmount = curOffer.getResourceValue(resource);
			
//			System.out.println("NewAmount       = " + newAmount);
//			System.out.println("CurOffer " + curOffer.toString());

			if (newAmount > 0)
			{
				//System.out.println("RI OPTION 1");
				getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
				
			}
			else  if (newAmount == 0)
			{
				//System.out.println("RI OPTION 2");
				getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);	
			}
		}
		
		tradeCheck();
	}

	@Override
	public void sendTradeOffer() {
		System.out.println("DomTradeCon.sendTradeOffer: WORK IN PROGRESS");
		
		int curPlayerIndex = SessionManager.instance().getPlayerIndex();
		
		OfferTrade_Input newTradeOffer = new OfferTrade_Input(curPlayerIndex, curOffer, curPlayerToTradeTo);
		System.out.println("DomTradeCon.sendTradeOffer: Offer that was sent");
		System.out.println(newTradeOffer.toString());
		SessionManager.instance().getClientFacade().offerTrade(newTradeOffer);
		
		getTradeOverlay().closeModal();
		//getWaitOverlay().showModal();

		
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {
//		System.out.println("\n-->Mine DomTradeCon.setPlayerToTradeWith: Player's button was clicked");
//		System.out.println("Other player index = " + playerIndex);

		if (playerIndex == -1)
		{	
//			System.out.println("Disabling trade button");
			curPlayerToTradeTo = -1;
			getTradeOverlay().setStateMessage("TRADE WITH SOME YOU TOOLBAG!!!");
			getTradeOverlay().setTradeEnabled(false);
		}
		else
		{
			curPlayerToTradeTo = playerIndex;
			tradeCheck();
		}
	}

	@Override
	public void setResourceToReceive(ResourceType resource) {
//		System.out.println("\nDomTradeCon.setResourceToReceive: DONE??");
		receiveResources = true;
		
		
		unsetResource(resource);
		curOffer.resetOneResourceValue(resource);
		
        getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
        
        tradeCheck();
	}

	@Override
	public void setResourceToSend(ResourceType resource) { 
//		System.out.println("\nDomTradeCon.setResourceToSend: DONE??");
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
		
		tradeCheck();
	}

	@Override
	public void unsetResource(ResourceType resource) {
//		System.out.println("\nDomTradeCon.unsetResource: Resets number to trade");		

		curOffer.resetOneResourceValue(resource);
		getTradeOverlay().setResourceAmount(resource, "0");
		
		tradeCheck();
	}

	@Override
	public void cancelTrade() {
		System.out.println("\nDomTradeCon.cancelTrade: DONE??");
		
		getTradeOverlay().closeModal();
	}

	@Override
	public void acceptTrade(boolean willAccept) {
		System.out.println("DomTradeCon.acceptTrade: Working??");
		
		int curPlayerIndex = SessionManager.instance().getPlayerIndex();

		AcceptTrade_Input newOffer = new AcceptTrade_Input(curPlayerIndex, willAccept);
		
		SessionManager.instance().getClientFacade().acceptTrade(newOffer);
		
		getAcceptOverlay().closeModal();
	}

}

