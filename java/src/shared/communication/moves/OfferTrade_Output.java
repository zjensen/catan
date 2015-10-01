package shared.communication.moves;

public class OfferTrade_Output 
{
	private String response;
	
	public OfferTrade_Output()
	{
		
	}
	
	public OfferTrade_Output(String response)
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
