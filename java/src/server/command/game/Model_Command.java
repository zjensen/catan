package server.command.game;

import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.game.GameModel_Input;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

public class Model_Command extends ServerCommand {

	private GameModel_Input params = null;

	/**
	 * Command object for getting the client model
	 * 
	 * @param version
	 */
	public Model_Command(HttpExchange exchange) {
		super(exchange);
		// here we will deserialize the JSON into a GameModel_Input object
	}

	@Override
	public JsonElement execute() throws ServerInvalidRequestException {
		int version = identifyVersion();
		GameModel_Input params = new GameModel_Input(version);
		return ServerManager.instance().getGameFacade()
				.currentModel(params, gameId);
	}

	@Override
	public JsonElement execute(String json)
			throws ServerInvalidRequestException {
		// TODO Auto-generated method stub
		return null;
	}

	public int identifyVersion() {
		String url = httpObj.getRequestURI().toString();
		String[] split = url.split("/");
		String modelParam = split[split.length - 1];
		try {
			String[] modelSplit = modelParam.split("=");
			int version = Integer.parseInt(modelSplit[modelSplit.length - 1]);
			return version;
		} catch (NumberFormatException e) {
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
