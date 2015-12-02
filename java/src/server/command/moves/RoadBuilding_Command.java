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
import shared.communication.moves.RoadBuilding_Input;

public class RoadBuilding_Command extends ServerCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5954172457834244098L;
	private RoadBuilding_Input params = null;
	
	/**
	 * Command object for road building card
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public RoadBuilding_Command(ExchangeWrapper exchange)
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
		params = gson.fromJson(json, RoadBuilding_Input.class);
		JsonObject paramsJSON = (JsonObject) new JsonParser().parse(json);
		JsonObject spot1 = (JsonObject) paramsJSON.get("spot1");
		JsonObject spot2 = (JsonObject) paramsJSON.get("spot2");
		params.setSpot1(extractEdgeLocation(spot1));
		params.setSpot2(extractEdgeLocation(spot2));
		JsonElement result = ServerManager.instance().getMovesFacade().roadBuilding(params, super.playerId, super.gameId);
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
