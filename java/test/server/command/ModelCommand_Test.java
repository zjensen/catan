package server.command;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import server.command.game.Model_Command;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ModelCommand_Test {

	ExchangeWrapper mockExchange; 
	Model_Command cmdObj; 
	
	@Before
	public void init(){
		mockExchange = new ExchangeWrapper(null);
		
		ServerManager.instance().reset();
		ServerManager.instance().setFakeFacades();
	}
	
	@Test
	public void testGetCurrentModel() throws ServerInvalidRequestException{
		StringBuilder result = new StringBuilder("");
		File file = new File("java/MovesFacadeTestJSON.txt");

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
		JsonObject jsonToParse = new JsonParser().parse(jsonString)
				.getAsJsonObject();
		
		mockExchange.setJsonString("10");
		cmdObj = new Model_Command(mockExchange);
		JsonElement response = cmdObj.execute();
		assertEquals(jsonToParse, response);
	}
	
//	@Test (expected = ServerInvalidRequestException.class)
//	public void testRegisterNewUser() throws ServerInvalidRequestException{
//		mockExchange.setJsonString(jsonString2);
//		cmdObj = new Model_Command(mockExchange);
//		JsonElement response = cmdObj.execute();
//	}
}
