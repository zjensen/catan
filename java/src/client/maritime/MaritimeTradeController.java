package client.maritime;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import shared.communication.moves.MaritimeTrade_Input;
import shared.definitions.*;
import client.base.*;
import client.session.SessionManager;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController, Observer {

	private IMaritimeTradeOverlay tradeOverlay;
	private ResourceType[] resources = {ResourceType.BRICK,ResourceType.ORE,ResourceType.WHEAT,ResourceType.SHEEP,ResourceType.WOOD};
	private ResourceType[] available;
	private ResourceType give;
	private ResourceType receive;
	private int ratio;
	
	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay) {
		
		super(tradeView);

		setTradeOverlay(tradeOverlay);
		
		SessionManager.instance().addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		getTradeView().enableMaritimeTrade(SessionManager.instance().canPlay());
	}
	
	public IMaritimeTradeView getTradeView() {
		
		return (IMaritimeTradeView)super.getView();
	}
	
	public IMaritimeTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IMaritimeTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	@Override
	public void startTrade() {
		
		getTradeOverlay().showModal();
		getTradeOverlay().setCancelEnabled(true);
		getTradeOverlay().setTradeEnabled(false);
		
		int playerIndex = SessionManager.instance().getPlayerIndex();
		
		ArrayList<ResourceType> toGive = new ArrayList<ResourceType>();
		
		if(SessionManager.instance().getClientFacade().canMaritimeTradeResource(ResourceType.BRICK, playerIndex))
		{
			toGive.add(ResourceType.BRICK);
		}
		if(SessionManager.instance().getClientFacade().canMaritimeTradeResource(ResourceType.WHEAT, playerIndex))
		{
			toGive.add(ResourceType.WHEAT);
		}
		if(SessionManager.instance().getClientFacade().canMaritimeTradeResource(ResourceType.WOOD, playerIndex))
		{
			toGive.add(ResourceType.WOOD);
		}
		if(SessionManager.instance().getClientFacade().canMaritimeTradeResource(ResourceType.ORE, playerIndex))
		{
			toGive.add(ResourceType.ORE);
		}
		if(SessionManager.instance().getClientFacade().canMaritimeTradeResource(ResourceType.SHEEP, playerIndex))
		{
			toGive.add(ResourceType.SHEEP);
		}
		
		ResourceType[] toGiveArray = new ResourceType[toGive.size()];
		for(int i=0;i<toGive.size();i++)
		{
			toGiveArray[i] = toGive.get(i);
		}
		
		available = toGiveArray;
		getTradeOverlay().showGiveOptions(toGiveArray);
	}

	@Override
	public void makeTrade() {
		MaritimeTrade_Input params = new MaritimeTrade_Input(SessionManager.instance().getPlayerIndex(), ratio, give,receive);
		SessionManager.instance().getClientFacade().maritimeTrade(params);
		getTradeOverlay().closeModal();
	}

	@Override
	public void cancelTrade() {

		getTradeOverlay().closeModal();
	}

	@Override
	public void setGetResource(ResourceType r) {
		receive = r;
		getTradeOverlay().selectGetOption(r, 1);
	}

	@Override
	public void setGiveResource(ResourceType r) {
		give = r;
		int playerIndex = SessionManager.instance().getPlayerIndex();
		if(SessionManager.instance().getClientFacade().can2Trade(playerIndex, r))
		{
			ratio = 2;
			getTradeOverlay().selectGiveOption(r, 2);
		}
		else if(SessionManager.instance().getClientFacade().can3Trade(playerIndex, r))
		{
			 ratio = 3;
			getTradeOverlay().selectGiveOption(r, 3);
		}
		else if(SessionManager.instance().getClientFacade().can4Trade(playerIndex, r))
		{
			ratio = 4;
			getTradeOverlay().selectGiveOption(r, 4);
		}
	}

	@Override
	public void unsetGetValue() {
		getTradeOverlay().hideGetOptions();
	}

	@Override
	public void unsetGiveValue() {
		getTradeOverlay().hideGiveOptions();
	}

}

