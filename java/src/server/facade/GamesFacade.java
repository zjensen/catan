package server.facade;

import com.google.gson.JsonElement;

import shared.communication.games.CreateGame_Input;
import shared.communication.games.JoinGame_Input;

//make all methods static
public class GamesFacade implements IGamesFacade {

	/**
	 * Handles a create game request
	 * @param params
	 * @return A new game created with the info specified in the parameters, otherwise an error message in JSON
	 */
	@Override
	public JsonElement create(CreateGame_Input params) {
		return null;
	}
	
	/**
	 * Handles the request to join a game
	 * @param params
	 * @return "SUCCESS" if successful otherwise an error message in JSON
	 */
	@Override
	public JsonElement join(JoinGame_Input params) {
		return null;
	}
	
	/**
	 * Handles a request to see the current players of the game
	 * @return JSON representation of the game. Game Title, ID and players participating inclusive
	 */
	@Override
	public JsonElement list() {
		return null;
	}
}
