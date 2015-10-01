package shared.communication.moves;

public class AcceptTrade_Output 
{
	private String response;
	
	public AcceptTrade_Output()
	{
		
	}
	
	public AcceptTrade_Output(String response)
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
