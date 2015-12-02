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
import shared.communication.moves.Monopoly_Input;
import shared.definitions.ResourceType;

public class Monopoly_Command extends ServerCommand {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1710481300604140610L;
	private Monopoly_Input params;

	public Monopoly_Command(ExchangeWrapper exchange) {
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
		else if(!hasGameCookie)
		{
			return new JsonPrimitive("The catan.game HTTP cookie is missing.  You must join a game before calling this method.");
		}
		params = gson.fromJson(json, Monopoly_Input.class);
		JsonObject paramsJSON = (JsonObject) new JsonParser().parse(json);
		params.setResource(ResourceType.valueOf(paramsJSON.get("resource").getAsString().toUpperCase()));
		JsonElement result = ServerManager.instance().getMovesFacade().monopoly(params, super.playerId, super.gameId);
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
