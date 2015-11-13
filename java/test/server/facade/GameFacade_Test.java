package server.facade;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.game.GameModel_Input;
import shared.models.ClientModel;
import shared.utils.Interpreter;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class GameFacade_Test {

	private Interpreter interpreter = new Interpreter();
	private IGameFacade gameFacade;
	private ClientModel testModel;
	private Object gameID;

	@Before
	public void init() {
		StringBuilder result = new StringBuilder("");
		File file = new File("MovesFacadeTestJSON.txt");

		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}
			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		String jsonString = result.toString();

		JsonElement jsonToParse = new JsonParser().parse(jsonString)
				.getAsJsonObject();

		testModel = interpreter.deserialize(jsonToParse);

		ServerManager.instance().setFacades();
		ServerManager.instance().reset();

		gameID = ServerManager.instance().getGamesManager()
				.addNewGameGetID(testModel, "TITLE");
		gameFacade = ServerManager.instance().getGameFacade();
	}

	@Test(expected = ServerInvalidRequestException.class)
	public void testGameIdIsNull() throws JsonParseException,
			ServerInvalidRequestException {

		GameModel_Input params = new GameModel_Input(-1);

		ClientModel newModel = interpreter.deserialize(gameFacade.currentModel(
				params, null));
	}

	@Test(expected = ServerInvalidRequestException.class)
	public void testGameNotFound() throws JsonParseException,
			ServerInvalidRequestException {

		GameModel_Input params = new GameModel_Input(0);

		ClientModel newModel = interpreter.deserialize(gameFacade.currentModel(
				params, -1));
	}

	@Test
	public void testGameFoundWithCurrentVersion() throws JsonParseException,
			ServerInvalidRequestException {

		GameModel_Input params = new GameModel_Input(0);

		assertEquals(new JsonPrimitive("true"),
				gameFacade.currentModel(params, 0));
	}

	@Test
	public void testGameFoundWithOldVersion() throws JsonParseException,
			ServerInvalidRequestException {

		GameModel_Input params = new GameModel_Input(-1);

		assertEquals(interpreter.serialize(testModel),
				(gameFacade.currentModel(params, 0)));
	}
}
