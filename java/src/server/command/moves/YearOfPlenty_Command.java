package server.command.moves;

import com.google.gson.JsonElement;

import server.command.ICommand;
import server.manager.ServerManager;
import shared.communication.moves.YearOfPlenty_Input;

public class YearOfPlenty_Command implements ICommand {

	private int playerID = -1;
	private int gameID = -1;
	private YearOfPlenty_Input params = null;
	
	/**
	 * Command object for playing a year of plenty card
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public YearOfPlenty_Command(String json, int playerID, int gameID)
	{
		this.playerID = playerID;
		this.gameID = playerID;
		//here we will deserialize the JSON into a YearOfPlenty_Input object
	}

	@Override
	public JsonElement execute()
	{
		return ServerManager.instance().getMovesFacade().yearOfPlenty(params, playerID, gameID);
	}

}
