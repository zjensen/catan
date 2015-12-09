package server.command.games;

import server.command.ExchangeWrapper;
import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.games.JoinGame_Input;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class Join_Command extends ServerCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8540101442963586124L;
	private JoinGame_Input params = null;

	/**
	 * Command object for joining a game
	 * 
	 * @param json
	 */
	public Join_Command(ExchangeWrapper exchange) {
		super(exchange);
		// here we will deserialize the JSON into a JoinGame_Input object
	}

	@Override
	public JsonElement execute() throws ServerInvalidRequestException {
		if (httpObj == null){
			params = gson.fromJson(json, JoinGame_Input.class);
			return ServerManager.instance().getGamesFacade().join(params, playerId);
		}
		if(!hasUserCookie)
		{
			return new JsonPrimitive("The catan.user HTTP cookie is missing.  You must login before calling this method.");
		}
		try {
			params = gson.fromJson(json, JoinGame_Input.class);
			JsonElement response = ServerManager.instance().getGamesFacade()
					.join(params, playerId);
			String encodedCookie = getEncodedJoinGameCookie(Integer
					.toString(params.getId()));
			httpObj.getExchange().getResponseHeaders().add("Set-cookie", encodedCookie);
			super.gameId=params.getId();
			super.addCommand();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerInvalidRequestException(e.getMessage());
		}
	}

	@Override
	public JsonElement execute(String json)
			throws ServerInvalidRequestException {
		// TODO Auto-generated method stub
		return null;
	}

}
