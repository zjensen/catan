package client.server;

import shared.communication.game.*;
import shared.communication.games.*;
import shared.communication.moves.*;
import shared.communication.user.*;

public interface IServer {
	
	/**
	 * 
	 * @param login_input
	 * @return
	 */
	public Login_Output login(Login_Input login_input);
	
	/**
	 * 
	 * @param register_input
	 * @return
	 */
	public Register_Output register(Register_Input register_input);
	
	/**
	 * 
	 * @param list_games_input
	 * @return
	 */
	public ListGames_Output listGames(ListGames_Input list_games_input);
	
	/**
	 * 
	 * @param create_game_input
	 * @return
	 */
	public CreateGame_Output createGame(CreateGame_Input create_game_input);
	
	/**
	 * 
	 * @param join_game_input
	 * @return
	 */
	public JoinGame_Output joinGame(JoinGame_Input join_game_input);
	
	/**
	 * 
	 * @param save_game_input
	 * @return
	 */
	public SaveGame_Output saveGame(SaveGame_Input save_game_input);
	
	/**
	 * 
	 * @param load_game_input
	 * @return
	 */
	public LoadGame_Output loadGame(LoadGame_Input load_game_input);
	
	/**
	 * 
	 * @param game_model_input
	 * @return
	 */
	public GameModel_Output getModel(GameModel_Input game_model_input);
	
	/**
	 * 
	 * @param reset_game_input
	 * @return
	 */
	public ResetGame_Output resetGame(ResetGame_Input reset_game_input);
	
	/**
	 * 
	 * @param get_commands_input
	 * @return
	 */
	public GETCommands_Output getCommands(GetCommands_Input get_commands_input);
	
	/**
	 * 
	 * @param post_commands_input
	 * @return
	 */
	public POSTCommands_Output postCommands(POSTCommands_Input post_commands_input);
	
	/**
	 * 
	 * @param add_ai_input
	 * @return
	 */
	public AddAI_Output addAI(AddAI_Input add_ai_input);
	
	/**
	 * 
	 * @param list_ai_input
	 * @return
	 */
	public ListAI_Output listAI(ListAI_Input list_ai_input);
	
	/**
	 * 
	 * @param send_chat_input
	 * @return
	 */
	public SendChat_Output sendChat(SendChat_Input send_chat_input);
	
	/**
	 * 
	 * @param roll_number_input
	 * @return
	 */
	public RollNumber_Output rollNumber(RollNumber_Input roll_number_input);
	
	/**
	 * 
	 * @param rob_player_input
	 * @return
	 */
	public RobPlayer_Output robPlayer(RobPlayer_Input rob_player_input);
	
	/**
	 * 
	 * @param finish_turn_input
	 * @return
	 */
	public FinishTurn_Output finishTurn(FinishTurn_Input finish_turn_input);
	
	/**
	 * 
	 * @param buy_dev_card_input
	 * @return
	 */
	public BuyDevCard_Output buyDevCard(BuyDevCard_Input buy_dev_card_input);
	
	/**
	 * 
	 * @param year_of_plenty_input
	 * @return
	 */
	public YearOfPlenty_Output yearOfPlenty(YearOfPlenty_Input year_of_plenty_input);
	
	/**
	 * 
	 * @param road_building_input
	 * @return
	 */
	public RoadBuilding_Output roadBuilding(RoadBuilding_Input road_building_input);
	
	/**
	 * 
	 * @param soldier_input
	 * @return
	 */
	public Soldier_Output soldier(Soldier_Input soldier_input);
	
	/**
	 * 
	 * @param monopoly_input
	 * @return
	 */
	public Monopoly_Output monopoly(Monopoly_Input monopoly_input);
	
	/**
	 * 
	 * @param monument_input
	 * @return
	 */
	public Monument_Output monument(Monument_Input monument_input);
	
	/**
	 * 
	 * @param build_road_input
	 * @return
	 */
	public BuildRoad_Output buildRoad(BuildRoad_Input build_road_input);
	
	/**
	 * 
	 * @param build_settlement_input
	 * @return
	 */
	public BuildSettlement_Output buildSettlement(BuildSettlement_Input build_settlement_input);
	
	/**
	 * 
	 * @param build_city_input
	 * @return
	 */
	public BuildCity_Output buildCity(BuildCity_Input build_city_input);
	
	/**
	 * 
	 * @param offer_trade_input
	 * @return
	 */
	public OfferTrade_Output offerTrade(OfferTrade_Input offer_trade_input);
	
	/**
	 * 
	 * @param accept_trade_input
	 * @return
	 */
	public AcceptTrade_Output acceptTrade(AcceptTrade_Input accept_trade_input);
	
	/**
	 * 
	 * @param maritime_trade_input
	 * @return
	 */
	public MaritimeTrade_Output maritimeTrade(MaritimeTrade_Input maritime_trade_input);
	
	/**
	 * 
	 * @param discard_cards_input
	 * @return
	 */
	public DiscardCards_Output discardCards(DiscardCards_Input discard_cards_input);
}