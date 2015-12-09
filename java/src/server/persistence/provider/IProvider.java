package server.persistence.provider;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import server.command.ServerCommand;
import server.manager.ServerManager;
import server.persistence.dao.*;
import shared.models.Game;
import shared.models.User;
import shared.utils.Interpreter;

public abstract class IProvider {

	protected IUserDAO userDAO;
	protected IGameDAO gameDAO;
	protected ICommandDAO commandDAO;
	protected int delta;
	private Interpreter interpreter = new Interpreter();
	
	public IProvider()
	{
		
	}
	
	/**
	 * Resets persistence provider
	 */
	public abstract void clean();
	
	/**
	 * Starts transaction with the database
	 */
	public abstract void startTransaction();
	
	/**
	 * Finishes transaction with the database
	 * @param commit - true if we want to commit, false if we want to rollback
	 */
	public abstract void endTransaction(boolean commit);
	
	/**
	 * load all the games that are saved
	 * @return list of all saved games
	 */
	public List<Game> loadGames()
	{
		List<String> gameStrings = gameDAO.loadGames();
		List<Game> games = new ArrayList<Game>();
		for(String gameString : gameStrings)
		{
			Game g = interpreter.deserializeGame(gameString);
			games.add(g);
		}
		return games;
	}
	
	/**
	 * updates then saves an existing game
	 * @param game to update
	 */
	public void updateGame(Game game)
	{
		String gameString = interpreter.serializeGame(game);
		gameDAO.updateGame(gameString, game.getId());
	}
	
	/**
	 * save a new game
	 * @param game to be saved
	 */
	public void addGame(Game game)
	{
		String gameString = interpreter.serializeGame(game);
		gameDAO.addGame(gameString, game.getId());
	}
	
	/**
	 * loads all saved users
	 * @return list of all saved users
	 */
	public List<User> loadUsers()
	{
		List<String> userStrings = userDAO.loadUsers();
		List<User> users = new ArrayList<User>();
		for(String userString : userStrings)
		{
			try 
			{
				byte b[] = userString.getBytes("ISO-8859-1");
				ByteArrayInputStream bi = new ByteArrayInputStream(b);
				ObjectInputStream si = new ObjectInputStream(bi);
				User u = (User) si.readObject();
				users.add(u);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		return users;
	}
	
	/**
	 * saves a new user
	 * @param user to be saved
	 */
	public void addUser(User user)
	{
		String userString = user.serialize();
		int userID = user.getPlayerID();
		userDAO.addUser(userString,userID);
	}
	
	/**
	 * loads all saved commands
	 * @return list of saved commands
	 */
	public List<ServerCommand> loadCommands()
	{
		List<String> commmandStrings = commandDAO.loadCommands();
		List<ServerCommand> commands = new ArrayList<ServerCommand>();
		for(String commmandString : commmandStrings)
		{
			try 
			{
				commmandString = commmandString.trim();
				byte b[] = commmandString.getBytes("ISO-8859-1");
				ByteArrayInputStream bi = new ByteArrayInputStream(b);
				ObjectInputStream si = new ObjectInputStream(bi);
				ServerCommand c = (ServerCommand) si.readObject();
				si.close();
				c.gson = new Gson();
				commands.add(c);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		return commands;
	}
	
	/**
	 * saves a new command
	 * @param string
	 */
	public void addCommand(String command, int gameID)
	{
		commandDAO.addCommand(command, gameID);
		int savedCommands = commandDAO.getNumberOfSavedCommands(gameID);
		if(savedCommands >= delta)
		{
			updateGame(ServerManager.instance().getGamesManager().getGameById(gameID));
			commandDAO.purge(gameID);
		}
	}

	public void setDelta(int delta_in) {
		this.delta = delta_in;
	}
	
	public Interpreter getInterpreter() {
		return interpreter;
	}
	
	public IUserDAO getUserDAO() {
		return userDAO;
	}

	public IGameDAO getGameDAO() {
		return gameDAO;
	}

	public ICommandDAO getCommandDAO() {
		return commandDAO;
	}

	public int getDelta() {
		return delta;
	}
	
}
