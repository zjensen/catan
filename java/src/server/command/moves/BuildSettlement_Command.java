package server.command.moves;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.moves.BuildSettlement_Input;

public class BuildSettlement_Command extends ServerCommand {

	private BuildSettlement_Input params = null;
	
	public BuildSettlement_Command(HttpExchange exchange)
	{
		super(exchange);
		//here we will deserialize the JSON into a BuildSettlement_Input object
	}

	@Override
	public JsonElement execute()
	{
		return ServerManager.instance().getMovesFacade().buildSettlement(params, super.playerId, super.gameId);
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		//TODO get params from json
		params = gson.fromJson(json, BuildSettlement_Input.class);
		
		return ServerManager.instance().getMovesFacade().buildSettlement(params, super.playerId, super.gameId);
	}

}
