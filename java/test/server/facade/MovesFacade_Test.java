package server.facade;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shared.models.ClientModel;
import client.facade.ClientFacade;
import client.interpreter.Interpreter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class MovesFacade_Test {

	private ClientModel clientModel;
	private ClientFacade cf;
	
	@Before
	public void setUp() throws Exception
	{
		Interpreter interpreter = new Interpreter();
				
		StringBuilder result = new StringBuilder("");
	    File file = new File("CanDoTestJSON.txt");
		
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
				
		JsonElement jsonToParse = new JsonParser().parse(jsonString).getAsJsonObject();

		clientModel = interpreter.deserialize(jsonToParse);
		
		cf = new ClientFacade(clientModel);
				
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void test()
	{
		Interpreter interpreter = new Interpreter();
		String s = interpreter.serialize(clientModel).toString();
		clientModel =interpreter.deserialize(s);
		System.out.println(clientModel);
		assert(true);
	}

}
