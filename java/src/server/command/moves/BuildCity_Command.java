package server.command.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import server.command.ExchangeWrapper;
import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.moves.BuildCity_Input;

public class BuildCity_Command extends ServerCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5979802917290508077L;
	private BuildCity_Input params = null;
	
	/**
	 * Command object for building a city
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public BuildCity_Command(ExchangeWrapper exchange)
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
		params = gson.fromJson(json, BuildCity_Input.class);
		
		JsonObject paramsJSON = (JsonObject) new JsonParser().parse(json);
		JsonObject location = (JsonObject) paramsJSON.get("vertexLocation");
		
		params.setVertexLocation(extractVertexLocation(location));
		
		JsonElement result = ServerManager.instance().getMovesFacade().buildCity(params, super.playerId, super.gameId);
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
