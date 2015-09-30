package client.interpreter;
import shared.models.*;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Scanner;

import org.junit.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import sun.misc.IOUtils;


public class InterpreterTest {
	
	public Interpreter interpreter;
	//public ClientModel tcm;
	//public ResourceCards rc;
	GsonBuilder gsonBuilder;
	Gson gson;
	String jsonString;
	
	@Before
	public void init() {
		gsonBuilder = new GsonBuilder();
		gson = gsonBuilder.create();
		
		interpreter = new Interpreter();
		
		
		// Loads json file into memory to use for testing
		StringBuilder result = new StringBuilder("");
	    File file = new File("ResourceCardTest.txt");
		
		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}
			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		jsonString = result.toString();
	}
	
	@Test
	public void testConversionClientModel() {
		
		//System.out.println(json.toString());
		JsonElement jo = new JsonParser().parse(jsonString).getAsJsonObject();
		ResourceCards bank = (ResourceCards) interpreter.BankDeserialize.deserialize(jo, null, null);
		//DevCards deck = (DevCards) interpreter.DeckDeserializer.deserialize(jo,null,null);
		
		
		//System.out.println(bank.toString());
		
		return;
	}
	
	
	
}