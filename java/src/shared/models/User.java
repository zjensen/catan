package shared.models;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4389014523860776611L;
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
	
	public String serialize()
	{
		 String serializedObject = "";

		 // serialize the object
		 try {
		     ByteArrayOutputStream bo = new ByteArrayOutputStream();
		     ObjectOutputStream so = new ObjectOutputStream(bo);
		     so.writeObject(this);
		     so.flush();
		     serializedObject = bo.toString("ISO-8859-1");
		 } catch (Exception e) {
		     e.printStackTrace();
		 }
		 return serializedObject;
	}

}
