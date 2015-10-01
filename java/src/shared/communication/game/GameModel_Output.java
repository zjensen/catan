package shared.communication.game;

public class GameModel_Output 
{
	private String response;
	
	public GameModel_Output()
	{
		
	}
	
	public GameModel_Output(String response)
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
