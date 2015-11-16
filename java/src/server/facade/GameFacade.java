package server.facade;

import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.game.GameModel_Input;
import shared.models.ClientModel;
import shared.models.Game;
import shared.utils.Interpreter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

//make all methods static
public class GameFacade implements IGameFacade {

	Interpreter myInterpreter = new Interpreter();

	/**
	 * Grabs the current state of the game
	 * 
	 * @param params
	 * @return Returns a JSON representation of the current state of the game,
	 *         otherwise if the game is up to date returns "true"
	 * @throws ServerInvalidRequestException
	 */
	@Override
	public JsonElement currentModel(GameModel_Input params, Integer gameId)
			throws ServerInvalidRequestException {
		if (gameId == null) {
			throw new ServerInvalidRequestException("missing gameId field");
		}

		Game game = ServerManager.instance().getGamesManager()
				.getGameById(gameId);

		if (game == null) {
			throw new ServerInvalidRequestException("Invalid game ID");
		}

		ClientModel model = game.getClientModel();

		int currentVersion = model.getVersion();

		if (currentVersion != params.getVersion()) {
			return myInterpreter.serialize(model);
		} else {
			return new JsonPrimitive("true");
		}
	}
}
