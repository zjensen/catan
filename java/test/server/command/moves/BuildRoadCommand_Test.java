package server.command.moves;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import server.command.ExchangeWrapper;
import server.command.game.Model_Command;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;

public class BuildRoadCommand_Test 
{
	ExchangeWrapper mockExchange; 
	BuildRoad_Command cmdObj; 
	
	@Before
	public void init(){
		mockExchange = new ExchangeWrapper(null);
		
		ServerManager.instance().reset();
		ServerManager.instance().setFakeFacades();
	}
	
	@Test
	public void testGetCurrentModel() throws ServerInvalidRequestException{
		String jsonString = "{\"type\": \"buildRoad\",\"playerIndex\": 1,"
				+ "\"roadLocation\":{\"x\": 1, \"y\": 1, \"direction\": \"N\"},\"free\": true}";
		JsonObject json = new JsonParser().parse(jsonString)
				.getAsJsonObject();
		mockExchange.setJson(json);
		cmdObj = new BuildRoad_Command(mockExchange);
		cmdObj.setPlayerID(1);
		cmdObj.setGameID(1);
		
		assert(cmdObj.execute().getClass() != JsonPrimitive.class);
	}
}
