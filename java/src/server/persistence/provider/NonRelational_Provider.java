package server.persistence.provider;

import java.util.List;

import server.persistence.dao.*;
import shared.models.Game;
import shared.models.User;

public class NonRelational_Provider implements IProvider {
	
	NonRelational_UserDAO userDAO = new NonRelational_UserDAO();
	NonRelational_GameDAO gameDAO = new NonRelational_GameDAO();
	NonRelational_CommandDAO commandDAO = new NonRelational_CommandDAO();
	int delta;

	public NonRelational_Provider(int delta)
	{
		this.delta = delta;
	}

	@Override
	public void startTransaction()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endTransaction(boolean commit)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveGames(List<Game> games)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Game> loadGames()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateGame(Game game)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addGame(Game game)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveUsers(List<User> users)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> loadUsers()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addUser(User user)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveCommands(List<String> commands)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> loadCommands()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCommand(String command, int gameID)
	{
		// TODO Auto-generated method stub
		
	}

	

}
