package server.command.games;

import com.google.gson.JsonElement;

import server.command.ICommand;
import server.manager.ServerManager;
import shared.communication.games.CreateGame_Input;

public class Create_Command implements ICommand {

	private CreateGame_Input params = null;
	
	/**
	 * Command object for creating a game
	 * @param json
	 */
	public Create_Command(String json)
	{
		//here we will deserialize the JSON into a CreateGame_Input object
	}

	@Override
	public JsonElement execute()
	{
		return ServerManager.instance().getGamesFacade().create(params);
	}

}
