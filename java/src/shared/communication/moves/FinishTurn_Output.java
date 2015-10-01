package shared.communication.moves;

public class FinishTurn_Output 
{
	private String response;
	
	public FinishTurn_Output()
	{
		
	}
	
	public FinishTurn_Output(String response)
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
