package server.facade;

import server.main.ServerInvalidRequestException;
import shared.communication.game.GameModel_Input;

import com.google.gson.JsonElement;

public interface IGameFacade {

	public JsonElement currentModel(GameModel_Input params, Integer gameId)
			throws ServerInvalidRequestException;

}
