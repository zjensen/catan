package server.command.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import server.command.ExchangeWrapper;
import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.moves.DiscardCards_Input;

public class DiscardCards_Command extends ServerCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2801751798563819909L;
	private DiscardCards_Input params = null;
	
	/**
	 * Command object for discarding cards
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public DiscardCards_Command(ExchangeWrapper exchange)
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
		params = gson.fromJson(json, DiscardCards_Input.class);
		
		JsonElement result = ServerManager.instance().getMovesFacade().discardCards(params, super.playerId, super.gameId);
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
