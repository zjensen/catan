package shared.communication.moves;

public class BuildSettlement_Output 
{
	private String response;
	
	public BuildSettlement_Output()
	{
		
	}
	
	public BuildSettlement_Output(String response)
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
