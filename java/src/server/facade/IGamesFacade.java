package server.facade;

import com.google.gson.JsonElement;

import server.main.ServerInvalidRequestException;
import shared.communication.games.CreateGame_Input;
import shared.communication.games.JoinGame_Input;

public interface IGamesFacade {

	public JsonElement create(CreateGame_Input params);
	
	public JsonElement join(JoinGame_Input params, int playerId) throws ServerInvalidRequestException;

	public JsonElement list();
}
