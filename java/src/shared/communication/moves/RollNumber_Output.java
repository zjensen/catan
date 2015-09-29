package shared.communication.moves;

public class RollNumber_Output 
{
	private String response;
	
	public RollNumber_Output()
	{
		
	}
	
	public RollNumber_Output(String response)
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
