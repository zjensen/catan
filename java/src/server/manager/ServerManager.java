package server.manager;

import server.facade.*;
import server.model.*;

public class ServerManager {
	
	public static ServerManager _instance;
	private IUserFacade userFacade;
	private IGameFacade gameFacade;
	private IGamesFacade gamesFacade;
	private IMovesFacade movesFacade;
	private GamesManager gamesManager = new GamesManager();
	private UsersManager usersManager = new UsersManager();
	
	
	public static ServerManager instance() {
		if (_instance == null)
			_instance = new ServerManager();
		return _instance;
	}

	public void setFacades() {
		userFacade = new UserFacade();
		gameFacade = new GameFacade();
		gamesFacade = new GamesFacade();
		movesFacade = new MovesFacade();
	}
	
	public void setFakeFacades() {
		userFacade = new FakeUserFacade();
		gameFacade = new FakeGameFacade();
		gamesFacade = new FakeGamesFacade();
		movesFacade = new FakeMovesFacade();
	}

	public IUserFacade getUserFacade() {
		return userFacade;
	}

	public IGameFacade getGameFacade() {
		return gameFacade;
	}

	public IGamesFacade getGamesFacade() {
		return gamesFacade;
	}

	public IMovesFacade getMovesFacade() {
		return movesFacade;
	}
	
	public GamesManager getGamesManager()
	{
		return gamesManager;
	}

	public void setGamesManager(GamesManager gamesManager)
	{
		this.gamesManager = gamesManager;
	}

	public UsersManager getUsersManager()
	{
		return usersManager;
	}

	public void setUsersManager(UsersManager usersManager)
	{
		this.usersManager = usersManager;
	}
}
