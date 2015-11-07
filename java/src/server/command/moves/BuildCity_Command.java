package server.command.moves;

import com.google.gson.JsonElement;

import server.command.ICommand;
import server.manager.ServerManager;
import shared.communication.moves.BuildCity_Input;

public class BuildCity_Command implements ICommand {

	private int playerID = -1;
	private int gameID = -1;
	private BuildCity_Input params = null;
	
	/**
	 * Command object for building a city
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public BuildCity_Command(String json, int playerID, int gameID)
	{
		this.playerID = playerID;
		this.gameID = playerID;
		//here we will deserialize the JSON into a BuildCity_Input object
	}

	@Override
	public JsonElement execute()
	{
		return ServerManager.instance().getMovesFacade().buildCity(params, playerID, gameID);
	}

}
