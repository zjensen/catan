package server.facade;

import client.server.IServer;
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
import shared.communication.moves.*;
import shared.communication.user.Login_Input;
import shared.communication.user.Login_Output;
import shared.communication.user.Register_Input;
import shared.communication.user.Register_Output;
import shared.models.ClientModel;

public class ServerFacade {
	
	private ClientModel clientModel;
	private IServer server;
	
	/**
	 * Constructs a moves facade
	 * @param clientModel
	 */
	public ServerFacade(ClientModel clientModel) {
		this.clientModel = clientModel;
	}
	
	/**
	 * 
	 * @param playerIndex
	 * @return true if it is this players turn, else false
	 */
	public boolean isPlayersTurn(int playerIndex)
	{
		return (playerIndex == clientModel.getTurnTracker().getCurrentTurn());
	}
	
	/**
	 * @param params
	 * @return true if client can make this move, else false
	 */
	public boolean canSendChat(SendChat_Input params)
	{
		 return(params.getContent() != null && !params.getContent().isEmpty());
	}

	/**
	 * sends a chat message
	 * 
	 * @param params
	 * @return SendChat_Output
	 */
	public boolean sendChat(SendChat_Input params)
	{
		return canSendChat(params);
	}
	
	/**
	 * @param params
	 * @return true if client can make this move, else false
	 */
	public boolean canRollNumber(RollNumber_Input params)
	{
		if(isPlayersTurn(params.getPlayerIndex()))
		{
			if(params.getNumber() <= 12 && params.getNumber() >= 2)
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Used to roll a number at the beginning of your turn
	 * 
	 * @param params
	 * @return SendChat_Output
	 */
	public boolean rollNumber(RollNumber_Input params)
	{
		return canRollNumber(params);
	}
	
	/**
	 * @param params
	 * @return true if client can make this move, else false
	 */
	public boolean canRobPlayer(RobPlayer_Input params) //todo
	{
		//if player is not victim, and it is the players turn
		if(params.getPlayerIndex()!=params.getVictimIndex() && isPlayersTurn(params.getPlayerIndex()))
		{
			return clientModel.canRobPlayer(params);
		}
		return false;
	}
	
	/**
	 * Moves the Robber, selecting the new robber position and the player to rob
	 * 
	 * @param params
	 * @return RobPlayer_Output
	 */
	public boolean robPlayer(RobPlayer_Input params)
	{
		return canRobPlayer(params);
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can buyDevCard with these params, else false
	 */
	public boolean canFinishTurn(FinishTurn_Input params)
	{
		return isPlayersTurn(params.getPlayerIndex());
	}
	
	/**
	 * Used to finish your turn
	 * 
	 * @param params
	 * @return FinishTurn_Output
	 */
	public boolean finishTurn(FinishTurn_Input params)
	{
		return canFinishTurn(params);
	}

	/**
	 * 
	 * @param params
	 * @return true if we can buyDevCard with these params, else false
	 */
	public boolean canBuyDevCard(BuyDevCard_Input params)
	{
		return (clientModel.canBuyDevCard(params) && isPlayersTurn(params.getPlayerIndex()));
	}
	
	/**
	 * Used to buy a development card
	 * 
	 * @param params
	 * @return BuyDevCard_Output
	 */
	public boolean buyDevCard(BuyDevCard_Input params)
	{
		return canBuyDevCard(params);
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can yearOfPlenty with these params, else false
	 */
	public boolean canYearOfPlenty(YearOfPlenty_Input params)
	{
		return( clientModel.canYearOfPlenty(params) && isPlayersTurn(params.getPlayerIndex()));
	}

	/**
	 * Plays a "Year of Plenty" card from your hand to gain 
	 * the two specified resources
	 * 
	 * @param params
	 * @return YearOfPlenty_Output
	 */
	public boolean yearOfPlenty(YearOfPlenty_Input params)
	{
		return canYearOfPlenty(params);
	}

	/**
	 * 
	 * @param params
	 * @return true if we can roadBuilding with these params, else false
	 */
	public boolean canRoadBuilding(RoadBuilding_Input params) //todo
	{
		return( clientModel.canRoadBuilding(params) && isPlayersTurn(params.getPlayerIndex()));
	}
	
	/**
	 * Plays a "Road Building" card from your hand to build 
	 * two roads at the specified locations
	 * 
	 * @param params
	 * @return RoadBuilding_Output
	 */
	public boolean roadBuilding(RoadBuilding_Input params)
	{
		return canRoadBuilding(params);
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can soldier with these params, else false
	 */
	public boolean canSoldier(Soldier_Input params) //todo
	{
		return( clientModel.canSoldier(params) && isPlayersTurn(params.getPlayerIndex()));
	}

	
	/**
	 * Plays a 'Soldier' from your hand, selecting the 
	 * new robber position and player to rob.
	 * 
	 * @param params
	 * @return Soldier_Output
	 */
	public boolean soldier(Soldier_Input params)
	{
		return canSoldier(params);
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can monopoly with these params, else false
	 */
	public boolean canMonopoly(Monopoly_Input params)
	{
		return( clientModel.canMonopoly(params) && isPlayersTurn(params.getPlayerIndex()));
	}

	/**
	 * Plays a 'Monopoly' card from your hand to monopolize the specified resource
	 * 
	 * @param params
	 * @return Monopoly_Output
	 */
	public boolean monopoly(Monopoly_Input params)
	{
		return canMonopoly(params);
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can monument with these params, else false
	 */
	public boolean canMonument(Monument_Input params)
	{
		return( clientModel.canMonument(params) && isPlayersTurn(params.getPlayerIndex()));
	}
	
	/**
	 * Plays a 'Monument' card from your hand to give you a victory point
	 * 
	 * @param params
	 * @return Monument_Output
	 */
	public boolean monument(Monument_Input params)
	{
		return canMonument(params);
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can buildRoad with these params, else false
	 */
	public boolean canBuildRoad(BuildRoad_Input params) //todo
	{
		return (clientModel.canBuildRoad(params) && isPlayersTurn(params.getPlayerIndex()));
	}
	
	/**
	 * Builds a road at the specified location. 
	 * (Set 'free' to true during initial setup.)
	 * 
	 * @param params
	 * @return BuildRoad_Output
	 */
	public boolean buildRoad(BuildRoad_Input params)
	{
		return canBuildRoad(params);
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can buildSettlement with these params, else false
	 */
	public boolean canBuildSettlement(BuildSettlement_Input params) //todo
	{
		return (clientModel.canBuildSettlement(params) && isPlayersTurn(params.getPlayerIndex()));
	}
	
	/**
	 * Builds a settlement at the specified location. 
	 * (Set 'free' to true during initial setup.)
	 * 
	 * @param params
	 * @return BuildSettlement_Output
	 */
	public boolean buildSettlement(BuildSettlement_Input params)
	{
		return canBuildSettlement(params);
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can buildCity with these params, else false
	 */
	public boolean canBuildCity(BuildCity_Input params)
	{
		return (clientModel.canBuildCity(params) && isPlayersTurn(params.getPlayerIndex()));
	}
	
	/**
	 * Builds a city at the specified location.
	 * 
	 * @param params
	 * @return BuildCity_Output
	 */
	public boolean buildCity(BuildCity_Input params)
	{
		return canBuildCity(params);
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can offerTrade with these params, else false
	 */
	public boolean canOfferTrade(OfferTrade_Input params)
	{
		return(clientModel.canOfferTrade(params) && isPlayersTurn(params.getPlayerIndex()) && params.getPlayerIndex()!=params.getReceiver());
	}
	
	/**
	 * Offers a domestic trade to another player
	 * 
	 * @param params
	 * @return OfferTrade_Output
	 */
	public boolean offerTrade(OfferTrade_Input params) 
	{
		return canOfferTrade(params);
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can acceptTrade with these params, else false
	 */
	public boolean canAcceptTrade(AcceptTrade_Input params) //todo
	{
		return( clientModel.canAcceptTrade(params));
	}
	
	/**
	 * Used to accept or reject a trade offered to you
	 * 
	 * @param params
	 * @return AcceptTrade_Output
	 */
	public boolean acceptTrade(AcceptTrade_Input params)
	{
		return canAcceptTrade(params);
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can maritimeTrade with these params, else false
	 */
	public boolean canMaritimeTrade(MaritimeTrade_Input params) //todo
	{
		return( clientModel.canMaritimeTrade(params) && isPlayersTurn(params.getPlayerIndex()));
	}
	
	/**
	 * Used to execute a maritime trade
	 * 
	 * @param params
	 * @return MaritimeTrade_Output
	 */
	public boolean maritimeTrade(MaritimeTrade_Input params)
	{
		return canMaritimeTrade(params);
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can discardCards with these params, else false
	 */
	public boolean canDiscardCards(DiscardCards_Input params)
	{
		return (clientModel.canDiscardCards(params));
	}

	
	/**
	 * Discards the specified resource cards.
	 * 
	 * @param params
	 * @return DiscardCards_Output
	 */
	public boolean discardCards(DiscardCards_Input params)
	{
		return canDiscardCards(params);
	}

	//*************************************************************************
	//FORMERLY GAME FACADE
	//*************************************************************************
	/**
	 * Returns the current state of the game in JSON format
	 * 
	 * @param params
	 * @return GameModel_Output (JSON data)
	 */
	public GameModel_Output model(GameModel_Input params){
		return server.getModel(params);
	}
	
	/**
	 * Clears out the command history of the current game
	 * 
	 * @param params
	 * @return ResetGame_Output
	 */
	public ResetGame_Output reset(ResetGame_Input params){
		return server.resetGame(params);
	}
	
	/**
	 * Executes the specified command list in the current game
	 * 
	 * @param params
	 * @return POSTCommands_Output
	 */
	public POSTCommands_Output postCommands(POSTCommands_Input params){
		return server.postCommands(params);
	}
	
	/**
	 * Returns a list of commands that have been executed in the current game
	 * 
	 * @param params
	 * @return GETCommands_Output
	 */
	public GETCommands_Output getCommands(GetCommands_Input params){
		return server.getCommands(params);
	}
	
	/**
	 * Adds an AI player to the current game
	 * 
	 * @param params
	 * @return AddAI_Output
	 */
	public AddAI_Output addAI(AddAI_Input params){
		return server.addAI(params);
	}
	
	/**
	 * Returns a list of supported AI player types 
	 * (currently, LARGEST_ARMY is the only supported type)
	 * 
	 * @param params
	 * @return ListAI_Output
	 */
	public ListAI_Output listAI(ListAI_Input params){
		return server.listAI(params);
	}

	//*************************************************************************
	//FORMERLY GAMES FACADE
	//*************************************************************************
	/**
	 * Returns the list of games 
	 * 
	 * @param params
	 * @return ListGames_Output
	 */
	public ListGames_Output list(ListGames_Input params){
		return server.listGames(params);
	}
	
	/**
	 * Creates a new game
	 * 
	 * @param params
	 * @return
	 */
	public CreateGame_Output create(CreateGame_Input params){
		
		return server.createGame(params);
	}
	
	/**
	 * Joins a game that has was started by another player or a game 
	 * the player started
	 * 
	 * @param params
	 * @return JoinGame_Output
	 */
	public JoinGame_Output join(JoinGame_Input params){

		return server.joinGame(params);
	}
	
	/**
	 * Saves the current game and all associated details about the game state 
	 * 
	 * @param params
	 * @return SaveGame_Output
	 */
	public SaveGame_Output save(SaveGame_Input params){
		return server.saveGame(params);
	}
	
	/**
	 * Loads a game that was previously saved
	 * 
	 * @param params
	 * @return LoadGame_Output
	 */
	public LoadGame_Output load(LoadGame_Input params){
		return server.loadGame(params);
	}
	
	
	//*************************************************************************
	// FORMERLY USER FACADE
	//*************************************************************************
	/**
	 * Logs into the server with the provided credentials
	 * 
	 * @param params
	 * @return Login_Output
	 */
	public Login_Output login(Login_Input params){
		
		return server.login(params);
	}
	
	/**
	 * Registers a user with the provided credentials
	 * 
	 * @param params
	 * @return Register_Output
	 */
	public Register_Output register(Register_Input params){
		
		return server.register(params);
	}
	
	
	
	//*************************************************************************
	// GETTERS AND SETTERS
	//*************************************************************************
	
	public ClientModel getClientModel() {
		return clientModel;
	}

	public void setClientModel(ClientModel clientModel) {
		this.clientModel = clientModel;
	}

	public IServer getServer() {
		return server;
	}

	public void setServer(IServer server) {
		this.server = server;
	}
}
