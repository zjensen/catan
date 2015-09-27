package shared.communication.games;

public class CreateGame_Output 
{
	private String response;
	
	public CreateGame_Output(String response)
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
