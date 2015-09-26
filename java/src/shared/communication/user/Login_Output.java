package shared.communication.user;

public class Login_Output {
	
	private String response;
	
	public Login_Output(int loginStatus) {
		if(loginStatus == 0) this.response = "Success";
		else if(loginStatus == 1) this.response = "Invalid";
		else this.response = "Failed";		
	}

	public String getReponse() {
		return response;
	}

	public void setReponse(String response) {
		this.response = response;
	}
}
