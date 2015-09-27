package client.server;

import shared.communication.game.*;
import shared.communication.games.*;
import shared.communication.moves.*;
import shared.communication.user.*;

public class FakeServer implements IServer {

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
	public CreateGame_Output createGame(CreateGame_Input create_game_input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JoinGame_Output joinGame(JoinGame_Input join_game_input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SaveGame_Output saveGame(SaveGame_Input save_game_input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoadGame_Output loadGame(LoadGame_Input load_game_input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModel_Output getModel(GameModel_Input game_model_input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResetGame_Output resetGame(ResetGame_Input reset_game_input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GETCommands_Output getCommands(GetCommands_Input get_commands_input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public POSTCommands_Output postCommands(POSTCommands_Input post_commands_input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AddAI_Output addAI(AddAI_Input add_ai_input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListAI_Output listAI(ListAI_Input list_ai_input) {
		// TODO Auto-generated method stub
		return null;
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
