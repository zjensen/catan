package server.command.moves;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.moves.FinishTurn_Input;

public class FinishTurn_Command extends ServerCommand {

	private FinishTurn_Input params = null;
	
	/**
	 * Command object for finishing a turn
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public FinishTurn_Command(HttpExchange exchange)
	{
		super(exchange);
		//here we will deserialize the JSON into a FinishTurn_Input object
	}

	@Override
	public JsonElement execute()
	{
		return ServerManager.instance().getMovesFacade().finishTurn(params, super.playerId, super.gameId);
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		// TODO Auto-generated method stub
		return null;
	}

}
