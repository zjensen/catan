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
			String getSQL = "select * from command order by gameId";
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
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = ((SQLProvider) provider).getConnection();
			String insertSQL = "insert into command (gameId, command) "
					+ "values (?, ?)";
			pstmt = connection.prepareStatement(insertSQL);
			pstmt.setInt(1, gameID+1); //server starts counting at 0, database starts at 1
			pstmt.setString(2,  command);
			
			pstmt.executeUpdate();
			pstmt.close();
		}
		catch (SQLException e) {
			System.out.println("ERROR\n\tWas unable to add a user SQLCommandDAO.addCommand()");
			e.printStackTrace();
		}

	}

	@Override
	public void purge(int gameID)
	{
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = ((SQLProvider) provider).getConnection();
			String insertSQL = "delete from command "
					+ "where gameId = ?";
			pstmt = connection.prepareStatement(insertSQL);
			pstmt.setInt(1, gameID+1); //server starts counting at 0, database starts at 1
			
			pstmt.executeUpdate();
			pstmt.close();
		}
		catch (SQLException e) {
			System.out.println("ERROR\n\tWas unable to add a user SQLCommandDAO.purge()");
			e.printStackTrace();
		}
	}

	@Override
	public int getNumberOfSavedCommands(int gameID)
	{
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			connection = ((SQLProvider) provider).getConnection();
			String getSQL = "select count(*) from command where gameid = ?";
			pstmt = connection.prepareStatement(getSQL);
			pstmt.setInt(1, gameID+1); //server starts counting at 0, database starts at 1
			
			rs = pstmt.executeQuery();
			
			count = rs.getInt(1); //should only have one number as the result
			
			pstmt.close();
		}
		catch (SQLException e){
			System.out.println("ERROR\n\tWas unable to get all commands SQLCommandDAO.getNumberOfSavedCommands()");
			e.printStackTrace();
			return 0; //should this be an empty or -1 for the error?
		}
		return count;
	}

	public void resetCommandTable() {
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = ((SQLProvider) provider).getConnection();
			String insertSQL = "drop table if exists command;"
					+ "create table command ("
					+ "gameId int not null,"
					+ "command varchar(2000) not null"
					+ ");";
			pstmt = connection.prepareStatement(insertSQL);
			
			pstmt.executeUpdate();
			pstmt.close();
		}
		catch (SQLException e) {
			System.out.println("ERROR\n\tWas unable to reset command table SQLCommandDAO.resetCommandTable()");
			e.printStackTrace();
		}

	}
}
