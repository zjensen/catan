package server.persistence.provider;

import java.util.List;

import server.persistence.dao.*;
import shared.models.Game;
import shared.models.User;

public class Relational_Provider implements IProvider {
	
	Relational_UserDAO userDAO = new Relational_UserDAO();
	Relational_GameDAO gameDAO = new Relational_GameDAO();
	Relational_CommandDAO commandDAO = new Relational_CommandDAO();
	int delta;

	public Relational_Provider(int delta)
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
