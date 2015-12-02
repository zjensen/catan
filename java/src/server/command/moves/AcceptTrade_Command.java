package server.command.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import server.command.ExchangeWrapper;
import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.moves.AcceptTrade_Input;

public class AcceptTrade_Command extends ServerCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 226039177329646576L;
	private AcceptTrade_Input params = null;
	
	/**
	 * Command object for accepting a trade
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public AcceptTrade_Command(ExchangeWrapper exchange)
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
		params = gson.fromJson(json, AcceptTrade_Input.class);
		JsonElement result = ServerManager.instance().getMovesFacade().acceptTrade(params, super.playerId, super.gameId);
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
