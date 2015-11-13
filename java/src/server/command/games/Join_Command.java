package server.command.games;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.games.JoinGame_Input;

public class Join_Command extends ServerCommand {

	private JoinGame_Input params = null;
	
	/**
	 * Command object for joining a game
	 * @param json
	 */
	public Join_Command(HttpExchange exchange)
	{
		super(exchange);
		//here we will deserialize the JSON into a JoinGame_Input object
	}

	@Override
	public JsonElement execute() throws ServerInvalidRequestException
	{
		try 
		{
			params = gson.fromJson(json, JoinGame_Input.class);
			JsonElement response = ServerManager.instance().getGamesFacade().join(params, playerId);
			String encodedCookie = getEncodedJoinGameCookie(Integer.toString(params.getId()));
			httpObj.getResponseHeaders().add("Set-cookie", encodedCookie);
			return response;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new ServerInvalidRequestException(e.getMessage());
		}
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		// TODO Auto-generated method stub
		return null;
	}

}
