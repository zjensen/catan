package server.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;
import shared.models.*;
import shared.utils.Interpreter;

public class MapGenerator {
	
	private Map defaultMap;
	private Interpreter interpreter;
	
	public MapGenerator() {
		loadDefaultMap();
	}
	
	private void loadDefaultMap() {
		interpreter = new Interpreter();
		StringBuilder result = new StringBuilder("");
	    File file = new File("json/DefaultMapJSON.txt");
	    
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

		defaultMap = interpreter.deserializeDefaultMap(jsonToParse);
	}
	
	public Map generateMap(boolean randHexes, boolean randPorts, boolean randNumbers) {
		
		Map returnMap = new Map(defaultMap.getHexes(), defaultMap.getPorts(), defaultMap.getRoads(), 
				defaultMap.getSettlements(), defaultMap.getCities(), 
				defaultMap.getRadius(), defaultMap.getRobber());
		if(randHexes) randomizeTiles(returnMap);
		if(randPorts) randomizePorts(returnMap);
		if(randNumbers) randomizeNumbers(returnMap);
		return returnMap;
	}
	
	private static void randomizeTiles(Map m) {
		ArrayList<HexType> hexList = new ArrayList<>();
		Random rand = new Random();
		for(Hex h : m.getHexes()) {
			hexList.add(h.getResource());
		}
		int randomNumber = 0;
		int i = 0;
		while(hexList.size() > 0) {
			randomNumber = rand.nextInt(hexList.size());
			m.getHexes()[i].setResource(hexList.get(randomNumber));
			if(hexList.get(randomNumber)==null)
				m.setRobber(new HexLocation(m.getHexes()[i].getLocation().getX(), m.getHexes()[i].getLocation().getY()));
			hexList.remove(randomNumber);
			i++;
		}
	}
	
	private static void randomizePorts(Map m) {
		ArrayList<ResourceType> portList = new ArrayList<>();
		Random rand = new Random();
		for(Port h : m.getPorts()) {
			portList.add(h.getResourceType());
		}
		int randomNumber = 0;
		int i = 0;
		while(portList.size() > 0) {
			randomNumber = rand.nextInt(portList.size());
			m.getPorts()[i].setResourceType(portList.get(randomNumber));
			if(portList.get(randomNumber) == null)
				m.getPorts()[i].setRatio(3);
			else
				m.getPorts()[i].setRatio(2);
			portList.remove(randomNumber);
			i++;
		}
	}
	
	private static void randomizeNumbers(Map m) {
		ArrayList<Integer> numberList = new ArrayList<>();
		Random rand = new Random();
		for(Hex h : m.getHexes()) {
			if(h.getNumber() != -1)
				numberList.add(h.getNumber());
		}
		int randomNumber = 0;
		int i = 0;
		while(numberList.size() > 0) {
			randomNumber = rand.nextInt(numberList.size());
			if(m.getHexes()[i].getResource() == null) 
				i++;
			m.getHexes()[i].setNumber(numberList.get(randomNumber));
			numberList.remove(randomNumber);
			i++;
		}
	}
	
	
//	public static ClientModel buildZacksClientModel() {
//	ClientModel model;
//	Interpreter interpreter = new Interpreter();
//	StringBuilder result = new StringBuilder("");
//    File file = new File("MovesFacadeTestJSON.txt");
//	
//	try (Scanner scanner = new Scanner(file)) {
//
//		while (scanner.hasNextLine()) {
//			String line = scanner.nextLine();
//			result.append(line).append("\n");
//		}
//		scanner.close();
//
//	} catch (IOException e) {
//		e.printStackTrace();
//	}
//	
//	String jsonString = result.toString();
//			
//	JsonElement jsonToParse = new JsonParser().parse(jsonString).getAsJsonObject();
//
//	model = interpreter.deserialize(jsonToParse);
//	return model;
//}
}
