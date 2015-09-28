package client.server;

import shared.communication.game.*;
import shared.communication.games.*;
import shared.communication.moves.*;
import shared.communication.user.*;

public class FakeServer implements IServer {
	
	private final String no_user = "The catan.user HTTP cookie is missing.  You must login before calling this method.";
	private final String no_game = "The catan.game HTTP cookie is missing.  You must join a game before calling this method.";
	private final String exampleModel = "{\"deck\": {\"yearOfPlenty\": 2,\"monopoly\": 2,\"soldier\": 14,\"roadBuilding\": 2,\"monument\": 5},"
			+ "\"map\": {\"hexes\": [{\"location\": {\"x\": 0,\"y\": -2}},{\"resource\": \"brick\",\"location\": {\"x\": 1,\"y\": -2},"
			+ "\"number\": 4},{\"resource\": \"wood\",\"location\": {\"x\": 2,\"y\": -2},\"number\": 11},"
			+ "{\"resource\": \"brick\",\"location\": {\"x\": -1,\"y\": -1},\"number\": 8},"
			+ "{\"resource\": \"wood\",\"location\": {\"x\": 0,\"y\": -1},\"number\": 3},"
			+ "{\"resource\": \"ore\",\"location\": {\"x\": 1,\"y\": -1},\"number\": 9},"
			+ "{\"resource\": \"sheep\",\"location\": {\"x\": 2,\"y\": -1},\"number\": 12},"
			+ "{\"resource\": \"ore\",\"location\": {\"x\": -2,\"y\": 0},\"number\": 5},"
			+ "{\"resource\": \"sheep\",\"location\": {\"x\": -1,\"y\": 0},\"number\": 10},"
			+ "{\"resource\": \"wheat\",\"location\": {\"x\": 0,\"y\": 0},\"number\": 11},"
			+ "{\"resource\": \"brick\",\"location\": {\"x\": 1,\"y\": 0},\"number\": 5},"
			+ "{\"resource\": \"wheat\",\"location\": {\"x\": 2,\"y\": 0},\"number\": 6},"
			+ "{\"resource\": \"wheat\",\"location\": {\"x\": -2,\"y\": 1},\"number\": 2},"
			+ "{\"resource\": \"sheep\",\"location\": {\"x\": -1,\"y\": 1},\"number\": 9},"
			+ "{\"resource\": \"wood\",\"location\": {\"x\": 0,\"y\": 1},\"number\": 4},"
			+ "{\"resource\": \"sheep\",\"location\": {\"x\": 1,\"y\": 1},\"number\": 10},"
			+ "{\"resource\": \"wood\",\"location\": {\"x\": -2,\"y\": 2},\"number\": 6},"
			+ "{\"resource\": \"ore\",\"location\": {\"x\": -1,\"y\": 2},\"number\": 3},"
			+ "{\"resource\": \"wheat\",\"location\": {\"x\": 0,\"y\": 2},\"number\": 8}],"
			+ "\"roads\": [],\"cities\": [],\"settlements\": [],\"radius\": 3,\"ports\": [{\"ratio\": 3,"
			+ "\"direction\": \"N\",\"location\": {\"x\": 0,\"y\": 3}},{\"ratio\": 3,\"direction\": \"NW\",\"location\": {\"x\": 2,\"y\": 1}},"
			+ "{\"ratio\": 2,\"resource\": \"sheep\",\"direction\": \"NW\",\"location\": {\"x\": 3,\"y\": -1}},"
			+ "{\"ratio\": 3,\"direction\": \"SW\",\"location\": {\"x\": 3,\"y\": -3}},"
			+ "{\"ratio\": 2,\"resource\": \"wheat\",\"direction\": \"S\",\"location\": {\"x\": -1,\"y\": -2}},"
			+ "{\"ratio\": 2,\"resource\": \"wood\",\"direction\": \"NE\",\"location\": {\"x\": -3,\"y\": 2}},"
			+ "{\"ratio\": 2,\"resource\": \"brick\",\"direction\": \"NE\",\"location\": {\"x\": -2,\"y\": 3}},"
			+ "{\"ratio\": 3,\"direction\": \"SE\",\"location\": {\"x\": -3,\"y\": 0}},"
			+ "{\"ratio\": 2,\"resource\": \"ore\",\"direction\": \"S\",\"location\": {\"x\": 1,\"y\": -3}}],"
			+ "\"robber\": {\"x\": 0,\"y\": -2}},\"players\": [{\"resources\": {\"brick\": 0,\"wood\": 0,\"sheep\": 0,\"wheat\": 0,\"ore\": 0},"
			+ "\"oldDevCards\": {\"yearOfPlenty\": 0,\"monopoly\": 0,\"soldier\": 0,\"roadBuilding\": 0,\"monument\": 0},"
			+ "\"newDevCards\": {\"yearOfPlenty\": 0,\"monopoly\": 0,\"soldier\": 0,\"roadBuilding\": 0,\"monument\": 0},"
			+ "\"roads\": 15,\"cities\": 4,\"settlements\": 5,\"soldiers\": 0,\"victoryPoints\": 0,\"monuments\": 0,\"playedDevCard\": false,"
			+ "\"discarded\": false,\"playerID\": 12,\"playerIndex\": 0,\"name\": \"Test\",\"color\": \"orange\"},null,null,null],"
			+ "\"log\": {\"lines\": []},\"chat\": {\"lines\": []},\"bank\": {\"brick\": 24,\"wood\": 24,\"sheep\": 24,\"wheat\": 24,\"ore\": 24},"
			+ "\"turnTracker\": {\"status\": \"FirstRound\",\"currentTurn\": 0,\"longestRoad\": -1,\"largestArmy\": -1},"
			+ "\"winner\": -1,\"version\": 0}";

	@Override
	public Login_Output login(Login_Input login_input) 
	{
		if(login_input.getUsername() == "valid")
			return new Login_Output("Success");
		return new Login_Output("Failed to login - bad username or password.");
	}

	@Override
	public Register_Output register(Register_Input register_input) 
	{
		if(register_input.getUsername() == "new_username")
			return new Register_Output("Success");
		return new Register_Output("Failed to register - someone already has that username.");
	}

	@Override
	public ListGames_Output listGames(ListGames_Input list_games_input) 
	{
		String response = "[{\"title\": \"Default Game\",\"id\": 0,\"players\": "
				+ "[{\"color\": \"orange\",\"name\": \"Sam\",\"id\": 0},"
				+ "{\"color\": \"blue\",\"name\": \"Brooke\",\"id\": 1},"
				+ "{\"color\": \"red\",\"name\": \"Pete\",\"id\": 10},"
				+ "{\"color\": \"green\",\"name\": \"Mark\",\"id\": 11}]}]";
		return new ListGames_Output(response);
	}

	@Override
	public CreateGame_Output createGame(CreateGame_Input create_game_input) 
	{
		String response = "{\"title\": \"New Game\",\"id\": 1,\"players\": [{},{},{},{}]}";
		return new CreateGame_Output(response);
	}

	@Override
	public JoinGame_Output joinGame(JoinGame_Input join_game_input) 
	{
		String response;
		if(join_game_input.getId() == 1)
			response = "Success";
		else if(join_game_input.getId() > 1)
			response = "The player could not be added to the specified game.";
		else if(join_game_input.getColor().equals("bad_color_value"))
			response = "Invalid request: color has bad value";
		else
			response = this.no_user;
		return new JoinGame_Output(response);
	}

	@Override
	public SaveGame_Output saveGame(SaveGame_Input save_game_input) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoadGame_Output loadGame(LoadGame_Input load_game_input) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModel_Output getModel(GameModel_Input game_model_input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResetGame_Output resetGame(ResetGame_Input reset_game_input) 
	{
		String response;
		if(!reset_game_input.hasCatan_user())
			response = this.no_user;
		else if(!reset_game_input.hasCatan_game())
			response = this.no_game;
		else
			response = this.exampleModel;
		return new ResetGame_Output(response);
	}

	@Override
	public GETCommands_Output getCommands(GetCommands_Input get_commands_input) {
		// TODO Auto-generated method-stub
		return null;
	}

	@Override
	public POSTCommands_Output postCommands(POSTCommands_Input post_commands_input) 
	{
		String response;
		if(!post_commands_input.hasCatan_user())
			response = this.no_user;
		else if(!post_commands_input.hasCatan_game())
			response = this.no_game;
		else
			response = this.exampleModel;
		return new POSTCommands_Output(response);
	}

	@Override
	public AddAI_Output addAI(AddAI_Input add_ai_input) 
	{
		String response;
		if(!add_ai_input.hasCatan_user())
			response = this.no_user;
		else if(!add_ai_input.hasCatan_game())
			response = this.no_game;
		else if(add_ai_input.getAi_type().equals("VALID_AI"))
			response = "Success";
		else
			response = "Could not add AI player  [" + add_ai_input.getAi_type() + "]";
		return new AddAI_Output(response);
	}

	@Override
	public ListAI_Output listAI(ListAI_Input list_ai_input) 
	{
		String response = "[\"LARGEST_ARMY\"]";
		return new ListAI_Output(response);
	}

	@Override
	public SendChat_Output sendChat(SendChat_Input send_chat_input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RollNumber_Output rollNumber(RollNumber_Input roll_number_input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RobPlayer_Output robPlayer(RobPlayer_Input rob_player_input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FinishTurn_Output finishTurn(FinishTurn_Input finish_turn_input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuyDevCard_Output buyDevCard(BuyDevCard_Input buy_dev_card_input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public YearOfPlenty_Output yearOfPlenty(YearOfPlenty_Input year_of_plenty_input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoadBuilding_Output roadBuilding(RoadBuilding_Input road_building_input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Soldier_Output soldier(Soldier_Input soldier_input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Monopoly_Output monopoly(Monopoly_Input monopoly_input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Monument_Output monument(Monument_Input monument_input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuildRoad_Output buildRoad(BuildRoad_Input build_road_input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuildSettlement_Output buildSettlement(BuildSettlement_Input build_settlement_input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuildCity_Output buildCity(BuildCity_Input build_city_input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OfferTrade_Output offerTrade(OfferTrade_Input offer_trade_input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AcceptTrade_Output acceptTrade(AcceptTrade_Input accept_trade_input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MaritimeTrade_Output maritimeTrade(MaritimeTrade_Input maritime_trade_input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DiscardCards_Output discardCards(DiscardCards_Input discard_cards_input) {
		// TODO Auto-generated method stub
		return null;
	}

}
