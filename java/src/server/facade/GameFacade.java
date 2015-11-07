package server.facade;

import com.google.gson.JsonElement;

import shared.communication.game.GameModel_Input;

//make all methods static
public class GameFacade implements IGameFacade {

	/**
	 * Grabs the current state of the game
	 * @param params
	 * @return Returns a JSON representation of the current state of the game, otherwise if the game is up to date returns "true"
	 */
	@Override
	public JsonElement currentModel(GameModel_Input params) {
		return null;
	}
}
