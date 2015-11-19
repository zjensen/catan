package server.command;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import server.command.games.Create_Command;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;

import com.google.gson.JsonElement;

public class CreateCommand_Test {

	ExchangeWrapper mockExchange; 
	Create_Command cmdObj; 
	
	String jsonInput = "{ "+
  "\"randomTiles\": \"false\"," +
  "\"randomNumbers\": \"false\"," +
  "\"randomPorts\": \"false\"," +
  "\"name\": \"newgame\"" +
  "}";
	
	@Before
	public void init(){
		mockExchange = new ExchangeWrapper(null);
		
		ServerManager.instance().reset();
		ServerManager.instance().setFakeFacades();
	}
	
	@Test
	public void testCreateGame() throws ServerInvalidRequestException{
		mockExchange.setJsonString(jsonInput);
		cmdObj = new Create_Command(mockExchange);
		JsonElement response = cmdObj.execute();
		assertEquals(String.format(
				"{\"title\":\"%s\",\"id\":1,\"players\":[{},{},{},{}]}",
				"newgame"), response.toString());
	}
	
//	@Test (expected = ServerInvalidRequestException.class)
//	public void testRegisterNewUser() throws ServerInvalidRequestException{
//		mockExchange.setJsonString(jsonString2);
//		cmdObj = new Model_Command(mockExchange);
//		JsonElement response = cmdObj.execute();
//	}
}
