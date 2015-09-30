package shared.communication.moves;

public class Monument_Output 
{
	private String response;
	
	public Monument_Output()
	{
		
	}
	
	public Monument_Output(String response)
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
