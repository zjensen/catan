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
	private boolean showing = false;
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
		if(SessionManager.instance().getClientModel().getTurnTracker().getStatus().equalsIgnoreCase("discarding"))
		{
			if(SessionManager.instance().getClientFacade().needsToDiscard(SessionManager.instance().getPlayerIndex()))
			{
				if(!showing)
				{
					minimum = SessionManager.instance().getClientFacade().cardsToDiscard(SessionManager.instance().getPlayerIndex());
					discarded = 0;
					playersCards = SessionManager.instance().getClientModel().getPlayerByIndex(SessionManager.instance().getPlayerIndex()).getResources();
					getDiscardView().showModal();
					showing = true;
					cards = new ResourceCards();
					
					this.updateDiscardView();
				}
			}
			else
			{
				if(showing)
				{
					getDiscardView().closeModal();
				}
				getWaitView().showModal();
				showing = true;
			}
		}
		else if(showing)
		{
			getDiscardView().closeModal();
			getWaitView().closeModal();
			showing = false;
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
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, cards.getWheat()<playersCards.getWheat(), cards.getWheat()>0);
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, cards.getOre()<playersCards.getOre(), cards.getOre()>0);
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, cards.getBrick()<playersCards.getBrick(), cards.getBrick()>0);
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, cards.getSheep()<playersCards.getSheep(), cards.getSheep()>0);
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, cards.getWood()<playersCards.getWood(), cards.getWood()>0);
	
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
		
		getDiscardView().setDiscardButtonEnabled(discarded>=minimum);
		
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
	public void discard() {
		if(cards.getTotal() > minimum)
		{
			getDiscardView().closeModal();
		}
		
	}

}

