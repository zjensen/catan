package server.command.moves;

import com.google.gson.JsonElement;

import server.command.ICommand;
import server.manager.ServerManager;
import shared.communication.moves.Soldier_Input;

public class Soldier_Command implements ICommand {

	private int playerID = -1;
	private int gameID = -1;
	private Soldier_Input params = null;
	
	/**
	 * Command object for playing a knight card
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public Soldier_Command(String json, int playerID, int gameID)
	{
		this.playerID = playerID;
		this.gameID = playerID;
		//here we will deserialize the JSON into a Soldier_Input object
	}

	@Override
	public JsonElement execute()
	{
		return ServerManager.instance().getMovesFacade().soldier(params, playerID, gameID);
	}

}
