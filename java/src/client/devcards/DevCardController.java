package client.devcards;

import java.util.Observable;
import java.util.Observer;

import shared.communication.moves.BuyDevCard_Input;
import shared.communication.moves.Monopoly_Input;
import shared.communication.moves.Monument_Input;
import shared.communication.moves.YearOfPlenty_Input;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.models.Player;
import client.base.*;
import client.session.SessionManager;


/**
 * "Dev card" controller implementation
 */
public class DevCardController extends Controller implements IDevCardController, Observer {

	private IBuyDevCardView buyCardView;
	private IAction soldierAction;
	private IAction roadAction;
	
	/**
	 * DevCardController constructor
	 * 
	 * @param view "Play dev card" view
	 * @param buyCardView "Buy dev card" view
	 * @param soldierAction Action to be executed when the user plays a soldier card.  It calls "mapController.playSoldierCard()".
	 * @param roadAction Action to be executed when the user plays a road building card.  It calls "mapController.playRoadBuildingCard()".
	 */
	public DevCardController(IPlayDevCardView view, IBuyDevCardView buyCardView, 
								IAction soldierAction, IAction roadAction) {

		super(view);
		
		this.buyCardView = buyCardView;
		this.soldierAction = soldierAction;
		this.roadAction = roadAction;
		
		SessionManager.instance().addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		if(arg.equals("reset")) //are all the players here??
		{
			return;
		}
		// TODO Auto-generated method stub
	}
	
	

	public IPlayDevCardView getPlayCardView() {
		return (IPlayDevCardView)super.getView();
	}

	public IBuyDevCardView getBuyCardView() {
		return buyCardView;
	}

	@Override
	public void startBuyCard() {
		System.out.println("DevCardCont.startBuyCard: Called when buy dev card button is clicked");
		getBuyCardView().showModal();
	}

	@Override
	public void cancelBuyCard() {
		System.out.println("DevCardCont.cancelBuyCard: ???");
		getBuyCardView().closeModal();
	}

	
	@Override
	public void buyCard() {
		
		System.out.println("DevCardCont.buyCard: Called from listener");
		
		int player_index = SessionManager.instance().getPlayerIndex();
		
		BuyDevCard_Input newDevCard = new BuyDevCard_Input(player_index);
		
		SessionManager.instance().getClientFacade().buyDevCard(newDevCard);
		
		getBuyCardView().closeModal();
		System.out.println("Should have bought dev card");
	}

	@Override
	public void startPlayCard() {
		
		getPlayCardView().reset();
			
		int player_index = SessionManager.instance().getPlayerIndex();
		
		Player curPlayer = SessionManager.instance().getClientModel().getPlayerByIndex(player_index);
		
		int soldier = curPlayer.getTotalSoldierCards();
		int year_of_plenty = curPlayer.getTotalYearOfPlentyCards();
		int monopoly = curPlayer.getTotalMonopolyCards();
		int road_building = curPlayer.getTotalRoadBuildingCards();
		int monument = curPlayer.getTotalMonumentCards();
				
		getPlayCardView().setCardAmount(DevCardType.SOLDIER, soldier);
		getPlayCardView().setCardAmount(DevCardType.YEAR_OF_PLENTY, year_of_plenty);
		getPlayCardView().setCardAmount(DevCardType.MONOPOLY, monopoly);
		getPlayCardView().setCardAmount(DevCardType.ROAD_BUILD, road_building);
		getPlayCardView().setCardAmount(DevCardType.MONUMENT, monument);
		
		getPlayCardView().setCardEnabled(DevCardType.SOLDIER, curPlayer.canSoldier());
		getPlayCardView().setCardEnabled(DevCardType.YEAR_OF_PLENTY, curPlayer.canYearOfPlenty());
		getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, curPlayer.canMonopoly());
		getPlayCardView().setCardEnabled(DevCardType.ROAD_BUILD, curPlayer.canRoadBuilding());
		getPlayCardView().setCardEnabled(DevCardType.MONUMENT, curPlayer.canMonument());

		getPlayCardView().showModal();
	}

	@Override
	public void cancelPlayCard() {
		getPlayCardView().closeModal();
	}
	
	
	@Override
	public void playMonopolyCard(ResourceType resource) {
		int player_index = SessionManager.instance().getPlayerIndex();
		
		Monopoly_Input newMonopoly = new Monopoly_Input(player_index, resource);
		
		SessionManager.instance().getClientFacade().monopoly(newMonopoly);
	}

	@Override
	public void playMonumentCard() {
		int player_index = SessionManager.instance().getPlayerIndex();
		
		Monument_Input newMonument = new Monument_Input(player_index);
		
		SessionManager.instance().getClientFacade().monument(newMonument);
	}

	@Override
	public void playRoadBuildCard() {
		
		roadAction.execute();
	}

	@Override
	public void playSoldierCard() {
		
		soldierAction.execute();
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) {
		int player_index = SessionManager.instance().getPlayerIndex();
		
		YearOfPlenty_Input newYOP = new YearOfPlenty_Input(player_index, resource1, resource2);
		
		SessionManager.instance().getClientFacade().yearOfPlenty(newYOP);
	}

}

