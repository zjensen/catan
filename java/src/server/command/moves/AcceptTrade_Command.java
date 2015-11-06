package server.command.moves;

import com.google.gson.JsonElement;

import server.command.ICommand;
import server.facade.MovesFacade;
import shared.communication.moves.AcceptTrade_Input;

public class AcceptTrade_Command implements ICommand {

	private int playerID = -1;
	private int gameID = -1;
	private AcceptTrade_Input params = null;
	
	/**
	 * Command object for accepting a trade
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public AcceptTrade_Command(String json, int playerID, int gameID)
	{
		this.playerID = playerID;
		this.gameID = playerID;
		//here we will deserialize the JSON into a AcceptTrade_Input object
	}

	@Override
	public JsonElement execute()
	{
		return MovesFacade.acceptTrade(params, playerID, gameID);
	}

}
