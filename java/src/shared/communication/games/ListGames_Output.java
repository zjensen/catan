package shared.communication.games;

import client.data.GameInfo;

public class ListGames_Output 
{
	private String response;
	private GameInfo[] games;
	
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

	public GameInfo[] getGames()
	{
		return games;
	}

	public void setGames(GameInfo[] games)
	{
		this.games = games;
	}
}
