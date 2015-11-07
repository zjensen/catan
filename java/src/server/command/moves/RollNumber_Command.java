package server.command.moves;

import com.google.gson.JsonElement;

import server.command.ICommand;
import server.manager.ServerManager;
import shared.communication.moves.RollNumber_Input;

public class RollNumber_Command implements ICommand {

	private int playerID = -1;
	private int gameID = -1;
	private RollNumber_Input params = null;
	
	/**
	 * Command object for rolling the dice
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public RollNumber_Command(String json, int playerID, int gameID)
	{
		this.playerID = playerID;
		this.gameID = playerID;
		//here we will deserialize the JSON into a RollNumber_Input object
	}

	@Override
	public JsonElement execute()
	{
		return ServerManager.instance().getMovesFacade().rollNumber(params, playerID, gameID);
	}

}
