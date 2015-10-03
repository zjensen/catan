package shared.communication.moves;

public class RoadBuilding_Output 
{
	private String response;
	
	public RoadBuilding_Output()
	{
		
	}
	
	public RoadBuilding_Output(String response)
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
