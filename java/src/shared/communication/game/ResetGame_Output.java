package shared.communication.game;

public class ResetGame_Output 
{
	private String response;
	
	public ResetGame_Output()
	{
		
	}
	
	public ResetGame_Output(String response)
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
