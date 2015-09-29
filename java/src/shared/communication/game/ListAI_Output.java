package shared.communication.game;

public class ListAI_Output 
{
	private String response;
	
	public ListAI_Output()
	{
		
	}
	
	public ListAI_Output(String response)
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
