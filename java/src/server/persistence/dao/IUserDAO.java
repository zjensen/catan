package server.persistence.dao;

import java.util.List;

public interface IUserDAO {

	
	/**
	 * loads all saved users
	 * @return list of all saved users
	 */
	public List<String> loadUsers();
	
	/**
	 * saves a new user
	 * @param user to be saved
	 */
	public void addUser(String user, int userID);

}
