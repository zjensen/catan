package shared.communication.moves;

public class BuildCity_Output 
{
	private String response;
	
	public BuildCity_Output()
	{
		
	}
	
	public BuildCity_Output(String response)
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
