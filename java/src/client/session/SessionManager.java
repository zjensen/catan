package client.session;

import java.util.Observable;

import client.data.GameInfo;
import client.data.PlayerInfo;
import client.facade.ClientFacade;
import shared.utils.Interpreter;
import client.poller.Poller;
import client.server.*;
import shared.communication.game.GameModel_Input;
import shared.models.ClientModel;
import shared.models.Player;

public class SessionManager extends Observable{
	
	//Private Data Members
	private ClientFacade clientFacade = new ClientFacade(this.clientModel);
	public ClientModel clientModel = new ClientModel();
	private Poller poller;
	private Interpreter interpreter = new Interpreter();
	private IServer server;
	private PlayerInfo playerInfo;
	private GameInfo gameInfo;
	private boolean started = false;
	private boolean ended = false;
	private PlayerInfo winningPlayer = null;
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
		this.poller = new Poller();
	}
	
	/**
	 * Sets connects to the correct server given the host and port
	 * @param host
	 * @param port
	 */
	public void setServer(String host, String port) {
		this.server = new Server(host, port);//This will eventually pass in both the host and port
	}
	
	/**
	 * Starts the poller when a user joins a game
	 */
	public void startPoller()
	{
		if(poller == null)
		{
			this.poller = new Poller();
			this.poller.setClientModel(this.clientModel);
		}
	}
	
	/**
	 * Starts the poller when a user joins a game
	 */
	public void stopPoller()
	{
		this.poller.stopTimer();
		this.poller = null;
	}
	
	/**
	 * Updates the all client models to the latest version
	 * <pre>
	 * A newer version of the client model has been generated
	 * </pre>
	 * @param newClientModel
	 */
	public void updateClientModels(ClientModel newClientModel) 
	{
		this.clientModel = newClientModel;
		this.clientFacade.setClientModel(newClientModel);
		this.poller.setClientModel(newClientModel);
		
		if(newClientModel.getPlayers().length != 4)
		{
			started = false;
		}
		else if(!started)
		{
			this.updateGameInfo();
			started = true;
		}
		
		this.setChanged();
		this.notifyObservers(started);
	}
	
	//make sure this is updated when games are created and players are still being added
	public void updateGameInfo()
	{
		for(Player p : this.clientModel.getPlayers())
		{
			if(p==null)
				continue;
			int id = p.getPlayerID();
			PlayerInfo pi = new PlayerInfo(p.getName(),id,p.getCatanColor(),p.getIndex());
			gameInfo.updatePlayer(pi);
		}
	}
	
	//--------------------------------------------------------------------------------------------------
	//Getter Methods
	
	public ClientFacade getClientFacade() {
		return clientFacade;
	}

	public Poller getPoller() {
		return poller;
	}

	public IServer getServer() {
		return server;
	}

	public Interpreter getInterpreter() {
		return interpreter;
	}
	
	public static SessionManager get_instance() {
		return _instance;
	}
	
	public ClientModel getClientModel()
	{
		return clientModel;
	}

	public PlayerInfo getPlayerInfo()
	{
		return playerInfo;
	}

	public void setPlayerInfo(PlayerInfo player)
	{
		this.playerInfo = player;
	}
	
	public GameInfo getGameInfo()
	{
		return gameInfo;
	}

	public void setGameInfo(GameInfo gameInfo)
	{
		this.gameInfo = gameInfo;
	}
	
	///////////////////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @return playerIndex of CURRENT PLAYER IN SESSION
	 */
	public int getPlayerIndex()
	{
		return playerInfo.getPlayerIndex();
	}

	public void setPlayerInfo(String jsonString)
	{
		this.playerInfo = interpreter.deserializePlayerInfo(jsonString);
	}
	
	public void setGameInfo(String jsonString)
	{
		this.gameInfo = interpreter.deserializeGameInfo(jsonString);
	}

	/** 
	 * called after user joins a game, to load model, start poller, notify controllers
	 */
	public void setupGame()
	{
		if(this.started)
		{
			return;
		}
		ClientModel initialClientModel = this.clientFacade.getInitialModel(); //loads client model
		int index = initialClientModel.getPlayerIndexByID(this.playerInfo.getId());
		this.playerInfo.setPlayerIndex(index);
		if(poller == null)
		{
			this.poller = new Poller();
		}
		this.updateClientModels(initialClientModel);
	}
	
	public void forceUpdate()
	{
		this.clientModel = this.clientFacade.getInitialModel();
		this.updateClientModels(this.clientModel);
		this.getServer().getModel(new GameModel_Input(-1));
	}
	
	public boolean isOurTurn()
	{
		return clientModel.getTurnTracker().getCurrentTurn() == playerInfo.getPlayerIndex();
	}
	
	public boolean canPlay()
	{
		if(clientModel.getTurnTracker().getCurrentTurn() == playerInfo.getPlayerIndex())
		{
			return clientModel.getTurnTracker().getStatus().equalsIgnoreCase("playing");
		}
		return false;
	}

	public void endGame(int playerIndex) //index of winner
	{
		if(!ended) //if they enter a game that is over, the points controller won't know it yet
		{
			winningPlayer = gameInfo.getPlayerByIndex(playerIndex);
			ended = true;
			this.setChanged();
			this.notifyObservers(started);
		}
	}
	
	public PlayerInfo getWinner()
	{
		return winningPlayer;
	}
	
	public void leaveGame()
	{
		stopPoller();
		
		clientModel = new ClientModel();
		clientFacade = new ClientFacade(this.clientModel);
		gameInfo = new GameInfo();
		started = false;
		ended = false;
		winningPlayer = null;
		
		this.setChanged();
		this.notifyObservers("reset");
	}
}