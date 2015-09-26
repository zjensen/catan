package shared.communication.user;

public class Register_Output {

	private String response;
	
	public Register_Output(int registerStatus) {
		if(registerStatus == 0) this.response = "Success";
		else if(registerStatus == 1) this.response = "Invalid";
		else this.response = "Failed";		
	}

	public String getResponse() {
		return response;
	}

	public void setReponse(String response) {
		this.response = response;
	}
}
