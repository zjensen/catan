package shared.communication.user;

public class Login_Output {
	
	private String response;
	
	public Login_Output(String response)
	{
		this.response = response;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
}
