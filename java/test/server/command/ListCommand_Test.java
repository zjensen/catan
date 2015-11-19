package server.command;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import server.command.games.List_Command;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;

import com.google.gson.JsonElement;

public class ListCommand_Test {

	ExchangeWrapper mockExchange; 
	List_Command cmdObj; 
	
	private final String gamesListJSON = "[{\"title\":\"Default Game\",\"id\":0,\"players\":[{},{},{},{}]}]";
	
	@Before
	public void init(){
		mockExchange = new ExchangeWrapper(null);
		
		ServerManager.instance().reset();
		ServerManager.instance().setFakeFacades();
	}
	
	@Test
	public void testCreateGame() throws ServerInvalidRequestException{
		cmdObj = new List_Command(mockExchange);
		JsonElement response = cmdObj.execute();
		assertEquals(gamesListJSON, response.toString());
	}
	
//	@Test (expected = ServerInvalidRequestException.class)
//	public void testRegisterNewUser() throws ServerInvalidRequestException{
//		mockExchange.setJsonString(jsonString2);
//		cmdObj = new Model_Command(mockExchange);
//		JsonElement response = cmdObj.execute();
//	}
}
