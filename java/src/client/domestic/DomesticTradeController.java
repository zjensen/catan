package client.domestic;

import java.util.Observable;
import java.util.Observer;

import shared.communication.moves.AcceptTrade_Input;
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
//		System.out.println("Current Players");
		for (int a = 0; a < this.players.length; a++)
		{
			this.players[a].toString();
		}
	}
	
	public void fixResource(TradeOffer receivedTrade, ResourceType resource)
	{
		int res_amount = receivedTrade.getOffer().getResourceValue(resource);
		if(resource == null)
		{
			return;
		}
		if (res_amount < 0)
		{
			getAcceptOverlay().addGiveResource(resource, Math.abs(res_amount));
		}
		else if (res_amount > 0)
		{
			getAcceptOverlay().addGetResource(resource, Math.abs(res_amount));
		}
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		getTradeView().enableDomesticTrade(false);
		getAcceptOverlay().setAcceptEnabled(false);
		if(getWaitOverlay().isModalShowing())
		{
			getWaitOverlay().closeModal();
		}
		if(arg.equals("reset")) //are all the players here??
		{
			return;
		}
		if(SessionManager.instance().getClientModel().getTurnTracker().getStatus().equalsIgnoreCase("playing"))
		{
			if(SessionManager.instance().canPlay() && SessionManager.instance().getClientFacade().hasCards(SessionManager.instance().getPlayerIndex()) && SessionManager.instance().getClientModel().getTradeOffer() == null)
			{
				getTradeView().enableDomesticTrade(true);
			}
			else
			{
				getTradeView().enableDomesticTrade(false);
			}
			
			if (SessionManager.instance().getClientModel().getTradeOffer() == null)
			{
				//System.out.println("NO trade to check. Returning");
				return;
			}
			else if(SessionManager.instance().getClientModel().getTradeOffer().getSender() == SessionManager.instance().getPlayerIndex())
			{
				if(!getWaitOverlay().isModalShowing())
				{
					getWaitOverlay().showModal();
				}
				return;
			}
			
//			System.out.println("YES trade to check. proceed with caution");
			
			int cur_player_index = SessionManager.instance().getPlayerIndex();
			int receiver = SessionManager.instance().getClientModel().getTradeOffer().getReceiver();
			
			if (cur_player_index == receiver)
			{
//				System.out.println("This player needs to accept/deny trade");
				
				TradeOffer receivedTrade = new TradeOffer();
				receivedTrade.setSender(SessionManager.instance().getClientModel().getTradeOffer().getSender());
				receivedTrade.setOffer(SessionManager.instance().getClientModel().getTradeOffer().getOffer());
				receivedTrade.setReceiver(SessionManager.instance().getClientModel().getTradeOffer().getReceiver());

				getAcceptOverlay().reset();
				
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
					getAcceptOverlay().showModal();
				}

				if(SessionManager.instance().getClientFacade().canAcceptTrade(new AcceptTrade_Input(receiver, true)))
				{
					getAcceptOverlay().setAcceptEnabled(true);
				}
				else
				{
					getAcceptOverlay().setAcceptEnabled(false);
				}
			}
			else
			{
				//System.out.println("YOU sent trade");
			}
		}
		else
		{
			//System.out.println("\tNot playing");
		}
		
		
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
			getTradeOverlay().setStateMessage("TRADE!");
			getTradeOverlay().setTradeEnabled(true);
		}
		else
		{
			getTradeOverlay().setStateMessage("CAN\'T TRADE.");
			getTradeOverlay().setTradeEnabled(false);
		}
	}
	
	

	@Override
	public void startTrade() {
		curPlayerToTradeTo = -1;
		curOffer.resetAllResourceValues();
		
		if (playersSet == false)
		{
			playersSet = true;
			updatePlayerInfo();
			this.tradeOverlay.setPlayers(players);
		}
		else
		{
//			System.out.println("\nDomTradeCon.startTrade: Do nothing :)");
		}
		
		getTradeOverlay().reset();
		getTradeOverlay().setTradeEnabled(false);
		getTradeOverlay().setStateMessage("TRADE WITH SOMEONE!");
		getTradeOverlay().showModal();
	}

	@Override
	public void decreaseResourceAmount(ResourceType resource) { 
		
		// Check to see if Send or Receive Button was pressed
		if (receiveResources == false)
		{
			Player[] playerList = SessionManager.instance().getClientModel().getPlayers();	
			int curPlayerIndex = SessionManager.instance().getPlayerIndex();
			
			int curPlayerAmount = playerList[curPlayerIndex].getResources().getResourceValue(resource);
			
			curOffer.addOne(resource);
			int newAmount = curOffer.getResourceValue(resource);

			if (Math.abs(newAmount) > 0)
			{
				if (Math.abs(newAmount) < curPlayerAmount)
				{
					getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
				}
				else
				{
					getTradeOverlay().setResourceAmountChangeEnabled(resource, false, true);
				}
			}
			else if (Math.abs(newAmount) == 0)
			{
				if (Math.abs(newAmount) < curPlayerAmount)
				{
					getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
				}
				else
				{
					getTradeOverlay().setResourceAmountChangeEnabled(resource, false, false);
				}
			}
		}
		else // This path is for when the receive option was selected
		{
			curOffer.subtractOne(resource);
			int newAmount = curOffer.getResourceValue(resource);

			if (newAmount > 0)
			{
				getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
				
			}
			else  if (newAmount == 0)
			{
				getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
				
			}
		}
		
		tradeCheck();
	}

	@Override
	public void increaseResourceAmount(ResourceType resource) { 
		
		// Check to see if Send or Receive Button was pressed
		if (receiveResources == false)
		{
			Player[] playerList = SessionManager.instance().getClientModel().getPlayers();	
			int curPlayerIndex = SessionManager.instance().getPlayerIndex();
			
			int curPlayerAmount = playerList[curPlayerIndex].getResources().getResourceValue(resource);
			
			curOffer.subtractOne(resource);
			int newAmount = curOffer.getResourceValue(resource);

			if (Math.abs(newAmount) < curPlayerAmount)
			{
				if (Math.abs(newAmount) > 0)
				{
					getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
				}
				else
				{
					getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
				}
			}
			else  if (Math.abs(newAmount) == curPlayerAmount)
			{
				if (Math.abs(newAmount) > 0)
				{
					getTradeOverlay().setResourceAmountChangeEnabled(resource, false, true);
				}
				else
				{
					getTradeOverlay().setResourceAmountChangeEnabled(resource, false, false);
				}
			}
		}
		else // This path is for when the receive option was selected
		{
			curOffer.addOne(resource);
			int newAmount = curOffer.getResourceValue(resource);
			
			if (newAmount > 0)
			{
				getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
			}
			else  if (newAmount == 0)
			{
				getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);	
			}
		}
		
		tradeCheck();
	}

	@Override
	public void sendTradeOffer() {
		if(getTradeOverlay().isModalShowing())
		{
			getTradeOverlay().closeModal();
		}
		
		if(!getWaitOverlay().isModalShowing())
		{
			getWaitOverlay().showModal();
		}
		
		int curPlayerIndex = SessionManager.instance().getPlayerIndex();
		OfferTrade_Input newTradeOffer = new OfferTrade_Input(curPlayerIndex, curOffer, curPlayerToTradeTo);
		SessionManager.instance().getClientFacade().offerTrade(newTradeOffer);
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {

		if (playerIndex == -1)
		{	
			curPlayerToTradeTo = -1;
			getTradeOverlay().setStateMessage("TRADE WITH SOMEONE!");
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
		receiveResources = true;
		unsetResource(resource);
		curOffer.resetOneResourceValue(resource);
		
        getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
        
        tradeCheck();
	}

	@Override
	public void setResourceToSend(ResourceType resource) { 
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
//			System.out.println("DomTradeCon.setResourceToSend: ERROR: -1 problem");
		}
		
		tradeCheck();
	}

	@Override
	public void unsetResource(ResourceType resource) {
		curOffer.resetOneResourceValue(resource);
		getTradeOverlay().setResourceAmount(resource, "0");
		
		tradeCheck();
	}

	@Override
	public void cancelTrade() {
		getTradeOverlay().closeModal();
	}

	@Override
	public void acceptTrade(boolean willAccept) {
		int curPlayerIndex = SessionManager.instance().getPlayerIndex();
		AcceptTrade_Input newOffer = new AcceptTrade_Input(curPlayerIndex, willAccept);
		
		SessionManager.instance().getClientFacade().acceptTrade(newOffer);
		
		if(getAcceptOverlay().isModalShowing())
		{
			getAcceptOverlay().closeModal();
		}
	}

}

