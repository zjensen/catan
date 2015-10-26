package client.resources;

import java.util.*;

import client.base.*;
import client.session.SessionManager;
import shared.definitions.ResourceType;


/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends Controller implements IResourceBarController, Observer {

	private Map<ResourceBarElement, IAction> elementActions;
	
	public ResourceBarController(IResourceBarView view) {

		super(view);
		
		elementActions = new HashMap<ResourceBarElement, IAction>();
		
		SessionManager.instance().addObserver(this);
	}

	@Override
	public IResourceBarView getView() {
		return (IResourceBarView)super.getView();
	}

	/**
	 * Sets the action to be executed when the specified resource bar element is clicked by the user
	 * 
	 * @param element The resource bar element with which the action is associated
	 * @param action The action to be executed
	 */
	public void setElementAction(ResourceBarElement element, IAction action) {

		elementActions.put(element, action);
	}

	@Override
	public void buildRoad() {
		executeElementAction(ResourceBarElement.ROAD);
	}

	@Override
	public void buildSettlement() {
		executeElementAction(ResourceBarElement.SETTLEMENT);
	}

	@Override
	public void buildCity() {
		executeElementAction(ResourceBarElement.CITY);
	}

	@Override
	public void buyCard() {
		executeElementAction(ResourceBarElement.BUY_CARD);
	}

	@Override
	public void playCard() {
		executeElementAction(ResourceBarElement.PLAY_CARD);
	}
	
	private void executeElementAction(ResourceBarElement element) {
		
		if (elementActions.containsKey(element)) {
			
			IAction action = elementActions.get(element);
			action.execute();
		}
	}
	
	
	@Override
	public void update(Observable o, Object arg)
	{
		if(arg.equals("reset")) //are all the players here??
		{
			this.getView().setElementAmount(ResourceBarElement.SHEEP, 0);
			this.getView().setElementAmount(ResourceBarElement.WOOD, 0);
			this.getView().setElementAmount(ResourceBarElement.WHEAT, 0);
			this.getView().setElementAmount(ResourceBarElement.BRICK, 0);
			this.getView().setElementAmount(ResourceBarElement.ORE, 0);
			this.getView().setElementAmount(ResourceBarElement.ROAD, 0);
			this.getView().setElementAmount(ResourceBarElement.SETTLEMENT, 0);
			this.getView().setElementAmount(ResourceBarElement.CITY, 0);
			
			this.getView().setElementEnabled(ResourceBarElement.ROAD, false);
			this.getView().setElementEnabled(ResourceBarElement.SETTLEMENT, false);
			this.getView().setElementEnabled(ResourceBarElement.CITY, false);
			this.getView().setElementEnabled(ResourceBarElement.PLAY_CARD, false);
			this.getView().setElementEnabled(ResourceBarElement.SOLDIERS, false);
			this.getView().setElementEnabled(ResourceBarElement.BUY_CARD, false);
			return;
		}
		int index = SessionManager.instance().getPlayerIndex();
		
		//Get the new resource counts after client model update using the players index
		int sheep = SessionManager.instance().getClientFacade().getResourceAmount(index, ResourceType.SHEEP);
		int wood = SessionManager.instance().getClientFacade().getResourceAmount(index, ResourceType.WOOD);
		int wheat = SessionManager.instance().getClientFacade().getResourceAmount(index, ResourceType.WHEAT);
		int brick = SessionManager.instance().getClientFacade().getResourceAmount(index, ResourceType.BRICK);
		int ore = SessionManager.instance().getClientFacade().getResourceAmount(index, ResourceType.ORE);
		
		//Get the building counts after the client model update using player's index
		int roads = SessionManager.instance().getClientFacade().getRoads(index);
		int settlements = SessionManager.instance().getClientFacade().getSettlements(index);
		int cities = SessionManager.instance().getClientFacade().getCities(index);
		int devCardCount = SessionManager.instance().getClientFacade().getOldDevCardCount(index);
		int knightCards = SessionManager.instance().getClientModel().getPlayerByIndex(index).getSoldiers();
//		System.out.println(devCardCount);
		
		//Updating the view to present the most recent number counts
		this.getView().setElementAmount(ResourceBarElement.SHEEP, sheep);
		this.getView().setElementAmount(ResourceBarElement.WOOD, wood);
		this.getView().setElementAmount(ResourceBarElement.WHEAT, wheat);
		this.getView().setElementAmount(ResourceBarElement.BRICK, brick);
		this.getView().setElementAmount(ResourceBarElement.ORE, ore);
		this.getView().setElementAmount(ResourceBarElement.ROAD, roads);
		this.getView().setElementAmount(ResourceBarElement.SETTLEMENT, settlements);
		this.getView().setElementAmount(ResourceBarElement.CITY, cities);
		this.getView().setElementAmount(ResourceBarElement.SOLDIERS, knightCards);
		
		//Enable/Disable Buttons
		if(SessionManager.instance().getClientFacade().isPlayersTurn(index)) {
			if(wood > 0 && brick > 0 && roads > 0) this.getView().setElementEnabled(ResourceBarElement.ROAD, true);
			else this.getView().setElementEnabled(ResourceBarElement.ROAD, false);
			if(sheep > 0 && wood > 0 && wheat > 0 && brick > 0 && settlements > 0) this.getView().setElementEnabled(ResourceBarElement.SETTLEMENT, true);
			else this.getView().setElementEnabled(ResourceBarElement.SETTLEMENT, false);
			if(wheat > 1 && ore > 2 && cities > 0) this.getView().setElementEnabled(ResourceBarElement.CITY, true);
			else this.getView().setElementEnabled(ResourceBarElement.CITY, false);
			if(sheep > 0 && wheat > 0 && ore > 0) this.getView().setElementEnabled(ResourceBarElement.BUY_CARD, true);
			else this.getView().setElementEnabled(ResourceBarElement.BUY_CARD, false);
			if(devCardCount > 0) this.getView().setElementEnabled(ResourceBarElement.PLAY_CARD, true);
			else this.getView().setElementEnabled(ResourceBarElement.PLAY_CARD, false);
		}
		else {
			this.getView().setElementEnabled(ResourceBarElement.ROAD, false);
			this.getView().setElementEnabled(ResourceBarElement.SETTLEMENT, false);
			this.getView().setElementEnabled(ResourceBarElement.CITY, false);
			this.getView().setElementEnabled(ResourceBarElement.BUY_CARD, false);
			this.getView().setElementEnabled(ResourceBarElement.PLAY_CARD, false);
		}
		
	}

}

