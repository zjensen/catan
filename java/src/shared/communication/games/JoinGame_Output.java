package shared.communication.games;

public class JoinGame_Output 
{
	private String response;
	
	public JoinGame_Output()
	{
		
	}
	
	public JoinGame_Output(String response)
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
