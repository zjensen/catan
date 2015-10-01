package shared.communication.moves;

public class MaritimeTrade_Output 
{
	private String response;
	
	public MaritimeTrade_Output()
	{
		
	}
	
	public MaritimeTrade_Output(String response)
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
