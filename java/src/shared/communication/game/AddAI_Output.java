package shared.communication.game;

public class AddAI_Output 
{
	private String response;
	
	public AddAI_Output()
	{
		
	}
	
	public AddAI_Output(String response)
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
