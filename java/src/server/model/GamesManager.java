package server.model;

import java.util.ArrayList;
import java.util.List;

import shared.models.ClientModel;
import shared.models.Game;

/**
 * This manager class stores all the games on the current server This class
 * provides the server access to these games This will only be accessed by the
 * ServerManager singleton class
 * 
 * @author Ian
 *
 */
public class GamesManager {

	/**
	 * A list of games stored on the server
	 */
	private List<Game> games = new ArrayList<Game>();

	/**
	 * new GamesManager constructor should add default games?
	 */
	public GamesManager() {

	}

	/**
	 * copy constructor
	 * 
	 * @param games
	 */
	public GamesManager(List<Game> games) {
		this.games = games;
	}

	/**
	 * Adds a new game to the existing list
	 * 
	 * @param newGame
	 *            : the game to add to the current list
	 */
	public void addGame(Game newGame) {
		games.add(newGame);
	}
	
	/**
	 * adds a game that is brand new by setting it's id
	 * @param newGame
	 */
	public void addNewGame(ClientModel model, String name) {
		// Checks to give a unique ID
		int highestID = games.stream().mapToInt(game -> game.getId()).reduce(Integer.MAX_VALUE, Integer::max);
		int newID = highestID + 1;
		Game newGame = new Game(model, name, newID);
		addGame(newGame);
	}
	
	/**
	 * adds game and returns it's ID
	 * @param model
	 * @param name
	 * @return id of game just added
	 */
	public int addNewGameGetID(ClientModel model, String name) {
		// Checks to give a unique ID
		int highestID = games.stream().mapToInt(game -> game.getId()).reduce(Integer.MAX_VALUE, Integer::max);
		int newID = highestID + 1;
		Game newGame = new Game(model, name, newID);
		addGame(newGame);
		return newID;
	}

	/**
	 * Get a game by its id
	 * 
	 * @param id
	 *            : The unique ID of the game to retrieve
	 * @return
	 */
	public Game getGameById(int id) {
		for (Game game : games) {
			if (game.getId() == id) {
				return game;
			}
		}
		System.out.println("game id not found, getGameById is returning null");
		return null;
	}
	
	public ClientModel getClientModelById(int id) {
		for (Game game : games) {
			if (game.getId() == id) {
				return game.getClientModel();
			}
		}
		System.out.println("game id not found, getGameById is returning null");
		return null;
	}

	/**
	 * Returns the list of existing games
	 * 
	 * @return
	 */
	public List<Game> getGames() {
		return games;
	}
}