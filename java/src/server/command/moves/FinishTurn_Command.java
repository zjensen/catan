package server.command.moves;

import com.google.gson.JsonElement;

import server.command.ICommand;
import server.facade.MovesFacade;
import shared.communication.moves.FinishTurn_Input;

public class FinishTurn_Command implements ICommand {

	private int playerID = -1;
	private int gameID = -1;
	private FinishTurn_Input params = null;
	
	/**
	 * Command object for finishing a turn
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public FinishTurn_Command(String json, int playerID, int gameID)
	{
		this.playerID = playerID;
		this.gameID = playerID;
		//here we will deserialize the JSON into a FinishTurn_Input object
	}

	@Override
	public JsonElement execute()
	{
		return MovesFacade.finishTurn(params, playerID, gameID);
	}

}
