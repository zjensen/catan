package client.join;

import java.util.Observable;
import java.util.Observer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import shared.communication.game.GameModel_Input;
import shared.communication.game.GameModel_Output;
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
		// TODO Auto-generated method stub
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
		
		getJoinGameView().showModal();
	}

	@Override
	public void startCreateNewGame() {
		
		getNewGameView().showModal();
	}

	@Override
	public void cancelCreateNewGame() {
		
		getNewGameView().closeModal();
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
			SessionManager.instance().getServer().joinGame(jg);
			getNewGameView().closeModal();
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
		SessionManager.instance().setGameInfo(game); //sets the currentGameInfo, which we use to save the gameID
		getSelectColorView().enableAllButtons(); //resets all the buttons
		getSelectColorView().showModal();
		for(PlayerInfo pi : game.getPlayers())
		{
			if(pi.getId()!=SessionManager.instance().getPlayerInfo().getId())
				getSelectColorView().setColorEnabled(pi.getColor(), false); //disables colors already taken
		}
		this.game = game;
		getSelectColorView().showModal();
	}

	@Override
	public void cancelJoinGame() 
	{
		getJoinGameView().closeModal();
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
			getMessageView().setMessage("Error joining game: " + result.getResponse());
			getMessageView().showModal();
		}
	}
}

