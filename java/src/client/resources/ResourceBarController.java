package client.resources;

import java.util.*;

import client.base.*;
import client.session.SessionManager;
import shared.definitions.ResourceType;
import shared.models.ResourceCards;


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
		
		//Updating the view to present the most recent number counts
		this.getView().setElementAmount(ResourceBarElement.SHEEP, sheep);
		this.getView().setElementAmount(ResourceBarElement.WOOD, wood);
		this.getView().setElementAmount(ResourceBarElement.WHEAT, wheat);
		this.getView().setElementAmount(ResourceBarElement.BRICK, brick);
		this.getView().setElementAmount(ResourceBarElement.ORE, ore);
		this.getView().setElementAmount(ResourceBarElement.ROAD, roads);
		this.getView().setElementAmount(ResourceBarElement.SETTLEMENT, settlements);
		this.getView().setElementAmount(ResourceBarElement.CITY, cities);
		
	}

}

