package server.persistence.dao;

import java.util.List;

import server.command.ServerCommand;

public interface ICommandDAO {

	/**
	 * saves a list of commands
	 * @param commands to be saved
	 */
//	public void saveCommands(List<ServerCommand> commands);
	
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

}
