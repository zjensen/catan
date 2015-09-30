package shared.communication.moves;

public class Monopoly_Output 
{
	private String response;
	
	public Monopoly_Output()
	{
		
	}
	
	public Monopoly_Output(String response)
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
