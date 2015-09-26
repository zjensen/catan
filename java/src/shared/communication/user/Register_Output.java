package shared.communication.user;

public class Register_Output {

	private String response;
	
	public Register_Output(String response) 
	{
		this.response = response;		
	}

	public String getResponse() {
		return response;
	}

	public void setReponse(String response) {
		this.response = response;
	}
}
