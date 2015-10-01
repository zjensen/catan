package shared.communication.moves;

public class Soldier_Output 
{
	private String response;
	
	public Soldier_Output()
	{
		
	}
	
	public Soldier_Output(String response)
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
