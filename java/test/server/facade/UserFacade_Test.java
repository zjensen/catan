package server.facade;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.user.Login_Input;
import shared.communication.user.Register_Input;
import shared.models.ClientModel;
import shared.utils.Interpreter;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class UserFacade_Test {

	private ClientModel testModel;
	private Interpreter interpreter = new Interpreter();
	private int gameID;
	private IUserFacade userFacade;

	private String testUserSam = "{\"id\":1, \"username\":\"Sam\", \"password\":\"sam\"}";
	private String testUserOne = "{\"id\":5, \"username\":\"Jack\", \"password\":\"jack\"}";
	private String testUserTwo = "{\"id\":6, \"username\":\"Ian\", \"password\":\"ian\"}";

	@Before
	public void init() throws Exception {
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
		userFacade = ServerManager.instance().getUserFacade();
	}

	@Test(expected = ServerInvalidRequestException.class)
	public void registerUserWhoAlreadyExists()
			throws ServerInvalidRequestException {

		Register_Input params = new Register_Input("Koopa", "koopa");

		userFacade.register(params);
		userFacade.register(params);

	}

	@Test
	public void registerOneUser() throws ServerInvalidRequestException {

		Register_Input params = new Register_Input("Jack", "jack");

		JsonElement testUserOneJson = new JsonParser().parse(testUserOne)
				.getAsJsonObject();

		assertEquals(testUserOneJson, userFacade.register(params));
	}

	@Test
	public void registerTwoUsers() throws ServerInvalidRequestException {

		Register_Input params = new Register_Input("Jack", "jack");

		JsonElement testUserOneJson = new JsonParser().parse(testUserOne)
				.getAsJsonObject();

		assertEquals(testUserOneJson, userFacade.register(params));

		params = new Register_Input("Ian", "ian");

		JsonElement testUserTwoJson = new JsonParser().parse(testUserTwo)
				.getAsJsonObject();

		assertEquals(testUserTwoJson, userFacade.register(params));
	}

	@Test(expected = ServerInvalidRequestException.class)
	public void testLoginWithInvalidUser() throws ServerInvalidRequestException {

		Login_Input params = new Login_Input("Koopa", "koopa");

		userFacade.login(params);

	}

	@Test
	public void testLoginWithRegisteredUser()
			throws ServerInvalidRequestException {

		Login_Input params = new Login_Input("Sam", "sam");

		userFacade.login(params);

		JsonElement testUserSamJson = new JsonParser().parse(testUserSam)
				.getAsJsonObject();

		assertEquals(testUserSamJson, userFacade.login(params));
	}

}
