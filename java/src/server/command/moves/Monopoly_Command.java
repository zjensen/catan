package server.command.moves;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.moves.Monopoly_Input;

public class Monopoly_Command extends ServerCommand {
	
	private Monopoly_Input params;

	public Monopoly_Command(HttpExchange exchange) {
		super(exchange);
		// TODO Auto-generated constructor stub
	}

	@Override
	public JsonElement execute() throws ServerInvalidRequestException 
	{
		return execute(super.json);
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		// TODO get params from json
		params = gson.fromJson(json, Monopoly_Input.class);
		
		return ServerManager.instance().getMovesFacade().monopoly(params, super.playerId, super.gameId);
	}

}
