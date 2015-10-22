package client.domestic;

import java.util.Observable;
import java.util.Observer;

import shared.definitions.*;
import shared.models.Player;
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
	private PlayerInfo[] players = null;

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
		
		// Set the new players in the game
		//this.tradeOverlay.setPlayers(updatePlayerInfo());
		
		SessionManager.instance().addObserver(this);
	}
		
		
	/**
	* Creates PlayerInfo[] array to be used to display the players to trade with.
	* @return
	*/
	public PlayerInfo[] updatePlayerInfo()
	{
		
		Player[] playerList = SessionManager.instance().getClientModel().getPlayers();
		
		PlayerInfo[] players = new PlayerInfo[4];
	
		for (int a = 0; a < 4; a++)
		{   
			PlayerInfo addPlayer = new PlayerInfo(playerList[a].getName(), playerList[a].getPlayerID(),
		                          playerList[a].getCatanColor(), playerList[a].getIndex());
			
			players[a] = addPlayer;
		}
		return players;
	}

	public void printPlayers() 
	{
		System.out.println("Current Players");
		for (int a = 0; a < 4; a++)
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
		
		if (players == null)
		{
			System.out.println("DomesticTradeController.startTrade: No current players");
			players = updatePlayerInfo();
			this.tradeOverlay.setPlayers(players);
		}
		else
		{
			System.out.println("DomesticTradeController.startTrade: Do nothing");
			// do nothing
		}
		
		getTradeOverlay().reset();

		getTradeOverlay().showModal();
	}

	@Override
	public void decreaseResourceAmount(ResourceType resource) {

	}

	@Override
	public void increaseResourceAmount(ResourceType resource) {

	}

	@Override
	public void sendTradeOffer() {

		getTradeOverlay().closeModal();
//		getWaitOverlay().showModal();
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {

	}

	@Override
	public void setResourceToReceive(ResourceType resource) {

	}

	@Override
	public void setResourceToSend(ResourceType resource) {

	}

	@Override
	public void unsetResource(ResourceType resource) {

	}

	@Override
	public void cancelTrade() {

		getTradeOverlay().closeModal();
	}

	@Override
	public void acceptTrade(boolean willAccept) {

		getAcceptOverlay().closeModal();
	}

}

