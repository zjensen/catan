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
