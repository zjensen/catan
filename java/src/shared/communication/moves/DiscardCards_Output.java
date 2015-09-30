package shared.communication.moves;

public class DiscardCards_Output 
{
	private String response;
	
	public DiscardCards_Output()
	{
		
	}
	
	public DiscardCards_Output(String response)
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
