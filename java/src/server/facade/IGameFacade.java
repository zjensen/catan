package server.facade;

import com.google.gson.JsonElement;

import shared.communication.game.GameModel_Input;

public interface IGameFacade {
	
	public JsonElement currentModel(GameModel_Input params);

}
