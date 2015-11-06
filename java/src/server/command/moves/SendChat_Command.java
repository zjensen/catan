package server.command.moves;

import com.google.gson.JsonElement;

import server.command.ICommand;
import server.facade.MovesFacade;
import shared.communication.moves.SendChat_Input;

public class SendChat_Command implements ICommand{
	
	private int playerID = -1;
	private int gameID = -1;
	private SendChat_Input params = null;

	/**
	 * Command object for sending a chat
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public SendChat_Command(String json, int playerID, int gameID)
	{
		this.playerID = playerID;
		this.gameID = playerID;
		//here we will deserialize the JSON into a SendChat_Input object
	}

	@Override
	public JsonElement execute()
	{
		return MovesFacade.sendChat(params, playerID, gameID);
	}

}
