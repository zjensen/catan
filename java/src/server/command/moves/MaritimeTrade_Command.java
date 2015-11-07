package server.command.moves;

import com.google.gson.JsonElement;

import server.command.ICommand;
import server.manager.ServerManager;
import shared.communication.moves.MaritimeTrade_Input;

public class MaritimeTrade_Command implements ICommand {

	private int playerID = -1;
	private int gameID = -1;
	private MaritimeTrade_Input params = null;
	
	/**
	 * Command object for maritime trade
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public MaritimeTrade_Command(String json, int playerID, int gameID)
	{
		this.playerID = playerID;
		this.gameID = playerID;
		//here we will deserialize the JSON into a MaritimeTrade_Input object
	}

	@Override
	public JsonElement execute()
	{
		return ServerManager.instance().getMovesFacade().maritimeTrade(params, playerID, gameID);
	}

}
