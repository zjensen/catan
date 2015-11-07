package server.command.moves;

import com.google.gson.JsonElement;

import server.command.ICommand;
import server.manager.ServerManager;
import shared.communication.moves.RobPlayer_Input;

public class RobPlayer_Command implements ICommand {

	private int playerID = -1;
	private int gameID = -1;
	private RobPlayer_Input params = null;
	
	/**
	 * Command object for robbing a player
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public RobPlayer_Command(String json, int playerID, int gameID)
	{
		this.playerID = playerID;
		this.gameID = playerID;
		//here we will deserialize the JSON into a RobPlayer_Input object
	}

	@Override
	public JsonElement execute()
	{
		return ServerManager.instance().getMovesFacade().robPlayer(params, playerID, gameID);
	}

}
