package shared.communication.game;

public class GETCommands_Output 
{
	private String response;
	
	public GETCommands_Output()
	{
		
	}
	
	public GETCommands_Output(String response)
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
