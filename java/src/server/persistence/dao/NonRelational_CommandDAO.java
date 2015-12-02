package server.persistence.dao;

import java.util.List;

import server.command.ServerCommand;

public class NonRelational_CommandDAO implements ICommandDAO {

	public NonRelational_CommandDAO()
	{
		// TODO Auto-generated constructor stub
	}

//	@Override
//	public void saveCommands(List<ServerCommand> commands)
//	{
//		// TODO Auto-generated method stub
//
//	}

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
