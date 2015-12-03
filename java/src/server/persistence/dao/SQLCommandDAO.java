package server.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import server.persistence.provider.IProvider;
import server.persistence.provider.SQLProvider;

public class SQLCommandDAO implements ICommandDAO {

	private IProvider provider;
	
	public SQLCommandDAO(IProvider provider) {
		this.provider = provider;
	}

	@Override
	public List<String> loadCommands()
	{
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<>();
		
		try {
			connection = ((SQLProvider) provider).getConnection();
			String getSQL = "select * form command";
			pstmt = connection.prepareStatement(getSQL);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String commandString = rs.getString(2);				
				list.add(commandString);
			}
			
			pstmt.close();
		}
		catch (SQLException e){
			System.out.println("ERROR\n\tWas unable to get all commands SQLCommandDAO.loadCommands()");
			e.printStackTrace();
			return null;
		}
				
		return list;
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
