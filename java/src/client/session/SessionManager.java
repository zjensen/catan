package client.session;

import org.junit.experimental.theories.Theories;

import client.poller.Poller;
import client.server.*;
import server.facade.ServerFacade;
import server.facade.game.GameFacade;
import server.facade.games.GamesFacade;
import server.facade.user.UserFacade;
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
	private UserFacade userFacade = new UserFacade();
	private GameFacade gameFacade = new GameFacade();
	private GamesFacade gamesFacade = new GamesFacade();
	private ServerFacade movesFacade = new ServerFacade(this.clientModel);
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
		this.userFacade.setServer(server);
		this.gamesFacade.setServer(server);
		this.gameFacade.setServer(server);
		this.movesFacade.setServer(server);
	}
	
	/**
	 * Sets connects to the correct server given the host and port
	 * @param host
	 * @param port
	 */
	public void setServer(String host, String port) {
		this.server = new Server();//This will eventually pass in both the host and port
		this.poller = new Poller(this.server);
		this.userFacade.setServer(server);
		this.gamesFacade.setServer(server);
		this.gameFacade.setServer(server);
		this.movesFacade.setServer(server);
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
		this.gameFacade.setClientModel(newClientModel);
		this.movesFacade.setClientModel(newClientModel);
	}
	
	//--------------------------------------------------------------------------------------------------
	//User Facade Calls
	
	/**
	 * Sends the login info to the user facade
	 * @param loginInput
	 * @return
	 */
	public Login_Output loginIntoServer(Login_Input loginInput) {
		return this.userFacade.login(loginInput);
	}
	
	/**
	 * Sends the registration info to the user facade
	 * @param registerInput
	 * @return
	 */
	public Register_Output registerNewUser(Register_Input registerInput) {
		return this.userFacade.register(registerInput);
	}
	
	//--------------------------------------------------------------------------------------------------
	//Games Facade Calls
	
	/**
	 * Sends the games list info to the games facade
	 * @param listGamesInput
	 * @return
	 */
	public ListGames_Output list(ListGames_Input listGamesInput){
		return this.gamesFacade.list(listGamesInput);
	}
	
	/**
	 * Sends the create game info to the games facade
	 * @param createGamesInput
	 * @return
	 */
	public CreateGame_Output create(CreateGame_Input createGameInput){
		return this.gamesFacade.create(createGameInput);
	}
	
	/**
	 * Sends join game info to the games facade
	 * @param joinGamesInput
	 * @return
	 */
	public JoinGame_Output join(JoinGame_Input joinGameInput){
		return this.gamesFacade.join(joinGameInput);
	}
	
	/**
	 * Sends save game info to games facade
	 * @param saveGameInput
	 * @return
	 */
	public SaveGame_Output save(SaveGame_Input saveGameInput){
		return this.gamesFacade.save(saveGameInput);
	}
	
	/**
	 * Sends load game info to the games facade
	 * @param loadGameInput
	 * @return
	 */
	public LoadGame_Output load(LoadGame_Input loadGameInput){
		return this.gamesFacade.load(loadGameInput);
	}
	
	//--------------------------------------------------------------------------------------------------
	//Game Facade Calls
	
	/**
	 * Sends game model info to game facade
	 * @param gameModelInput
	 * @return
	 */
	public GameModel_Output model(GameModel_Input gameModelInput){
		return this.gameFacade.model(gameModelInput);
	}
	
	/**
	 * Sends game reset info to game facade
	 * @param resetGameInput
	 * @return
	 */
	public ResetGame_Output reset(ResetGame_Input resetGameInput){
		return this.gameFacade.reset(resetGameInput);
	}
	
	/**
	 * Sends game post commands info to game facade
	 * @param postCommandsInput
	 * @return
	 */
	public POSTCommands_Output postCommands(POSTCommands_Input postCommandsInput){
		return this.gameFacade.postCommands(postCommandsInput);
	}
	
	/**
	 * Sends game get commands info to game facade
	 * @param getCommandsInput
	 * @return
	 */
	public GETCommands_Output getCommands(GetCommands_Input getCommandsInput){
		return this.gameFacade.getCommands(getCommandsInput);
	}
	
	/**
	 * Sends add AI info to game facade
	 * @param addAIInput
	 * @return
	 */
	public AddAI_Output addAI(AddAI_Input addAIInput){
		return this.gameFacade.addAI(addAIInput);
	}
	
	/**
	 * Sends list AI info to game facade
	 * @param listAIInput
	 * @return
	 */
	public ListAI_Output listAI(ListAI_Input listAIInput){
		return this.gameFacade.listAI(listAIInput);
	}

	//--------------------------------------------------------------------------------------------------
	//Moves Facade Calls
	
	/**
	 * Sends player index to moves facade
	 * @param playerIndex
	 * @return
	 */
	public boolean isPlayersTurn(int playerIndex) {
		return this.movesFacade.isPlayersTurn(playerIndex);
	}
	
	/**
	 * Sends can send chat info to moves facade
	 * @param sendChatInput
	 * @return
	 */
	public boolean canSendChat(SendChat_Input canSendChatInput) {
		return this.movesFacade.canSendChat(canSendChatInput);
	}

	/**
	 * Sends send chat info to moves facade
	 * @param sendChatInput
	 * @return
	 */
	public SendChat_Output sendChat(SendChat_Input sendChatInput) {
		return this.movesFacade.sendChat(sendChatInput);
	}
	
	/**
	 * Sends can roll number info to moves facade
	 * @param canRollNumberInput
	 * @return
	 */
	public boolean canRollNumber(RollNumber_Input canRollNumberInput) {
		return this.movesFacade.canRollNumber(canRollNumberInput);
	}
	
	/**
	 * Sends roll number info to moves facade
	 * @param rollNumberInput
	 * @return
	 */
	public RollNumber_Output rollNumber(RollNumber_Input rollNumberInput) {
		return this.movesFacade.rollNumber(rollNumberInput);
	}
	
	/**
	 * Sends can rob player info to moves facade
	 * @param canRobPlayerInput
	 * @return
	 */
	public boolean canRobPlayer(RobPlayer_Input canRobPlayerInput) {
		return this.movesFacade.canRobPlayer(canRobPlayerInput);
	}
	
	/**
	 * Sends rob player info to moves facade
	 * @param robPlayerInput
	 * @return
	 */
	public RobPlayer_Output robPlayer(RobPlayer_Input robPlayerInput) {
		return this.movesFacade.robPlayer(robPlayerInput);
	}
	
	/**
	 * Sends can finish turn info to moves facade
	 * @param canFinishTurnInput
	 * @return
	 */
	public boolean canFinishTurn(FinishTurn_Input canFinishTurnInput) {
		return this.movesFacade.canFinishTurn(canFinishTurnInput);
	}
	
	/**
	 * Sends finish turn info to moves facade
	 * @param finishTurnInput
	 * @return
	 */
	public FinishTurn_Output finishTurn(FinishTurn_Input finishTurnInput) {
		return this.movesFacade.finishTurn(finishTurnInput);
	}

	/**
	 * Sends can buy development card info to moves facade
	 * @param canBuyDevCardInput
	 * @return
	 */
	public boolean canBuyDevCard(BuyDevCard_Input canBuyDevCardInput) {
		return this.movesFacade.canBuyDevCard(canBuyDevCardInput);
	}
	
	/**
	 * Sends buy development card info to moves facade
	 * @param buyDevCardInput
	 * @return
	 */
	public BuyDevCard_Output buyDevCard(BuyDevCard_Input buyDevCardInput) {
		return this.movesFacade.buyDevCard(buyDevCardInput);
	}
	
	/**
	 * Sends can year of plenty info to moves facade
	 * @param canYearOfPlentyInput
	 * @return
	 */
	public boolean canYearOfPlenty(YearOfPlenty_Input canYearOfPlentyInput) {
		return this.movesFacade.canYearOfPlenty(canYearOfPlentyInput);
	}

	/**
	 * Sends year of plenty info to moves facade
	 * @param yearOfPlentyInput
	 * @return
	 */
	public YearOfPlenty_Output yearOfPlenty(YearOfPlenty_Input yearOfPlentyInput) {
		return this.movesFacade.yearOfPlenty(yearOfPlentyInput);
	}

	/**
	 * Sends can road building info to moves facade
	 * @param canRoadBuildingInput
	 * @return
	 */
	public boolean canRoadBuilding(RoadBuilding_Input canRoadBuildingInput) {
		return this.movesFacade.canRoadBuilding(canRoadBuildingInput);
	}
	
	/**
	 * Sends road building info to moves facade
	 * @param roadBuildingInput
	 * @return
	 */
	public RoadBuilding_Output roadBuilding(RoadBuilding_Input roadBuildingInput) {
		return this.movesFacade.roadBuilding(roadBuildingInput);
	}
	
	/**
	 * Sends can soldier info to moves facade
	 * @param canSoldierInput
	 * @return
	 */
	public boolean canSoldier(Soldier_Input canSoldierInput) {
		return this.movesFacade.canSoldier(canSoldierInput);
	}

	
	/**
	 * Sends soldier info to moves facade
	 * @param soldierInput
	 * @return
	 */
	public Soldier_Output soldier(Soldier_Input soldierInput) {
		return this.movesFacade.soldier(soldierInput);
	}
	
	/**
	 * Sends can monopoly info to moves facade
	 * @param canMonopolyInput
	 * @return
	 */
	public boolean canMonopoly(Monopoly_Input canMonopolyInput)	{
		return this.movesFacade.canMonopoly(canMonopolyInput);
	}

	/**
	 * Sends monopoly info to moves facade
	 * @param monopolyInput
	 * @return
	 */
	public Monopoly_Output monopoly(Monopoly_Input monopolyInput) {
		return this.movesFacade.monopoly(monopolyInput);
	}
	
	/**
	 * Sends can monument info to moves facade
	 * @param canMonumentInput
	 * @return
	 */
	public boolean canMonument(Monument_Input canMonumentInput)	{
		return this.movesFacade.canMonument(canMonumentInput);
	}
	
	/**
	 * Sends monument info to moves facade
	 * @param monumentInput
	 * @return
	 */
	public Monument_Output monument(Monument_Input monumentInput) {
		return this.movesFacade.monument(monumentInput);
	}
	
	/**
	 * Sends can build road info to moves facade
	 * @param canBuildRoadInput
	 * @return
	 */
	public boolean canBuildRoad(BuildRoad_Input canBuildRoadInput) {
		return this.movesFacade.canBuildRoad(canBuildRoadInput);
	}
	
	/**
	 * Sends build road info to moves facade
	 * @param buildRoadInput
	 * @return
	 */
	public BuildRoad_Output buildRoad(BuildRoad_Input buildRoadInput) {
		return this.movesFacade.buildRoad(buildRoadInput);
	}
	
	/**
	 * Sends can build settlement info to moves facade
	 * @param canBuildSettlementInput
	 * @return
	 */
	public boolean canBuildSettlement(BuildSettlement_Input canBuildSettlementInput) {
		return this.movesFacade.canBuildSettlement(canBuildSettlementInput);
	}
	
	/**
	 * Sends build settlement info to moves facade
	 * @param buildSettlementInput
	 * @return
	 */
	public BuildSettlement_Output buildSettlement(BuildSettlement_Input buildSettlementInput) {
		return this.movesFacade.buildSettlement(buildSettlementInput);
	}
	
	/**
	 * Sends can build city info to moves facade
	 * @param canBuildCityInput
	 * @return
	 */
	public boolean canBuildCity(BuildCity_Input canBuildCityInput) {
		return this.movesFacade.canBuildCity(canBuildCityInput);
	}
	
	/**
	 * Sends build city info to moves facade
	 * @param buildCityInput
	 * @return
	 */
	public BuildCity_Output buildCity(BuildCity_Input buildCityInput) {
		return this.movesFacade.buildCity(buildCityInput);
	}
	
	/**
	 * Sends can offer trade info to moves facade
	 * @param canOfferTradeInput
	 * @return
	 */
	public boolean canOfferTrade(OfferTrade_Input canOfferTradeInput) {
		return this.movesFacade.canOfferTrade(canOfferTradeInput);
	}
	
	/**
	 * Sends offer trade info to moves facade
	 * @param offerTradeInput
	 * @return
	 */
	public OfferTrade_Output offerTrade(OfferTrade_Input offerTradeInput) {
		return this.movesFacade.offerTrade(offerTradeInput);
	}
	
	/**
	 * Sends can accept trade info to moves facade
	 * @param canAcceptTradeInput
	 * @return
	 */
	public boolean canAcceptTrade(AcceptTrade_Input canAcceptTradeInput) {
		return this.movesFacade.canAcceptTrade(canAcceptTradeInput);
	}
	
	/**
	 * Sends accept trade info to moves facade
	 * @param acceptTradeInput
	 * @return
	 */
	public AcceptTrade_Output acceptTrade(AcceptTrade_Input acceptTradeInput) {
		return this.movesFacade.acceptTrade(acceptTradeInput);
	}
	
	/**
	 * Sends can maritime trade info to moves facade
	 * @param canMaritimeTradeInput
	 * @return
	 */
	public boolean canMaritimeTrade(MaritimeTrade_Input canMaritimeTradeInput) {
		return this.movesFacade.canMaritimeTrade(canMaritimeTradeInput);
	}
	
	/**
	 * Sends maritime trade info to moves facade
	 * @param maritimeTradeInput
	 * @return
	 */
	public MaritimeTrade_Output maritimeTrade(MaritimeTrade_Input maritimeTradeInput) {
		return this.movesFacade.maritimeTrade(maritimeTradeInput);
	}
	
	/**
	 * Sends can discard cards info to moves facade
	 * @param canDiscardCardsInput
	 * @return
	 */
	public boolean canDiscardCards(DiscardCards_Input canDiscardCardsInput) {
		return this.movesFacade.canDiscardCards(canDiscardCardsInput);
	}

	
	/**
	 * Sends discard cards info to moves facade
	 * @param discardCardsInput
	 * @return
	 */
	public DiscardCards_Output discardCards(DiscardCards_Input discardCardsInput) {
		return this.movesFacade.discardCards(discardCardsInput);
	}
}
