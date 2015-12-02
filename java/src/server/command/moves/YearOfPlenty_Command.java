package server.command.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;

import server.command.ExchangeWrapper;
import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.moves.YearOfPlenty_Input;
import shared.definitions.ResourceType;

public class YearOfPlenty_Command extends ServerCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8637392409489567448L;
	private YearOfPlenty_Input params = null;
	
	/**
	 * Command object for playing a year of plenty card
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public YearOfPlenty_Command(ExchangeWrapper exchange)
	{
		super(exchange);
	}

	@Override
	public JsonElement execute() throws ServerInvalidRequestException
	{
		return execute(super.json);
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		if(!hasUserCookie)
		{
			return new JsonPrimitive("The catan.user HTTP cookie is missing.  You must login before calling this method.");
		}
		if(!hasGameCookie)
		{
			return new JsonPrimitive("The catan.game HTTP cookie is missing.  You must join a game before calling this method.");
		}
		params = gson.fromJson(json, YearOfPlenty_Input.class);
		JsonObject paramsJSON = (JsonObject) new JsonParser().parse(json);
		params.setResource(ResourceType.valueOf(paramsJSON.get("resource1").getAsString().toUpperCase()));
		params.setResource1(ResourceType.valueOf(paramsJSON.get("resource2").getAsString().toUpperCase()));
		JsonElement result =  ServerManager.instance().getMovesFacade().yearOfPlenty(params, super.playerId, super.gameId);
		if(result == null)
		{
			result = new JsonPrimitive("Invalid Move");
		}
		else
		{
			super.addCommand();
		}
		return result;
	}

}
