package server.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import server.persistence.provider.IProvider;
import server.persistence.provider.SQLProvider;

public class SQLGameDAO implements IGameDAO {
	
	private IProvider provider;

	public SQLGameDAO(IProvider provider) {
		this.provider = provider;
	}

	@Override
	public List<String> loadGames()
	{
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<>();
		
		try {
			connection = ((SQLProvider) provider).getConnection();
			String getSQL = "select * from game";
			pstmt = connection.prepareStatement(getSQL);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String gameString = rs.getString(2);				
				list.add(gameString);
			}
			
			pstmt.close();
		}
		catch (SQLException e){
			System.out.println("ERROR\n\tWas unable to get all games SQLUserDAO.loadGames()");
			e.printStackTrace();
			return null;
		}
				
		return list;
	}

	@Override
	public void updateGame(String game, int gameID)
	{
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = ((SQLProvider) provider).getConnection();
			
			String updateSQL = "update game set gameJSON = ? "
					+ "where gameId = ?";
			pstmt = connection.prepareStatement(updateSQL);
			pstmt.setString(1, game);
			pstmt.setInt(2, gameID+1); //server starts counting at 0, database starts at 1
			
			pstmt.executeUpdate();
			pstmt.close();		
		}
		catch (SQLException e){
			System.out.println("ERROR\n\tWas unable update game SQLGameDAO.updateGame()");
			e.printStackTrace();
		}
	}

	@Override
	public void addGame(String game, int gameID)
	{
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = ((SQLProvider) provider).getConnection();
			String insertSQL = "insert into game (gameJSON) "
					+ "values (?)";
			pstmt = connection.prepareStatement(insertSQL);
			pstmt.setString(1,  game);
			
			pstmt.executeUpdate();
			pstmt.close();
		}
		catch (SQLException e) {
			System.out.println("ERROR\n\tWas unable to add a user SQLGameDAO.addGame()");
			e.printStackTrace();
		}
	}
	
	public void resetGameTable() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = ((SQLProvider) provider).getConnection();
			String insertSQL = "drop table if exists game;"
					+ "create table game ("
					+ " gameId integer primary key autoincrement not null,"
					+ "	gameJSON varchar(2000) not null"
					+ ");";
			pstmt = connection.prepareStatement(insertSQL);
			
			pstmt.executeUpdate();
			pstmt.close();
		}
		catch (SQLException e) {
			System.out.println("ERROR\n\tWas unable to reset the game table SQLGameDAO.resetGameTable()");
			e.printStackTrace();
		}
	}
}
