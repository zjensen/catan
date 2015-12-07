package server.persistence.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import server.persistence.provider.IProvider;
import server.persistence.provider.SQLProvider;

public class SQLUserDAO implements IUserDAO {

	private IProvider provider;
	
	public SQLUserDAO(IProvider provider) {
		this.provider = provider;
	}

	@Override
	public List<String> loadUsers()
	{
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<>();
		
		try {
			connection = ((SQLProvider) provider).getConnection();
			String getSQL = "select * from player";
			pstmt = connection.prepareStatement(getSQL);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String userString = rs.getString(1);				
				list.add(userString);
			}
			
			pstmt.close();
		}
		catch (SQLException e){
			System.out.println("ERROR\n\tWas unable to get all users SQLUserDAO.loadUsers()");
			e.printStackTrace();
			return null;
		}
				
		return list;
	}

	@Override
	public void addUser(String user, int userID)
	{
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = ((SQLProvider) provider).getConnection();
			String insertSQL = "insert into player (playerJSON) "
					+ "values (?)";
			pstmt = connection.prepareStatement(insertSQL);
			pstmt.setString(1,  user);
			
			pstmt.executeUpdate();
			pstmt.close();
		}
		catch (SQLException e) {
			System.out.println("ERROR\n\tWas unable to add a user SQLUserDAO.addUser()");
			e.printStackTrace();
		}
	}
	
	public void resetUserTable() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = ((SQLProvider) provider).getConnection();
			String insertSQL = "drop table if exists player; "
					+ "create table player ("
					+ "playerJSON varchar(50) unique not null"
					+ ");";
			pstmt = connection.prepareStatement(insertSQL);
			
			pstmt.executeUpdate();
			pstmt.close();
		}
		catch (SQLException e) {
			System.out.println("ERROR\n\tWas unable to reset the table SQLUserDAO.resetUserTable()");
			e.printStackTrace();
		}
	}
}
