package server.command.games;

import com.google.gson.JsonElement;

import server.command.ICommand;
import server.manager.ServerManager;
import shared.communication.games.JoinGame_Input;

public class Join_Command implements ICommand {

	private JoinGame_Input params = null;
	
	/**
	 * Command object for joining a game
	 * @param json
	 */
	public Join_Command(String json)
	{
		//here we will deserialize the JSON into a JoinGame_Input object
	}

	@Override
	public JsonElement execute()
	{
		return ServerManager.instance().getGamesFacade().join(params);
	}

}
