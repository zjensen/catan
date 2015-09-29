package shared.communication.moves;

public class BuyDevCard_Output 
{
	private String response;
	
	public BuyDevCard_Output()
	{
		
	}
	
	public BuyDevCard_Output(String response)
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
