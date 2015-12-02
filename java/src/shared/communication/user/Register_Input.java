package shared.communication.user;

import java.io.Serializable;

import com.google.gson.Gson;

public class Register_Input implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5493553057804752531L;
	private String username;
	private String password;
	
	public Register_Input(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String toJSON()
	{
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
