package server.command;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import server.command.user.Login_Command;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class LoginCommand_Test {

	ExchangeWrapper mockExchange; 
	Login_Command cmdObj; 
	String jsonString = "{\"username\": \"Sam\",\"password\": \"sam\"}";
	String jsonString2 = "{\"username\": \"Ian\",\"password\": \"ian\"}";
	
	@Before
	public void init(){
		mockExchange = new ExchangeWrapper(null);
		
		ServerManager.instance().reset();
	}
	
	@Test
	public void testLoginNotRegistered() throws ServerInvalidRequestException{
		mockExchange.setJsonString(jsonString);
		cmdObj = new Login_Command(mockExchange);
		JsonElement response = cmdObj.execute();
		assertEquals(new JsonPrimitive("Success"), response);
	}
	
	@Test (expected = ServerInvalidRequestException.class)
	public void testRegisterNewUser() throws ServerInvalidRequestException{
		mockExchange.setJsonString(jsonString2);
		cmdObj = new Login_Command(mockExchange);
		JsonElement response = cmdObj.execute();
	}
}
