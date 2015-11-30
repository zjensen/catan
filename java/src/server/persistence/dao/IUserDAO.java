package server.persistence.dao;

import java.util.List;

import shared.models.User;

public interface IUserDAO {

	/**
	 * save a list of users
	 * @param users
	 */
	public void saveUsers(List<User> users);
	
	/**
	 * loads all saved users
	 * @return list of all saved users
	 */
	public List<User> loadUsers();
	
	/**
	 * saves a new user
	 * @param user to be saved
	 */
	public void addUser(User user);

}
