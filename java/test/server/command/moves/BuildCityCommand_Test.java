package server.command.moves;

import org.junit.Before;
import org.junit.Test;

import server.command.ExchangeWrapper;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;

import com.google.gson.JsonPrimitive;

public class BuildCityCommand_Test {

	ExchangeWrapper mockExchange; 
	BuildCity_Command cmdObj; 
	
	String jsonInput = "{ "+
  "\"type\": \"buildCity\"," +
  "\"playerIndex\": 0," +
  "\"vertexLocation\": {\"x\":1,\"y\":1,\"direction\": \"NW\"}" +
  "}";
	
	
	
	@Before
	public void init(){
		mockExchange = new ExchangeWrapper(null);
		ServerManager.instance().reset();
		ServerManager.instance().setFakeFacades();
	}
	
	@Test
	public void testBuildCity() throws ServerInvalidRequestException{
		mockExchange.setJsonString(jsonInput);
		cmdObj = new BuildCity_Command(mockExchange);
		cmdObj.setPlayerID(0);
		cmdObj.setGameID(0);
		
		assert(cmdObj.execute().getClass() != JsonPrimitive.class);
	}
	
}
