package server.facade;

import com.google.gson.JsonElement;

import shared.communication.game.GameModel_Input;

//make all methods static
public class GameFacade {

	/**
	 * Grabs the current state of the game
	 * @param params
	 * @return Returns a JSON representation of the current state of the game, otherwise if the game is up to date returns "true"
	 */
	public static JsonElement currentModel(GameModel_Input params) {
		return null;
	}
}
