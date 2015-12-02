package server.persistence.dao;

import java.util.List;

public interface ICommandDAO {
	
	/**
	 * loads all saved commands
	 * @return list of saved commands
	 */
	public List<String> loadCommands();
	
	/**
	 * saves a new command
	 * @param command
	 */
	public void addCommand(String command, int gameID);
	
	/**
	 * DESTROYS ALL COMMANDS belonging to a specific game
	 * @param gameID of the game who's saved commands we want to obliterate
	 */
	public void purge(int gameID);
	
	/**
	 * Returns the number of commands we have saved for a specific game
	 * @param gameID
	 * @return number of commands stored for this game
	 */
	public int getNumberOfSavedCommands(int gameID);

}
