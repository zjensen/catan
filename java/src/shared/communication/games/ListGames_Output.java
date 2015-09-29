package shared.communication.games;

public class ListGames_Output 
{
	private String response;
	
	public ListGames_Output(String response)
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
