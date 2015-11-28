package client.join;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import shared.communication.games.CreateGame_Input;
import shared.communication.games.CreateGame_Output;
import shared.communication.games.JoinGame_Input;
import shared.communication.games.JoinGame_Output;
import shared.communication.games.ListGames_Input;
import shared.communication.games.ListGames_Output;
import shared.definitions.CatanColor;
import client.base.*;
import client.data.*;
import client.misc.*;
import client.session.SessionManager;


/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements IJoinGameController, Observer {

	private INewGameView newGameView;
	private ISelectColorView selectColorView;
	private IMessageView messageView;
	private IAction joinAction;
	private GameInfo game;
	private Timer gameTimer = new Timer(false);
	private GameInfo[] empty = {};
	
	/**
	 * JoinGameController constructor
	 * 
	 * @param view Join game view
	 * @param newGameView New game view
	 * @param selectColorView Select color view
	 * @param messageView Message view (used to display error messages that occur while the user is joining a game)
	 */
	public JoinGameController(IJoinGameView view, INewGameView newGameView, 
								ISelectColorView selectColorView, IMessageView messageView) {

		super(view);

		setNewGameView(newGameView);
		setSelectColorView(selectColorView);
		setMessageView(messageView);
		
		SessionManager.instance().addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		if(arg.equals(true))
		{
			if(getJoinGameView().isModalShowing())
			{
				this.getJoinGameView().closeModal();
			}
			return;
		}
		if(arg.equals("reset"))
		{
			getJoinGameView().setGames(empty, SessionManager.instance().getPlayerInfo());
			start();
			return; //might take this out
		}
		if(this.getNewGameView().isModalShowing())
		{
			this.getNewGameView().closeModal();
		}
		ListGames_Output result = null;
		try 
		{
			result = SessionManager.instance().getServer().listGames(new ListGames_Input());
			
		} 
		catch (Exception e) 
		{
			getMessageView().setTitle("Error");
			getMessageView().setMessage("Something went wrong while fetching active games. " + e.getMessage());
			getMessageView().showModal();
		}
		Gson gson = new Gson();
		JsonElement je = gson.fromJson (result.getResponse(), JsonElement.class);
		GameInfo[] updatedInfo = this.getGameInfo(je);
		if(needsUpdate(updatedInfo))
		{
			if(this.getJoinGameView().isModalShowing())
			{
				this.getJoinGameView().closeModal();
			}
			this.getJoinGameView().setGames(updatedInfo, SessionManager.instance().getPlayerInfo());
			if(!getJoinGameView().isModalShowing())
			{
				this.getJoinGameView().showModal();
			}
		}
	}
	
	public IJoinGameView getJoinGameView() {
		
		return (IJoinGameView)super.getView();
	}
	
	/**
	 * Returns the action to be executed when the user joins a game
	 * 
	 * @return The action to be executed when the user joins a game
	 */
	public IAction getJoinAction() {
		
		return joinAction;
	}

	/**
	 * Sets the action to be executed when the user joins a game
	 * 
	 * @param value The action to be executed when the user joins a game
	 */
	public void setJoinAction(IAction value) {	
		
		joinAction = value;
	}
	
	public INewGameView getNewGameView() {
		
		return newGameView;
	}

	public void setNewGameView(INewGameView newGameView) {
		
		this.newGameView = newGameView;
	}
	
	public ISelectColorView getSelectColorView() {
		
		return selectColorView;
	}
	public void setSelectColorView(ISelectColorView selectColorView) {
		
		this.selectColorView = selectColorView;
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	public void setMessageView(IMessageView messageView) {
		
		this.messageView = messageView;
	}

	@Override
	public void start() 
	{
		ListGames_Output result = SessionManager.instance().getServer().listGames(new ListGames_Input());
		if(result.getGames() != null)
		{
			getJoinGameView().setGames(result.getGames(), SessionManager.instance().getPlayerInfo());
		}
		
		if(!getJoinGameView().isModalShowing())
		{
			this.getJoinGameView().showModal();
		}
		startTimer();
	}

	@Override
	public void startCreateNewGame() 
	{
		killTimer();
		getNewGameView().showModal();
	}

	@Override
	public void cancelCreateNewGame() 
	{
		getNewGameView().closeModal();
		startTimer();
	}

	@Override
	public void createNewGame() {
		
		String title = getNewGameView().getTitle();
		if(title.equals(""))
		{
			getMessageView().setTitle("Error");
			getMessageView().setMessage("You cannot create a game with a blank title");
			getMessageView().showModal();
			return;
		}
		boolean randomHexes = getNewGameView().getRandomlyPlaceHexes();
		boolean randomNumbers = getNewGameView().getRandomlyPlaceNumbers();
		boolean randomPorts = getNewGameView().getUseRandomPorts();
		
		
		
		//sets up the request and transmits it through the proxy
		CreateGame_Input create_game_input = new CreateGame_Input(randomHexes, randomNumbers, randomPorts, title);
		
		try 
		{
			CreateGame_Output create_game_output = SessionManager.instance().getServer().createGame(create_game_input);
			Gson gson = new Gson();
			JsonElement gameID = gson.fromJson (create_game_output.getResponse(), JsonElement.class).getAsJsonObject().get("id");
			JoinGame_Input jg = new JoinGame_Input(gameID.getAsInt(), "red");
			getNewGameView().closeModal();
			SessionManager.instance().getServer().joinGame(jg);
			this.start();
		} 
		catch (Exception e) 
		{
			getMessageView().setTitle("Error");
			getMessageView().setMessage("New game could not be created. " + e.getMessage());
			getMessageView().showModal();
		}

	}

	@Override
	public void startJoinGame(GameInfo game) 
	{
		killTimer();
		SessionManager.instance().setGameInfo(game); //sets the currentGameInfo, which we use to save the gameID
		getSelectColorView().enableAllButtons(); //resets all the buttons
		if(!getSelectColorView().isModalShowing())
		{
			getSelectColorView().showModal();
		}
		for(PlayerInfo pi : game.getPlayers())
		{
			if(pi==null)
				continue;
			if(pi.getId()!=SessionManager.instance().getPlayerInfo().getId())
				getSelectColorView().setColorEnabled(pi.getColor(), false); //disables colors already taken
		}
		this.game = game;
		if(!getSelectColorView().isModalShowing())
		{
			getSelectColorView().showModal();
		}
	}

	@Override
	public void cancelJoinGame() 
	{
		startTimer();
		getJoinGameView().closeModal();
		if(!getJoinGameView().isModalShowing())
		{
			this.getJoinGameView().showModal();
		}
	}

	@Override
	public void joinGame(CatanColor color) 
	{
		if(this.game==null)
		{
			//we got an issue mate
			getMessageView().setTitle("Error");
			getMessageView().setMessage("Error joining game: game not set properly");
			getMessageView().showModal();
			return;
		}
		if(!canChooseColor(color))
		{
			getSelectColorView().setColorEnabled(color, false);
			getMessageView().setTitle("Color already chosen");
			getMessageView().setMessage("Somebody beat you to that color! Pick another");
			getMessageView().showModal();
			return;
		}
		JoinGame_Output result = SessionManager.instance().getServer().joinGame(new JoinGame_Input(this.game.getId(), color.toString().toLowerCase()));
		if(result.getResponse().toUpperCase().equals("SUCCESS")) //todo warn on fail
		{
			// If join succeeded
			SessionManager.instance().getPlayerInfo().setColor(color);
			SessionManager.instance().setGameInfo(this.game);
			getSelectColorView().closeModal();
			getJoinGameView().closeModal();
			SessionManager.instance().startPoller();
			joinAction.execute();
		}
		else
		{
			getMessageView().setTitle("Error");
			getMessageView().setMessage("Error joining game: " + result.getResponse() + " (game might be full now)");
			getMessageView().showModal();
		}
	}
	
	private GameInfo[] getGameInfo(JsonElement je)
	{
		ArrayList<GameInfo> gameInfoList = new ArrayList<GameInfo>();
		JsonArray gameArray = je.getAsJsonArray();
		for(int i=0; i < gameArray.size(); ++i){
			Gson gson = new Gson();
			JsonObject game = gameArray.get(i).getAsJsonObject();
			String title = gson.fromJson(game.get("title"), String.class);
			int id = game.get("id").getAsInt();
			GameInfo gi = new GameInfo();
			gi.setId(id);
			gi.setTitle(title);
			populatePlayerInfo(gi, game.get("players").getAsJsonArray());
			
			gameInfoList.add(gi);
		}
		GameInfo[] output = new GameInfo[gameInfoList.size()];
		for(int i=0; i<gameInfoList.size(); ++i){
			output[i] = gameInfoList.get(i);
		}
		return output;
	}
	
	private void populatePlayerInfo(GameInfo gi, JsonArray players) {
		for(int i = 0; i < players.size(); ++i){
			Gson gson = new Gson();
			JsonObject player = players.get(i).getAsJsonObject();
			if(!player.has("name"))
				continue;
			String string_color = gson.fromJson(player.get("color"), String.class);
			CatanColor color;
			switch (string_color.toLowerCase())
			{
				case "orange":
					color = CatanColor.ORANGE;
					break;
				case "blue":
					color = CatanColor.BLUE;
					break;
				case "brown":
					color = CatanColor.BROWN;
					break;
				case "puce":
					color = CatanColor.PUCE;
					break;
				case "purple":
					color = CatanColor.PURPLE;
					break;
				case "red":
					color = CatanColor.RED;
					break;
				case "yellow":
					color = CatanColor.YELLOW;
					break;
				case "white":
					color = CatanColor.WHITE;
					break;
				case "green":
					color = CatanColor.GREEN;
					break;
				default:
					color = null;
					break;
			}
			String name = gson.fromJson(player.get("name"), String.class);
			JsonElement idElement = player.get("id");
			if(idElement==null){
				continue;
			}
			int id = idElement.getAsInt();
			
			PlayerInfo pi = new PlayerInfo();
			pi.setColor(color);
			pi.setName(name);
			pi.setId(id);
			gi.addPlayer(pi);
		}
	}
	
	private boolean canChooseColor(CatanColor color)
	{
		try {
			ListGames_Output result = SessionManager.instance().getServer().listGames(new ListGames_Input());
			Gson gson = new Gson();
			JsonElement je = gson.fromJson (result.getResponse(), JsonElement.class);
			GameInfo[] updatedGames = this.getGameInfo(je); //gets all the games
			for(GameInfo gi : updatedGames){
				if(gi.getId()==SessionManager.instance().getGameInfo().getId()){ //finds our current game
					for(PlayerInfo pi : gi.getPlayers()){ //queries all players in our current game
						if(pi.getColor().equals(color)){  //if one of those players has our same color, fail immediately
							if(!pi.getName().equals(SessionManager.instance().getPlayerInfo().getName())){
								return false;
							}
						}
					}
				}
			}
		} 
		catch (Exception e) 
		{
			getMessageView().setTitle("Error");
			getMessageView().setMessage("Failure joining game, please check your connection (Error: " + e.getMessage() + ")");
			getMessageView().showModal();
		}
		return true;
	}
	
	private boolean needsUpdate(GameInfo[] updatedInfo) 
	{
		GameInfo [] oldInfo = this.getJoinGameView().getGames();
		if(oldInfo.length!=updatedInfo.length){ //new game
			return true;
		}
		else{
			for(int i = 0; i < updatedInfo.length; ++i){
				GameInfo current = updatedInfo[i];
				GameInfo old = oldInfo[i];
				if(current.getId()!=old.getId()){ //game ID changed (deletion and addition)
					return true;
				}
				List<PlayerInfo> oldPlayerInfo = old.getPlayers();
				List<PlayerInfo> currentPlayerInfo = current.getPlayers();
				if(oldPlayerInfo.size()!=currentPlayerInfo.size()){ //new player added
					return true;
				}
				for(int j = 0; j< currentPlayerInfo.size(); ++j){
					if(!oldPlayerInfo.get(j).equals(currentPlayerInfo.get(j))){ //player info updated
						return true;
					}
					else if(!oldPlayerInfo.get(j).getColor().equals(currentPlayerInfo.get(j).getColor())){ //color change
						return true;
					}
				}
				
			}
			return false; //no update needed
		}
	}
	
	private void startTimer() {
		gameTimer.scheduleAtFixedRate( new TimerTask() {
			@Override
			public void run() {
				updateGames();
			}
		} , 0, 5000);
		
	}
	
	private void killTimer() {
		gameTimer.cancel();
		gameTimer = new Timer(false);
		
	}
	
	private void updateGames() {
		if(this.getNewGameView().isModalShowing()){
			this.getNewGameView().closeModal();
		}
		ListGames_Output output = null;
		GameInfo[] updatedInfo = null;
		try {
			output = SessionManager.instance().getServer().listGames(new ListGames_Input());
			updatedInfo = SessionManager.instance().getInterpreter().deserializeGameInfoList(output.getResponse());
			 
			
		} catch (Exception e) {
			getMessageView().setTitle("Error");
			getMessageView().setMessage("Something went wrong while fetching active games. " + e.getMessage());
			getMessageView().showModal();
		}
		if(needsUpdate(updatedInfo)){
			if(getJoinGameView().isModalShowing())
			{
				getJoinGameView().closeModal();
			}
			this.getJoinGameView().setGames(updatedInfo, SessionManager.instance().getPlayerInfo());
			if(!getJoinGameView().isModalShowing())
			{
				getJoinGameView().showModal();
			}
		}
			
	}
}
