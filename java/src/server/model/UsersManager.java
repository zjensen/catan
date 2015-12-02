package server.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shared.models.User;

/**
 * This manager class stores all the users registered on the current server This
 * class provides the server access to these users This will only be accessed by
 * the ServerManager singleton class
 * 
 * @author Ian
 *
 */
public class UsersManager {

	/**
	 * The list of users registered on the server
	 */
	private List<User> users = new ArrayList<User>();

	/**
	 * Constructor: takes no parameters; adds default players to users list
	 */
	public UsersManager() {
		// Set default players :)
		User sam = new User("Sam", "sam", 1);
		User pete = new User("Pete", "pete", 2);
		User brooke = new User("Brooke", "brooke", 3);
		User mark = new User("Mark", "mark", 4);

		addUser(sam);
		addUser(pete);
		addUser(brooke);
		addUser(mark);
	}

	/**
	 * Copy constructor
	 * 
	 * @param users
	 *            : new Users list
	 */
	public UsersManager(List<User> users) {
		this.users = new ArrayList<User>(users);
	}

	/**
	 * adds a new user to the server's current list of registered users
	 * 
	 * @param newUser
	 */
	public void addUser(User newUser) {
		this.users.add(newUser);
	}

	/**
	 * Adds a new user to the UsersManager uses java 8 stream to find
	 * highestplayerID, which is really cool
	 * 
	 * @param username
	 * @param password
	 */
	public void addNewUser(String username, String password) {
		// Checks to give a unique ID
		int highestID = users.stream().mapToInt(user -> user.getPlayerID())
				.reduce(-1, Integer::max);

		// for (User u: users){
		// if (u.getPlayerID() > highestID){
		// highestID = u.getPlayerID();
		// }
		// }
		int newID = highestID + 1;
		User newUser = new User(username, password, newID);

		addUser(newUser);
		return;
	}
	
	/**
	 * adds user and returns their ID
	 * @param username
	 * @param password
	 * @return id of user just added
	 */
	public int addNewUserGetID(String username, String password) {
		// Checks to give a unique ID
		int highestID = users.stream().mapToInt(user -> user.getPlayerID())
				.reduce(-1, Integer::max);
		int newID = highestID + 1;
		User newUser = new User(username, password, newID);

		addUser(newUser);
		return newID;
	}

	/**
	 * returns a user by looking up their playerID
	 * 
	 * @param id
	 * @return
	 */
	public User getUserById(int id) {
		for (User user : users) {
			if (user.getPlayerID() == id) {
				return user;
			}
		}

		return null;
	}

	/**
	 * returns a User by looking up their userName
	 * 
	 * @param userName
	 * @return
	 */
	public User getUserByUserName(String userName) {
		for (User user : users) {
			if (user.getName().equals(userName)) {
				return user;
			}
		}

		return null;
	}

	/**
	 * Deletes a User by finding their ID
	 * 
	 * @param id
	 */
	public void deleteUser(int id) {
		for (User user : users) {
			if (user.getPlayerID() == id) {
				users.remove(user);
				return;
			}
		}
	}

	/**
	 * returns the current list of Users in an unmodifiable List
	 * 
	 * @return
	 */
	public List<User> getUsers() {
		return Collections.unmodifiableList(users);
	}
	
	/**
	 * sets the list of users
	 *
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}

	/**
	 * determines if a User is already registered with a given username
	 * 
	 * @param username
	 * @return true if a User with that username already exists, false otherwise
	 */
	public boolean userExists(String username) {
		for (User u : users) {
			if (u.getName().equals(username)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * determines if a User is already registered with a given id
	 * 
	 * @param username
	 * @return true if a User with that id already exists, false otherwise
	 */
	public boolean userExists(int userId) {
		for (User u : users) {
			if (u.getPlayerID() == userId) {
				return true;
			}
		}
		return false;
	}
	
	public boolean validateUser(String username, String password) {
		for (User u : users) {
			if (u.getName().equals(username)) {
				if (u.getPassword().equals(password)) return true;
				else return false;
			}
		}
		return false;
	}

	public int getNewestUserID()
	{
		// TODO Auto-generated method stub
		return users.get(users.size()-1).getPlayerID();
	}
}
