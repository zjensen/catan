package shared.communication.moves;

public class SendChat_Output 
{
	private String response;
	
	public SendChat_Output(String response)
	{
		this.setResponse(response);
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
}
