package server.command.moves;

import com.google.gson.JsonElement;

import server.command.ICommand;
import server.facade.MovesFacade;
import shared.communication.moves.BuildRoad_Input;

public class BuildRoad_Command implements ICommand {

	private int playerID = -1;
	private int gameID = -1;
	private BuildRoad_Input params = null;
	
	/**
	 * Command object for building a road
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public BuildRoad_Command(String json, int playerID, int gameID)
	{
		this.playerID = playerID;
		this.gameID = playerID;
		//here we will deserialize the JSON into a BuildRoad_Input object
	}

	@Override
	public JsonElement execute()
	{
		return MovesFacade.buildRoad(params, playerID, gameID);
	}

}
