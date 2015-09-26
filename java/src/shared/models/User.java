package shared.models;

public class User {
	private String name;
	private String password;
	private int playerID;
	
	public User(String name, String password, int playerID) 
	{
		super();
		this.name = name;
		this.password = password;
		this.playerID = playerID;
	}
	
	public User(String name, String password) 
	{
		super();
		this.name = name;
		this.password = password;
		this.playerID = -1;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the playerID
	 */
	public int getPlayerID() {
		return playerID;
	}

	/**
	 * @param playerID the playerID to set
	 */
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

}
