package shared.communication.moves;

public class BuildRoad_Output 
{
	private String response;
	
	public BuildRoad_Output()
	{
		
	}
	
	public BuildRoad_Output(String response)
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
