package client.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

import client.ClientException;
import client.data.GameInfo;
import client.session.SessionManager;
import shared.communication.game.*;
import shared.communication.games.*;
import shared.communication.moves.*;
import shared.communication.user.*;

public class Server implements IServer {
	
	private static String SERVER_HOST = "localhost";
	private static int SERVER_PORT = 8081;
	private static String URL_PREFIX = "http://" + SERVER_HOST + ":"
			+ SERVER_PORT;
	private static final String HTTP_GET = "GET";
	private static final String HTTP_POST = "POST";
	private String catan_user = null;
	private String catan_game = null;
	
	public Server()
	{
		
	}
	
	@SuppressWarnings("static-access")
	public Server(String host, String port)
	{
		this.SERVER_PORT = Integer.parseInt(port);
		this.SERVER_HOST = host;
		this.URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	}
	
	public void setCatanUser(String catan_user) {
		this.catan_user = catan_user;
	}
	
	public String getCatanUser() {
		return this.catan_user;
	}
	
	public void setCatanGame(String catan_game) {
		this.catan_game = catan_game;
	}
	
	public String getCatanGame() {
		return this.catan_game;
	}

	@Override
	public Login_Output login(Login_Input login_input) 
	{
		try 
		{
			String result = (String) doPost("/user/login", login_input.toJSON());
			Login_Output login_result = new Login_Output(result);
			return login_result;
		} 
		catch (ClientException e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Register_Output register(Register_Input register_input) 
	{
		try
		{
			String result = (String) doPost("/user/register", register_input.toJSON());
			Register_Output register_result = new Register_Output(result);
			return register_result;
		}
		catch (ClientException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ListGames_Output listGames(ListGames_Input list_games_input) 
	{
		try
		{
			// TODO Switch this method call to doGet?
			String result = (String) doPost("/games/list", list_games_input.toJSON());
			GameInfo[] games = SessionManager.instance().getInterpreter().deserializeGameInfoList(result);
			ListGames_Output list_games_result = new ListGames_Output(result);
			list_games_result.setGames(games);
			return list_games_result;
		}
		catch (ClientException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public CreateGame_Output createGame(CreateGame_Input create_game_input) 
	{
		try
		{
			String result = (String) doPost("/games/create", create_game_input.toJSON());
			CreateGame_Output create_game_result = new CreateGame_Output(result);
			return create_game_result;
		}
		catch (ClientException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JoinGame_Output joinGame(JoinGame_Input join_game_input) 
	{
		try
		{
			String result = (String) doPost("/games/join", join_game_input.toJSON());
			JoinGame_Output join_game_result = new JoinGame_Output(result);
			return join_game_result;
		}
		catch (ClientException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public SaveGame_Output saveGame(SaveGame_Input save_game_input) 
	{
//		try
//		{
//			SaveGame_Output save_game_result = (SaveGame_Output) doPost(
//					"/games/save", save_game_input);
//			return save_game_result;
//		}
//		catch (ClientException e)
//		{
//			e.printStackTrace();
//		}
		// NOT NEEDED FOR PHASE 1
		return null;
	}

	@Override
	public LoadGame_Output loadGame(LoadGame_Input load_game_input) 
	{
//		try
//		{
//			LoadGame_Output load_game_result = (LoadGame_Output) doPost(
//					"/games/load", load_game_input);
//			return load_game_result;
//		}
//		catch (ClientException e)
//		{
//			e.printStackTrace();
//		}
		//NOT NEEDED FOR PHASE 1
		return null;
	}

	@Override
	public GameModel_Output getModel(GameModel_Input game_model_input) 
	{
		try
		{
			String result = (String) doGet("/game/model", game_model_input.getVersion());
			GameModel_Output game_model_result = new GameModel_Output(result);
			return game_model_result;
		}
		catch (ClientException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ResetGame_Output resetGame(ResetGame_Input reset_game_input) 
	{
		try
		{
			String result = (String) doPost("/game/reset", reset_game_input.toJSON());
			ResetGame_Output reset_game_result = new ResetGame_Output(result);
			return reset_game_result;
		}
		catch (ClientException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public GETCommands_Output getCommands(GetCommands_Input get_commands_input) 
	{
//		try
//		{
//			// TODO Switch this method call to doGet?
//			GETCommands_Output get_commands_result = (GETCommands_Output) doPost(
//					"/game/commands", get_commands_input);
//			return get_commands_result;
//		}
//		catch (ClientException e)
//		{
//			e.printStackTrace();
//		}
		//NOT NEEDED FOR PHASE 1
		return null;
	}

	@Override
	public POSTCommands_Output postCommands(POSTCommands_Input post_commands_input) 
	{
//		try
//		{
//			POSTCommands_Output post_commands_result = (POSTCommands_Output) doPost(
//					"/game/commands", post_commands_input);
//			return post_commands_result;
//		}
//		catch (ClientException e)
//		{
//			e.printStackTrace();
//		}
		//NOT NEEDED FOR PHASE 1
		return null;
	}

	@Override
	public AddAI_Output addAI(AddAI_Input add_ai_input) 
	{
		try
		{
			String result = (String) doPost("/game/addAI", add_ai_input.toJSON());
			AddAI_Output add_ai_result = new AddAI_Output(result);
			return add_ai_result;
		}
		catch (ClientException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ListAI_Output listAI(ListAI_Input list_ai_input) 
	{
		try
		{
			// TODO Switch this method call to doGet?
			String result = (String) doPost("/game/listAI", list_ai_input.toJSON());
			ListAI_Output list_ai_result = new ListAI_Output(result);
			return list_ai_result;
		}
		catch (ClientException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public SendChat_Output sendChat(SendChat_Input send_chat_input) 
	{
		try
		{
			String result = (String) doPost("/moves/sendChat", send_chat_input.toJSON());
			SendChat_Output send_chat_result = new SendChat_Output(result);
			return send_chat_result;
		}
		catch (ClientException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public RollNumber_Output rollNumber(RollNumber_Input roll_number_input) 
	{
		try
		{
			String result = (String) doPost("/moves/rollNumber", roll_number_input.toJSON());
			RollNumber_Output roll_number_result = new RollNumber_Output(result);
			return roll_number_result;
		}
		catch (ClientException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public RobPlayer_Output robPlayer(RobPlayer_Input rob_player_input) 
	{
		try
		{
			String result = (String) doPost("/moves/robPlayer", rob_player_input.toJSON());
			RobPlayer_Output rob_player_result = new RobPlayer_Output(result);
			return rob_player_result;
		}
		catch (ClientException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public FinishTurn_Output finishTurn(FinishTurn_Input finish_turn_input) 
	{
		try
		{
			String result = (String) doPost("/moves/finishTurn", finish_turn_input.toJSON());
			FinishTurn_Output finish_turn_result = new FinishTurn_Output(result);
			return finish_turn_result;
		}
		catch (ClientException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BuyDevCard_Output buyDevCard(BuyDevCard_Input buy_dev_card_input) 
	{
		try
		{
			String result = (String) doPost("/moves/buyDevCard", buy_dev_card_input.toJSON());
			BuyDevCard_Output buy_dev_card_result = new BuyDevCard_Output(result);
			return buy_dev_card_result;
		}
		catch (ClientException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public YearOfPlenty_Output yearOfPlenty(YearOfPlenty_Input year_of_plenty_input) 
	{
		try
		{
			String result = (String) doPost("/moves/Year_of_Plenty", year_of_plenty_input.toJSON());
			YearOfPlenty_Output year_of_plenty_result = new YearOfPlenty_Output(result);
			return year_of_plenty_result;
		}
		catch (ClientException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public RoadBuilding_Output roadBuilding(RoadBuilding_Input road_building_input) 
	{
		try
		{
			String result = (String) doPost("/moves/Road_Building", road_building_input.toJSON());
			RoadBuilding_Output road_building_result = new RoadBuilding_Output(result);
			return road_building_result;
		}
		catch (ClientException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Soldier_Output soldier(Soldier_Input soldier_input) 
	{
		try
		{
			String result = (String) doPost("/moves/Soldier", soldier_input.toJSON());
			Soldier_Output soldier_result = new Soldier_Output(result);
			return soldier_result;
		}
		catch (ClientException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Monopoly_Output monopoly(Monopoly_Input monopoly_input) 
	{
		try
		{
			String result = (String) doPost("/moves/Monopoly", monopoly_input.toJSON());
			Monopoly_Output monopoly_result = new Monopoly_Output(result);
			return monopoly_result;
		}
		catch (ClientException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Monument_Output monument(Monument_Input monument_input) 
	{
		try
		{
			String result = (String) doPost("/moves/Monument", monument_input.toJSON());
			Monument_Output monument_result = new Monument_Output(result);
			return monument_result;
		}
		catch (ClientException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BuildRoad_Output buildRoad(BuildRoad_Input build_road_input) 
	{
		try
		{
			String result = (String) doPost("/moves/buildRoad", build_road_input.toJSON());
			BuildRoad_Output build_road_result = new BuildRoad_Output(result);
			return build_road_result;
		}
		catch (ClientException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BuildSettlement_Output buildSettlement(BuildSettlement_Input build_settlement_input) 
	{
		try
		{
			String result = (String) doPost("/moves/buildSettlement", build_settlement_input.toJSON());
			BuildSettlement_Output build_settlement_result = new BuildSettlement_Output(result);
			return build_settlement_result;
		}
		catch (ClientException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BuildCity_Output buildCity(BuildCity_Input build_city_input) 
	{
		try
		{
			String result = (String) doPost("/moves/buildCity", build_city_input.toJSON());
			BuildCity_Output build_city_result = new BuildCity_Output(result);
			return build_city_result;
		}
		catch (ClientException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public OfferTrade_Output offerTrade(OfferTrade_Input offer_trade_input) 
	{
		try
		{
			String result = (String) doPost("/moves/offerTrade", offer_trade_input.toJSON());
			OfferTrade_Output offer_trade_result = new OfferTrade_Output(result);
			return offer_trade_result;
		}
		catch (ClientException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public AcceptTrade_Output acceptTrade(AcceptTrade_Input accept_trade_input) 
	{
		try
		{
			String result = (String) doPost("/moves/acceptTrade", accept_trade_input.toJSON());
			AcceptTrade_Output accept_trade_result = new AcceptTrade_Output(result);
			return accept_trade_result;
		}
		catch (ClientException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public MaritimeTrade_Output maritimeTrade(MaritimeTrade_Input maritime_trade_input) 
	{
		try
		{
			String result = (String) doPost("/moves/maritimeTrade", maritime_trade_input.toJSON());
			MaritimeTrade_Output maritime_trade_result = new MaritimeTrade_Output(result);
			return maritime_trade_result;
		}
		catch (ClientException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public DiscardCards_Output discardCards(DiscardCards_Input discard_cards_input) 
	{
		try
		{
			String result = (String) doPost("/moves/discardCards", discard_cards_input.toJSON());
			DiscardCards_Output discard_cards_result = new DiscardCards_Output(result);
			return discard_cards_result;
		}
		catch (ClientException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	private Object doGet(String commandName, int getData) throws ClientException
	{
		URL url;
		try
		{
			url = new URL(URL_PREFIX + commandName + "?version=" + getData);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			if(catan_user != null && catan_game != null)
				connection.setRequestProperty("Cookie", "catan.user=" + catan_user + "; catan.game=" + catan_game);
			else if(catan_user != null)
				connection.setRequestProperty("Cookie", "catan.user=" + catan_user);
			
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) 
			{			
				InputStream input = connection.getInputStream();
				return extractResponseBody(input);
			}
			else if (connection.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST)
			{
				InputStream input = connection.getErrorStream();
				return extractResponseBody(input);
			}
			else 
			{
				throw new ClientException(String.format(
						"doGet failed: %s (http code %d)", commandName,
						connection.getResponseCode()));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	private Object doPost(String commandName, Object postData) throws ClientException 
	{
		assert commandName != null;
		assert postData != null;

		URL url;
		try 
		{
			url = new URL(URL_PREFIX + commandName);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod(HTTP_POST);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestProperty("Accept", "html/text");
			if(catan_user != null && catan_game != null)
				connection.setRequestProperty("Cookie", "catan.user=" + catan_user + "; catan.game=" + catan_game);
			else if(catan_user != null)
				connection.setRequestProperty("Cookie", "catan.user=" + catan_user);
			connection.connect();
			URL_PREFIX = "http://" + SERVER_HOST + ":"
					+ SERVER_PORT;
			OutputStreamWriter output = new OutputStreamWriter(connection.getOutputStream());
			output.write(postData.toString());
			output.flush();

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) 
			{
				String headerName = null;
				for (int i=1; (headerName = connection.getHeaderFieldKey(i))!=null; i++) 
				{
				 	if (headerName.equals("Set-cookie")) 
				 	{                  
				 		String cookie = connection.getHeaderField(i);
				 		if(cookie.substring(0, 11).equals("catan.user="))
				 		{
				 			catan_user = cookie.substring(11, cookie.indexOf(';'));
				 			SessionManager.instance().setPlayerInfo(URLDecoder.decode(catan_user));
				 		}
				 		else
				 			catan_game = cookie.substring(11, cookie.indexOf(';'));
				 	}
				}
				InputStream input = connection.getInputStream();
				return extractResponseBody(input);
			} 
			else if (connection.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST)
			{
				InputStream input = connection.getErrorStream();
				return extractResponseBody(input);
			}
			else 
			{
				throw new ClientException(String.format(
						"doPost failed: %s (http code %d)", commandName,
						connection.getResponseCode()));
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		return null;
	}

	private String extractResponseBody(InputStream input) throws IOException 
	{
		int len = 0;
		
		byte[] buffer = new byte[1024];
		StringBuilder str = new StringBuilder();
		while(-1 != (len = input.read(buffer)))
		{
			str.append(new String(buffer, 0, len));
		}
		return str.toString();
	}

}
