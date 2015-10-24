package client.discard;

import java.util.Observable;
import java.util.Observer;

import shared.communication.moves.DiscardCards_Input;
import shared.definitions.*;
import shared.models.ResourceCards;
import client.base.*;
import client.misc.*;
import client.session.SessionManager;


/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController, Observer {

	private IWaitView waitView;
	private ResourceCards cards;
	private ResourceCards playersCards;
	private int minimum;
	private int discarded;
	
	/**
	 * DiscardController constructor
	 * 
	 * @param view View displayed to let the user select cards to discard
	 * @param waitView View displayed to notify the user that they are waiting for other players to discard
	 */
	public DiscardController(IDiscardView view, IWaitView waitView) {
		
		super(view);
		
		this.waitView = waitView;
		
		SessionManager.instance().addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		if(arg.equals("reset")) //are all the players here??
		{
			return;
		}
		if(SessionManager.instance().getClientModel().getTurnTracker().getStatus().equalsIgnoreCase("discarding"))
		{
			if(SessionManager.instance().getClientFacade().needsToDiscard(SessionManager.instance().getPlayerIndex()))
			{
				if(!getDiscardView().isModalShowing())
				{
					minimum = SessionManager.instance().getClientFacade().cardsToDiscard(SessionManager.instance().getPlayerIndex());
					discarded = 0;
					playersCards = SessionManager.instance().getClientModel().getPlayerByIndex(SessionManager.instance().getPlayerIndex()).getResources();
					getDiscardView().showModal();
					cards = new ResourceCards();
					
					this.updateDiscardView();
				}
			}
			else
			{
				if(getDiscardView().isModalShowing())
				{
					getDiscardView().closeModal();
				}
				getWaitView().showModal();
			}
		}
		else if(getWaitView().isModalShowing())
		{
			getWaitView().closeModal();
		}
		else if(getDiscardView().isModalShowing())
		{
			getDiscardView().closeModal();
		}
	}

	public IDiscardView getDiscardView() {
		return (IDiscardView)super.getView();
	}
	
	public IWaitView getWaitView() {
		return waitView;
	}
	
	public void updateDiscardView()
	{
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, (discarded<minimum && cards.getWheat()<playersCards.getWheat()), cards.getWheat()>0);
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, (discarded<minimum && cards.getOre()<playersCards.getOre()), cards.getOre()>0);
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, (discarded<minimum && cards.getBrick()<playersCards.getBrick()), cards.getBrick()>0);
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, (discarded<minimum && cards.getSheep()<playersCards.getSheep()), cards.getSheep()>0);
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, (discarded<minimum && cards.getWood()<playersCards.getWood()), cards.getWood()>0);
	
		getDiscardView().setResourceDiscardAmount(ResourceType.WHEAT, cards.getWheat());
		getDiscardView().setResourceDiscardAmount(ResourceType.ORE, cards.getOre());
		getDiscardView().setResourceDiscardAmount(ResourceType.BRICK, cards.getBrick());
		getDiscardView().setResourceDiscardAmount(ResourceType.SHEEP, cards.getSheep());
		getDiscardView().setResourceDiscardAmount(ResourceType.WOOD, cards.getWood());
		
		getDiscardView().setResourceMaxAmount(ResourceType.WHEAT, playersCards.getWheat());
		getDiscardView().setResourceMaxAmount(ResourceType.ORE, playersCards.getOre());
		getDiscardView().setResourceMaxAmount(ResourceType.BRICK, playersCards.getBrick());
		getDiscardView().setResourceMaxAmount(ResourceType.SHEEP, playersCards.getSheep());
		getDiscardView().setResourceMaxAmount(ResourceType.WOOD, playersCards.getWood());
		
		getDiscardView().setDiscardButtonEnabled(discarded==minimum);
		
		getDiscardView().setStateMessage(discarded+"/"+minimum);
	
	}

	@Override
	public void increaseAmount(ResourceType resource) {
		switch(resource)
		{
			case WHEAT:
				cards.setWheat(cards.getWheat()+1);
				break;
			case ORE:
				cards.setOre(cards.getOre()+1);
				break;
			case BRICK:
				cards.setBrick(cards.getBrick()+1);
				break;
			case SHEEP:
				cards.setSheep(cards.getSheep()+1);
				break;
			case WOOD:
				cards.setWood(cards.getWood()+1);
				break;
		}
		discarded++;
		this.updateDiscardView();
	}

	@Override
	public void decreaseAmount(ResourceType resource) 
	{
		switch(resource)
		{
			case WHEAT:
				cards.setWheat(cards.getWheat()-1);
				break;
			case ORE:
				cards.setOre(cards.getOre()-1);
				break;
			case BRICK:
				cards.setBrick(cards.getBrick()-1);
				break;
			case SHEEP:
				cards.setSheep(cards.getSheep()-1);
				break;
			case WOOD:
				cards.setWood(cards.getWood()-1);
				break;
		}
		discarded--;
		this.updateDiscardView();
	}

	@Override
	public void discard() 
	{
		if(cards.getTotal() == minimum)
		{
			getDiscardView().closeModal();
			DiscardCards_Input params = new DiscardCards_Input(SessionManager.instance().getPlayerIndex(), cards);
			SessionManager.instance().getClientFacade().discardCards(params);
		}
	}

}

