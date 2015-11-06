package server.command.moves;

import com.google.gson.JsonElement;

import server.command.ICommand;
import server.facade.MovesFacade;
import shared.communication.moves.DiscardCards_Input;

public class DiscardCards_Command implements ICommand {

	private int playerID = -1;
	private int gameID = -1;
	private DiscardCards_Input params = null;
	
	/**
	 * Command object for discarding cards
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public DiscardCards_Command(String json, int playerID, int gameID)
	{
		this.playerID = playerID;
		this.gameID = playerID;
		//here we will deserialize the JSON into a DiscardCards_Input object
	}

	@Override
	public JsonElement execute()
	{
		return MovesFacade.discardCards(params, playerID, gameID);
	}

}
