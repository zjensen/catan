package server.persistence.provider;

import server.persistence.dao.*;

public class Relational_Provider extends IProvider {
	

	public Relational_Provider(int delta)
	{
		super.userDAO = new Relational_UserDAO();
		super.gameDAO = new Relational_GameDAO();
		super.commandDAO = new Relational_CommandDAO();
		super.delta = delta;
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
}
