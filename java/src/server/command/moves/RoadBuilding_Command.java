package server.command.moves;

import com.google.gson.JsonElement;

import server.command.ICommand;
import server.facade.MovesFacade;
import shared.communication.moves.RoadBuilding_Input;

public class RoadBuilding_Command implements ICommand {

	private int playerID = -1;
	private int gameID = -1;
	private RoadBuilding_Input params = null;
	
	/**
	 * Command object for road building card
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public RoadBuilding_Command(String json, int playerID, int gameID)
	{
		this.playerID = playerID;
		this.gameID = playerID;
		//here we will deserialize the JSON into a RoadBuilding_Input object
	}

	@Override
	public JsonElement execute()
	{
		return MovesFacade.roadBuilding(params, playerID, gameID);
	}

}
