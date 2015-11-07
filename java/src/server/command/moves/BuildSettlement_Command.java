package server.command.moves;

import com.google.gson.JsonElement;

import server.command.ICommand;
import server.manager.ServerManager;
import shared.communication.moves.BuildSettlement_Input;

public class BuildSettlement_Command implements ICommand {

	private int playerID = -1;
	private int gameID = -1;
	private BuildSettlement_Input params = null;
	
	public BuildSettlement_Command(String json, int playerID, int gameID)
	{
		this.playerID = playerID;
		this.gameID = playerID;
		//here we will deserialize the JSON into a BuildSettlement_Input object
	}

	@Override
	public JsonElement execute()
	{
		return ServerManager.instance().getMovesFacade().buildSettlement(params, playerID, gameID);
	}

}
