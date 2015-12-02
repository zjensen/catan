package server.persistence.dao;

import java.util.List;

public interface IGameDAO {
	
	/**
	 * load all the games that are saved
	 * @return list of all saved games
	 */
	public List<String> loadGames();
	
	/**
	 * updates then saves an existing game
	 * @param game to update
	 */
	public void updateGame(String game, int gameID);
	
	/**
	 * save a new game
	 * @param game to be saved
	 */
	public void addGame(String game, int gameID);
	

}
