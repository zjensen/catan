package server.facade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.games.CreateGame_Input;
import shared.communication.games.JoinGame_Input;
import shared.communication.user.Register_Input;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class GamesFacade_Test {

	private IGamesFacade gamesFacade;

	private final String testGameJSON = "{\"id\": 0,\"title\": \"Test_Game\",\"players\": [{},{},{},{}]}";
	private final String listGamesJSON = "[{\"id\": 0,\"title\": \"Test_Game\",\"players\": [{\"id\": 1,\"playerIndex\": 0,\"name\": \"Sam\",\"color\": \"ORANGE\"},{},{},{}]}]";

	@Before
	public void init() {
		ServerManager.instance().reset();
		gamesFacade = ServerManager.instance().getGamesFacade();
	}

	@Test
	public void testCreateGame() {
		CreateGame_Input params = new CreateGame_Input(false, false, false,
				"Test_Game");

		JsonParser parser = new JsonParser();

		JsonElement testGameElement = parser.parse(testGameJSON);

		assertNotNull(testGameElement);
		assertEquals(testGameElement, gamesFacade.create(params));
	}

	@Test(expected = ServerInvalidRequestException.class)
	public void testJoinGameWithNonexistentUser()
			throws ServerInvalidRequestException {
		JoinGame_Input params = new JoinGame_Input(0, "blue");
		gamesFacade.join(params, 9);
	}

	@Test(expected = ServerInvalidRequestException.class)
	public void testJoinGameNotYetCreated()
			throws ServerInvalidRequestException {

		JoinGame_Input params = new JoinGame_Input(0, "blue");
		gamesFacade.join(params, 1);
	}

	@Test(expected = ServerInvalidRequestException.class)
	public void testJoinGameWithDuplicateColor()
			throws ServerInvalidRequestException {

		CreateGame_Input creationParams = new CreateGame_Input(false, false,
				false, "Test_Game");
		ServerManager.instance().getGamesFacade().create(creationParams);

		JoinGame_Input joinParams = new JoinGame_Input(0, "blue");
		gamesFacade.join(joinParams, 1);

		joinParams = new JoinGame_Input(0, "blue");
		gamesFacade.join(joinParams, 2);
	}

	@Test(expected = ServerInvalidRequestException.class)
	public void testJoinGameAlreadyFull() throws ServerInvalidRequestException {

		CreateGame_Input creationParams = new CreateGame_Input(false, false,
				false, "Test_Game");
		ServerManager.instance().getGamesFacade().create(creationParams);

		JoinGame_Input joinParams = new JoinGame_Input(0, "blue");
		gamesFacade.join(joinParams, 1);

		joinParams = new JoinGame_Input(0, "red");
		gamesFacade.join(joinParams, 2);

		joinParams = new JoinGame_Input(0, "green");
		gamesFacade.join(joinParams, 3);

		joinParams = new JoinGame_Input(0, "white");
		gamesFacade.join(joinParams, 4);

		ServerManager.instance().getUserFacade()
				.register(new Register_Input("Ian", "ian"));

		joinParams = new JoinGame_Input(0, "purple");
		gamesFacade.join(joinParams, 5);
	}

	@Test
	public void testJoinGameAccept() throws ServerInvalidRequestException {

		CreateGame_Input creationParams = new CreateGame_Input(false, false,
				false, "Test_Game");
		ServerManager.instance().getGamesFacade().create(creationParams);

		JoinGame_Input joinParams = new JoinGame_Input(0, "orange");

		JsonElement testGameElement = new JsonPrimitive("Success");

		assertEquals(testGameElement, gamesFacade.join(joinParams, 1));
	}

	@Test
	public void testListGames() throws ServerInvalidRequestException {
		CreateGame_Input creationParams = new CreateGame_Input(false, false,
				false, "Test_Game");
		ServerManager.instance().getGamesFacade().create(creationParams);

		JoinGame_Input joinParams = new JoinGame_Input(0, "orange");
		ServerManager.instance().getGamesFacade().join(joinParams, 1);
		JsonParser parser = new JsonParser();

		JsonElement testListGamesElement = parser.parse(listGamesJSON);

		assertNotNull(testListGamesElement);
		assertEquals(testListGamesElement, gamesFacade.list());
	}
}
