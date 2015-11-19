package server.command;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import server.command.games.Join_Command;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class JoinCommand_Test {

	ExchangeWrapper mockExchange; 
	Join_Command cmdObj; 
	
	String jsonInput = "{ "+
  "\"id\":0," +
  "\"color\": \"blue\"" +
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
		cmdObj = new Join_Command(mockExchange);
		JsonElement response = cmdObj.execute();
		assertEquals(new JsonPrimitive("Success"), response);
	}
	
//	@Test (expected = ServerInvalidRequestException.class)
//	public void testRegisterNewUser() throws ServerInvalidRequestException{
//		mockExchange.setJsonString(jsonString2);
//		cmdObj = new Model_Command(mockExchange);
//		JsonElement response = cmdObj.execute();
//	}
}
