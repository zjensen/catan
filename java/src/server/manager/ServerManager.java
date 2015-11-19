package server.manager;

import server.facade.FakeGameFacade;
import server.facade.FakeGamesFacade;
import server.facade.FakeMovesFacade;
import server.facade.FakeUserFacade;
import server.facade.GameFacade;
import server.facade.GamesFacade;
import server.facade.IGameFacade;
import server.facade.IGamesFacade;
import server.facade.IMovesFacade;
import server.facade.IUserFacade;
import server.facade.MovesFacade;
import server.facade.UserFacade;
import server.model.GamesManager;
import server.model.UsersManager;

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

	// used by Zack's tests
	public void reset() {
		movesFacade = new MovesFacade();
		gamesFacade = new GamesFacade();
		userFacade = new UserFacade();
		gameFacade = new GameFacade();
		gamesManager = new GamesManager();
		usersManager = new UsersManager();
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

	public GamesManager getGamesManager() {
		return gamesManager;
	}

	public void setGamesManager(GamesManager gamesManager) {
		this.gamesManager = gamesManager;
	}

	public UsersManager getUsersManager() {
		return usersManager;
	}

	public void setUsersManager(UsersManager usersManager) {
		this.usersManager = usersManager;
	}
}
