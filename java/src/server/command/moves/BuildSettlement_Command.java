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
import shared.communication.moves.BuildSettlement_Input;

public class BuildSettlement_Command extends ServerCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6937157712678230596L;
	private BuildSettlement_Input params = null;
	
	public BuildSettlement_Command(ExchangeWrapper exchange)
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
		else if(!hasGameCookie)
		{
			return new JsonPrimitive("The catan.game HTTP cookie is missing.  You must join a game before calling this method.");
		}
		params = gson.fromJson(json, BuildSettlement_Input.class);
		
		JsonObject paramsJSON = (JsonObject) new JsonParser().parse(json);
		JsonObject location = (JsonObject) paramsJSON.get("vertexLocation");
		
		params.setVertexLocation(extractVertexLocation(location));
		
		JsonElement result = ServerManager.instance().getMovesFacade().buildSettlement(params, super.playerId, super.gameId);
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
