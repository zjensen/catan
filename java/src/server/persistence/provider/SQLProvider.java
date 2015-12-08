package server.persistence.provider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import server.persistence.dao.*;

public class SQLProvider extends IProvider {
	
	private Connection connection;

	public SQLProvider()
	{
		super.userDAO = new SQLUserDAO(this);
		super.gameDAO = new SQLGameDAO(this);
		super.commandDAO = new SQLCommandDAO(this);
		//super.delta = delta;
		this.connection = null;
	}

	@Override
	public void startTransaction()
	{
		String dbName = /*"Database" + File.separator + */"catandb.sqlite";
		try {
			Class.forName("org.sqlite.JDBC"); 
			connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
			connection.setAutoCommit(false);
						
		}
		catch (SQLException e) {
			System.out.println("Failed to connect to the SQL database");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Exception caused by \"Class.forName(org.sqlite.JDBC)\"");
			e.printStackTrace();
		}
	}

	@Override
	public void endTransaction(boolean commit)
	{
		try {
			if(commit) {
				connection.commit();
			}
			else {
				connection.rollback();
			}
		}
		catch (SQLException e) {
			System.out.println("Failed to commit or follow through with the transaction SQLProvider.endTransaction()");
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void setDelta(int delta_in){
		this.delta = delta_in;
	}
	
	@Override
	public void clean()
	{
		this.startTransaction();
		((SQLUserDAO) userDAO).resetUserTable();
		((SQLGameDAO) gameDAO).resetGameTable();
		((SQLCommandDAO) commandDAO).resetCommandTable();
		this.endTransaction(true);
	}
}
