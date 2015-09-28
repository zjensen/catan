package shared.communication.game;

public class POSTCommands_Output 
{
	private String response;
	
	public POSTCommands_Output()
	{
		
	}
	
	public POSTCommands_Output(String response)
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
