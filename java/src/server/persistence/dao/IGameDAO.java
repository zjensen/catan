package server.persistence.dao;

import java.util.List;

import shared.models.Game;

public interface IGameDAO {

	/**
	 * Saves a list of games
	 * @param games to be saved
	 */
	public void saveGames(List<Game> games);
	
	/**
	 * load all the games that are saved
	 * @return list of all saved games
	 */
	public List<Game> loadGames();
	
	/**
	 * updates then saves an existing game
	 * @param game to update
	 */
	public void updateGame(Game game);
	
	/**
	 * save a new game
	 * @param game to be saved
	 */
	public void addGame(Game game);
	

}
