package shared.communication.moves;

public class RobPlayer_Output 
{
	private String response;
	
	public RobPlayer_Output()
	{
		
	}
	
	public RobPlayer_Output(String response)
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
