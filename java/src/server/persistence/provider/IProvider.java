package server.persistence.provider;

import java.util.List;

import shared.models.Game;
import shared.models.User;

public interface IProvider {

	/**
	 * Starts transaction with the database
	 */
	public void startTransaction();
	
	/**
	 * Finishes transaction with the database
	 * @param commit - true if we want to commit, false if we want to rollback
	 */
	public void endTransaction(boolean commit);
	
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
	
	/**
	 * save a list of users
	 * @param users
	 */
	public void saveUsers(List<User> users);
	
	/**
	 * loads all saved users
	 * @return list of all saved users
	 */
	public List<User> loadUsers();
	
	/**
	 * saves a new user
	 * @param user to be saved
	 */
	public void addUser(User user);
	
	/**
	 * saves a list of commands
	 * @param commands to be saved
	 */
	public void saveCommands(List<String> commands);
	
	/**
	 * loads all saved commands
	 * @return list of saved commands
	 */
	public List<String> loadCommands();
	
	/**
	 * saves a new command
	 * @param string
	 */
	public void addCommand(String command, int gameID);
	
	
}
