package server.command.moves;

import com.google.gson.JsonElement;

import server.command.ICommand;
import server.manager.ServerManager;
import shared.communication.moves.Monument_Input;

public class Monument_Command implements ICommand {

	private int playerID = -1;
	private int gameID = -1;
	private Monument_Input params = null;
	
	/**
	 * Command object for placing a monument
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public Monument_Command(String json, int playerID, int gameID)
	{
		this.playerID = playerID;
		this.gameID = playerID;
		//here we will deserialize the JSON into a Monument_Input object
	}

	@Override
	public JsonElement execute()
	{
		return ServerManager.instance().getMovesFacade().monument(params, playerID, gameID);
	}

}
