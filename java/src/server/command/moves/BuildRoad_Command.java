package server.command.moves;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.moves.BuildRoad_Input;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

public class BuildRoad_Command extends ServerCommand {

	private BuildRoad_Input params = null;
	
	/**
	 * Command object for building a road
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public BuildRoad_Command(HttpExchange exchange)
	{
		super(exchange);
		//here we will deserialize the JSON into a BuildRoad_Input object
	}

	@Override
	public JsonElement execute() throws ServerInvalidRequestException
	{
		return execute(super.json);
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		params = gson.fromJson(json, BuildRoad_Input.class);
		EdgeLocation edgeLocParam = params.getRoadLocation();
		HexLocation hexLoc = edgeLocParam.getHexLoc();
		EdgeDirection edgeDirection = edgeLocParam.getDir();
		EdgeLocation edgeLocation = new EdgeLocation(hexLoc, edgeDirection);
//		System.out.println("build road command, build road player index?? " + params.getPlayerIndex());
		
//		ServerManager.instance().getMovesFacade().addCommand(json, gameId);
		return ServerManager.instance().getMovesFacade().buildRoad(params, super.playerId, super.gameId);
	}

}
