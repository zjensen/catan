package server.persistence.provider;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import server.command.ServerCommand;
import server.persistence.dao.*;
import shared.models.Game;
import shared.models.User;

public abstract class IProvider {

	protected IUserDAO userDAO;
	protected IGameDAO gameDAO;
	protected ICommandDAO commandDAO;
	protected int delta;
	
	public IProvider()
	{
		
	}
	
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
		return null;
	}
	
	/**
	 * updates then saves an existing game
	 * @param game to update
	 */
	public void updateGame(Game game)
	{
		return;
	}
	
	/**
	 * save a new game
	 * @param game to be saved
	 */
	public void addGame(Game game)
	{
		
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
		return null;
	}
	
	/**
	 * saves a new command
	 * @param string
	 */
	public void addCommand(String command, int gameID)
	{
		
	}
	
	
}
