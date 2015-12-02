package server.persistence.provider;

import server.persistence.dao.*;

public class NonRelational_Provider extends IProvider {
	
	
	public NonRelational_Provider(int delta)
	{
		super.userDAO = new NonRelational_UserDAO();
		super.gameDAO = new NonRelational_GameDAO();
		super.commandDAO = new NonRelational_CommandDAO();
		super.delta = delta;
	}

	@Override
	public void startTransaction()
	{
		return;
	}

	@Override
	public void endTransaction(boolean commit)
	{
		return;
	}
	
}
