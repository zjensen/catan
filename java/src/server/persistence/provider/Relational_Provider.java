package server.persistence.provider;

import java.util.List;

import server.command.ServerCommand;
import server.persistence.dao.*;
import shared.models.Game;
import shared.models.User;

public class Relational_Provider implements IProvider {
	
	Relational_UserDAO userDAO;
	Relational_GameDAO gameDAO;
	Relational_CommandDAO commandDAO;

	public Relational_Provider()
	{
		// TODO Auto-generated constructor stub
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
	public void saveCommands(List<ServerCommand> commands)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public List<ServerCommand> loadCommands()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCommand(ServerCommand command)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void handleCommand(ServerCommand command)
	{
		// TODO Auto-generated method stub

	}

}
