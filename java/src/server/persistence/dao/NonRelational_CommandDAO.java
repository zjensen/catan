package server.persistence.dao;

import java.util.List;

public class NonRelational_CommandDAO implements ICommandDAO {

	public NonRelational_CommandDAO()
	{
		// TODO Auto-generated constructor stub
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

	@Override
	public void purge(int gameID)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNumberOfSavedCommands(int gameID)
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
