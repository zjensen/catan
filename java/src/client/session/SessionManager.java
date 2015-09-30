package client.session;

import org.junit.experimental.theories.Theories;

import client.facade.ClientFacade;
import client.poller.Poller;
import client.server.*;
import shared.communication.game.AddAI_Input;
import shared.communication.game.AddAI_Output;
import shared.communication.game.GETCommands_Output;
import shared.communication.game.GameModel_Input;
import shared.communication.game.GameModel_Output;
import shared.communication.game.GetCommands_Input;
import shared.communication.game.ListAI_Input;
import shared.communication.game.ListAI_Output;
import shared.communication.game.POSTCommands_Input;
import shared.communication.game.POSTCommands_Output;
import shared.communication.game.ResetGame_Input;
import shared.communication.game.ResetGame_Output;
import shared.communication.games.CreateGame_Input;
import shared.communication.games.CreateGame_Output;
import shared.communication.games.JoinGame_Input;
import shared.communication.games.JoinGame_Output;
import shared.communication.games.ListGames_Input;
import shared.communication.games.ListGames_Output;
import shared.communication.games.LoadGame_Input;
import shared.communication.games.LoadGame_Output;
import shared.communication.games.SaveGame_Input;
import shared.communication.games.SaveGame_Output;
import shared.communication.moves.AcceptTrade_Input;
import shared.communication.moves.AcceptTrade_Output;
import shared.communication.moves.BuildCity_Input;
import shared.communication.moves.BuildCity_Output;
import shared.communication.moves.BuildRoad_Input;
import shared.communication.moves.BuildRoad_Output;
import shared.communication.moves.BuildSettlement_Input;
import shared.communication.moves.BuildSettlement_Output;
import shared.communication.moves.BuyDevCard_Input;
import shared.communication.moves.BuyDevCard_Output;
import shared.communication.moves.DiscardCards_Input;
import shared.communication.moves.DiscardCards_Output;
import shared.communication.moves.FinishTurn_Input;
import shared.communication.moves.FinishTurn_Output;
import shared.communication.moves.MaritimeTrade_Input;
import shared.communication.moves.MaritimeTrade_Output;
import shared.communication.moves.Monopoly_Input;
import shared.communication.moves.Monopoly_Output;
import shared.communication.moves.Monument_Input;
import shared.communication.moves.Monument_Output;
import shared.communication.moves.OfferTrade_Input;
import shared.communication.moves.OfferTrade_Output;
import shared.communication.moves.RoadBuilding_Input;
import shared.communication.moves.RoadBuilding_Output;
import shared.communication.moves.RobPlayer_Input;
import shared.communication.moves.RobPlayer_Output;
import shared.communication.moves.RollNumber_Input;
import shared.communication.moves.RollNumber_Output;
import shared.communication.moves.SendChat_Input;
import shared.communication.moves.SendChat_Output;
import shared.communication.moves.Soldier_Input;
import shared.communication.moves.Soldier_Output;
import shared.communication.moves.YearOfPlenty_Input;
import shared.communication.moves.YearOfPlenty_Output;
import shared.communication.user.Login_Input;
import shared.communication.user.Login_Output;
import shared.communication.user.Register_Input;
import shared.communication.user.Register_Output;
import shared.models.ClientModel;

public class SessionManager {
	
	//Private Data Members
	private ClientFacade clientFacade = new ClientFacade(this.clientModel);
	private ClientModel clientModel = new ClientModel();
	private Poller poller;
	private IServer server;
	//--------------------------------------------------------------------------------------------------
	//Singleton Setup
	
	private static SessionManager _instance;
	
	/**
	 * This sets up our SessionManager Singleton
	 * This class will the means of communication between the controllers, the facade, and the proxy
	 * There will be several methods created and implemented here in the future 
	 * @return
	 */
	public static SessionManager instance() {
		if (_instance == null) 
			_instance = new SessionManager();
		return _instance;
	}
	//--------------------------------------------------------------------------------------------------
	//Session Manager Methods

	/**
	 * A test method will connect to the fake server for testing
	 */
	public void setServer() {
		this.server = new FakeServer();
		this.poller = new Poller(this.server);
		this.clientFacade.setServer(server);
	}
	
	/**
	 * Sets connects to the correct server given the host and port
	 * @param host
	 * @param port
	 */
	public void setServer(String host, String port) {
		this.server = new Server();//This will eventually pass in both the host and port
		this.poller = new Poller(this.server);
		this.clientFacade.setServer(server);
	}
	
	/**
	 * Updates the all client models to the latest version
	 * <pre>
	 * A newer version of the client model has been generated
	 * </pre>
	 * @param newClientModel
	 */
	public void updateClientModels(ClientModel newClientModel) {
		this.clientModel = newClientModel;
		this.clientFacade.setClientModel(newClientModel);
	}
	
	//--------------------------------------------------------------------------------------------------
	//User Facade Calls
	
	/**
	 * Sends the login info to the user facade
	 * @param loginInput
	 * @return
	 */
	public Login_Output loginIntoServer(Login_Input loginInput) {
		return this.clientFacade.login(loginInput);
	}
	
	/**
	 * Sends the registration info to the user facade
	 * @param registerInput
	 * @return
	 */
	public Register_Output registerNewUser(Register_Input registerInput) {
		return this.clientFacade.register(registerInput);
	}
	
	//--------------------------------------------------------------------------------------------------
	//Games Facade Calls
	
	/**
	 * Sends the games list info to the games facade
	 * @param listGamesInput
	 * @return
	 */
	public ListGames_Output list(ListGames_Input listGamesInput){
		return this.clientFacade.list(listGamesInput);
	}
	
	/**
	 * Sends the create game info to the games facade
	 * @param createGamesInput
	 * @return
	 */
	public CreateGame_Output create(CreateGame_Input createGameInput){
		return this.clientFacade.create(createGameInput);
	}
	
	/**
	 * Sends join game info to the games facade
	 * @param joinGamesInput
	 * @return
	 */
	public JoinGame_Output join(JoinGame_Input joinGameInput){
		return this.clientFacade.join(joinGameInput);
	}
	
	/**
	 * Sends save game info to games facade
	 * @param saveGameInput
	 * @return
	 */
	public SaveGame_Output save(SaveGame_Input saveGameInput){
		return this.clientFacade.save(saveGameInput);
	}
	
	/**
	 * Sends load game info to the games facade
	 * @param loadGameInput
	 * @return
	 */
	public LoadGame_Output load(LoadGame_Input loadGameInput){
		return this.clientFacade.load(loadGameInput);
	}
	
	//--------------------------------------------------------------------------------------------------
	//Game Facade Calls
	
	/**
	 * Sends game model info to game facade
	 * @param gameModelInput
	 * @return
	 */
	public GameModel_Output model(GameModel_Input gameModelInput){
		return this.clientFacade.model(gameModelInput);
	}
	
	/**
	 * Sends game reset info to game facade
	 * @param resetGameInput
	 * @return
	 */
	public ResetGame_Output reset(ResetGame_Input resetGameInput){
		return this.clientFacade.reset(resetGameInput);
	}
	
	/**
	 * Sends game post commands info to game facade
	 * @param postCommandsInput
	 * @return
	 */
	public POSTCommands_Output postCommands(POSTCommands_Input postCommandsInput){
		return this.clientFacade.postCommands(postCommandsInput);
	}
	
	/**
	 * Sends game get commands info to game facade
	 * @param getCommandsInput
	 * @return
	 */
	public GETCommands_Output getCommands(GetCommands_Input getCommandsInput){
		return this.clientFacade.getCommands(getCommandsInput);
	}
	
	/**
	 * Sends add AI info to game facade
	 * @param addAIInput
	 * @return
	 */
	public AddAI_Output addAI(AddAI_Input addAIInput){
		return this.clientFacade.addAI(addAIInput);
	}
	
	/**
	 * Sends list AI info to game facade
	 * @param listAIInput
	 * @return
	 */
	public ListAI_Output listAI(ListAI_Input listAIInput){
		return this.clientFacade.listAI(listAIInput);
	}

	//--------------------------------------------------------------------------------------------------
	//Moves Facade Calls
	
	/**
	 * Sends player index to moves facade
	 * @param playerIndex
	 * @return
	 */
	public boolean isPlayersTurn(int playerIndex) {
		return this.clientFacade.isPlayersTurn(playerIndex);
	}
	
	/**
	 * Sends can send chat info to moves facade
	 * @param sendChatInput
	 * @return
	 */
	public boolean canSendChat(SendChat_Input canSendChatInput) {
		return this.clientFacade.canSendChat(canSendChatInput);
	}

	/**
	 * Sends send chat info to moves facade
	 * @param sendChatInput
	 * @return
	 */
	public boolean sendChat(SendChat_Input sendChatInput) {
		return this.clientFacade.sendChat(sendChatInput);
	}
	
	/**
	 * Sends can roll number info to moves facade
	 * @param canRollNumberInput
	 * @return
	 */
	public boolean canRollNumber(RollNumber_Input canRollNumberInput) {
		return this.clientFacade.canRollNumber(canRollNumberInput);
	}
	
	/**
	 * Sends roll number info to moves facade
	 * @param rollNumberInput
	 * @return
	 */
	public boolean rollNumber(RollNumber_Input rollNumberInput) {
		return this.clientFacade.rollNumber(rollNumberInput);
	}
	
	/**
	 * Sends can rob player info to moves facade
	 * @param canRobPlayerInput
	 * @return
	 */
	public boolean canRobPlayer(RobPlayer_Input canRobPlayerInput) {
		return this.clientFacade.canRobPlayer(canRobPlayerInput);
	}
	
	/**
	 * Sends rob player info to moves facade
	 * @param robPlayerInput
	 * @return
	 */
	public boolean robPlayer(RobPlayer_Input robPlayerInput) {
		return this.clientFacade.robPlayer(robPlayerInput);
	}
	
	/**
	 * Sends can finish turn info to moves facade
	 * @param canFinishTurnInput
	 * @return
	 */
	public boolean canFinishTurn(FinishTurn_Input canFinishTurnInput) {
		return this.clientFacade.canFinishTurn(canFinishTurnInput);
	}
	
	/**
	 * Sends finish turn info to moves facade
	 * @param finishTurnInput
	 * @return
	 */
	public boolean finishTurn(FinishTurn_Input finishTurnInput) {
		return this.clientFacade.finishTurn(finishTurnInput);
	}

	/**
	 * Sends can buy development card info to moves facade
	 * @param canBuyDevCardInput
	 * @return
	 */
	public boolean canBuyDevCard(BuyDevCard_Input canBuyDevCardInput) {
		return this.clientFacade.canBuyDevCard(canBuyDevCardInput);
	}
	
	/**
	 * Sends buy development card info to moves facade
	 * @param buyDevCardInput
	 * @return
	 */
	public boolean buyDevCard(BuyDevCard_Input buyDevCardInput) {
		return this.clientFacade.buyDevCard(buyDevCardInput);
	}
	
	/**
	 * Sends can year of plenty info to moves facade
	 * @param canYearOfPlentyInput
	 * @return
	 */
	public boolean canYearOfPlenty(YearOfPlenty_Input canYearOfPlentyInput) {
		return this.clientFacade.canYearOfPlenty(canYearOfPlentyInput);
	}

	/**
	 * Sends year of plenty info to moves facade
	 * @param yearOfPlentyInput
	 * @return
	 */
	public boolean yearOfPlenty(YearOfPlenty_Input yearOfPlentyInput) {
		return this.clientFacade.yearOfPlenty(yearOfPlentyInput);
	}

	/**
	 * Sends can road building info to moves facade
	 * @param canRoadBuildingInput
	 * @return
	 */
	public boolean canRoadBuilding(RoadBuilding_Input canRoadBuildingInput) {
		return this.clientFacade.canRoadBuilding(canRoadBuildingInput);
	}
	
	/**
	 * Sends road building info to moves facade
	 * @param roadBuildingInput
	 * @return
	 */
	public boolean roadBuilding(RoadBuilding_Input roadBuildingInput) {
		return this.clientFacade.roadBuilding(roadBuildingInput);
	}
	
	/**
	 * Sends can soldier info to moves facade
	 * @param canSoldierInput
	 * @return
	 */
	public boolean canSoldier(Soldier_Input canSoldierInput) {
		return this.clientFacade.canSoldier(canSoldierInput);
	}

	
	/**
	 * Sends soldier info to moves facade
	 * @param soldierInput
	 * @return
	 */
	public boolean soldier(Soldier_Input soldierInput) {
		return this.clientFacade.soldier(soldierInput);
	}
	
	/**
	 * Sends can monopoly info to moves facade
	 * @param canMonopolyInput
	 * @return
	 */
	public boolean canMonopoly(Monopoly_Input canMonopolyInput)	{
		return this.clientFacade.canMonopoly(canMonopolyInput);
	}

	/**
	 * Sends monopoly info to moves facade
	 * @param monopolyInput
	 * @return
	 */
	public boolean monopoly(Monopoly_Input monopolyInput) {
		return this.clientFacade.monopoly(monopolyInput);
	}
	
	/**
	 * Sends can monument info to moves facade
	 * @param canMonumentInput
	 * @return
	 */
	public boolean canMonument(Monument_Input canMonumentInput)	{
		return this.clientFacade.canMonument(canMonumentInput);
	}
	
	/**
	 * Sends monument info to moves facade
	 * @param monumentInput
	 * @return
	 */
	public boolean monument(Monument_Input monumentInput) {
		return this.clientFacade.monument(monumentInput);
	}
	
	/**
	 * Sends can build road info to moves facade
	 * @param canBuildRoadInput
	 * @return
	 */
	public boolean canBuildRoad(BuildRoad_Input canBuildRoadInput) {
		return this.clientFacade.canBuildRoad(canBuildRoadInput);
	}
	
	/**
	 * Sends build road info to moves facade
	 * @param buildRoadInput
	 * @return
	 */
	public boolean buildRoad(BuildRoad_Input buildRoadInput) {
		return this.clientFacade.buildRoad(buildRoadInput);
	}
	
	/**
	 * Sends can build settlement info to moves facade
	 * @param canBuildSettlementInput
	 * @return
	 */
	public boolean canBuildSettlement(BuildSettlement_Input canBuildSettlementInput) {
		return this.clientFacade.canBuildSettlement(canBuildSettlementInput);
	}
	
	/**
	 * Sends build settlement info to moves facade
	 * @param buildSettlementInput
	 * @return
	 */
	public boolean buildSettlement(BuildSettlement_Input buildSettlementInput) {
		return this.clientFacade.buildSettlement(buildSettlementInput);
	}
	
	/**
	 * Sends can build city info to moves facade
	 * @param canBuildCityInput
	 * @return
	 */
	public boolean canBuildCity(BuildCity_Input canBuildCityInput) {
		return this.clientFacade.canBuildCity(canBuildCityInput);
	}
	
	/**
	 * Sends build city info to moves facade
	 * @param buildCityInput
	 * @return
	 */
	public boolean buildCity(BuildCity_Input buildCityInput) {
		return this.clientFacade.buildCity(buildCityInput);
	}
	
	/**
	 * Sends can offer trade info to moves facade
	 * @param canOfferTradeInput
	 * @return
	 */
	public boolean canOfferTrade(OfferTrade_Input canOfferTradeInput) {
		return this.clientFacade.canOfferTrade(canOfferTradeInput);
	}
	
	/**
	 * Sends offer trade info to moves facade
	 * @param offerTradeInput
	 * @return
	 */
	public boolean offerTrade(OfferTrade_Input offerTradeInput) {
		return this.clientFacade.offerTrade(offerTradeInput);
	}
	
	/**
	 * Sends can accept trade info to moves facade
	 * @param canAcceptTradeInput
	 * @return
	 */
	public boolean canAcceptTrade(AcceptTrade_Input canAcceptTradeInput) {
		return this.clientFacade.canAcceptTrade(canAcceptTradeInput);
	}
	
	/**
	 * Sends accept trade info to moves facade
	 * @param acceptTradeInput
	 * @return
	 */
	public boolean acceptTrade(AcceptTrade_Input acceptTradeInput) {
		return this.clientFacade.acceptTrade(acceptTradeInput);
	}
	
	/**
	 * Sends can maritime trade info to moves facade
	 * @param canMaritimeTradeInput
	 * @return
	 */
	public boolean canMaritimeTrade(MaritimeTrade_Input canMaritimeTradeInput) {
		return this.clientFacade.canMaritimeTrade(canMaritimeTradeInput);
	}
	
	/**
	 * Sends maritime trade info to moves facade
	 * @param maritimeTradeInput
	 * @return
	 */
	public boolean maritimeTrade(MaritimeTrade_Input maritimeTradeInput) {
		return this.clientFacade.maritimeTrade(maritimeTradeInput);
	}
	
	/**
	 * Sends can discard cards info to moves facade
	 * @param canDiscardCardsInput
	 * @return
	 */
	public boolean canDiscardCards(DiscardCards_Input canDiscardCardsInput) {
		return this.clientFacade.canDiscardCards(canDiscardCardsInput);
	}

	
	/**
	 * Sends discard cards info to moves facade
	 * @param discardCardsInput
	 * @return
	 */
	public boolean discardCards(DiscardCards_Input discardCardsInput) {
		return this.clientFacade.discardCards(discardCardsInput);
	}
}
