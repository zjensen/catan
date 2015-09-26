package shared.communication.user;

import com.google.gson.Gson;

import sun.security.util.Password;

public class Login_Input {
	
	private String username;
	private String password;
	
	public Login_Input(String username, String password) {
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
