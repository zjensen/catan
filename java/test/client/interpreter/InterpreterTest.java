package client.interpreter;
import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.locations.*;
import shared.models.*;
import shared.utils.Interpreter;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;

import org.junit.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


public class InterpreterTest {
	
	public Interpreter interpreter;

	String jsonString;
	
	@Before
	public void init() {
		
		interpreter = new Interpreter();
		
		// Loads json file into memory to use for testing
		StringBuilder result = new StringBuilder("");
	    File file = new File("java/ResourceCardTest.txt");
		
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				result.append(scanner.nextLine()).append("\n");
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		jsonString = result.toString();
	}
	
	@Test
	public void testConversionClientModel() {
		
		JsonElement jsonToParse = new JsonParser().parse(jsonString).getAsJsonObject();

		ClientModel resultingClientModel = interpreter.deserialize(jsonToParse);

	//Bank
		assertEquals(23, resultingClientModel.getBank().getBrick());
		assertEquals(21, resultingClientModel.getBank().getWood());
		assertEquals(20, resultingClientModel.getBank().getSheep());
		assertEquals(22, resultingClientModel.getBank().getWheat());
		assertEquals(22, resultingClientModel.getBank().getOre());

	// Deck
		assertEquals(2, resultingClientModel.getDeck().getYearOfPlenty());
		assertEquals(2, resultingClientModel.getDeck().getMonopoly());
		assertEquals(14, resultingClientModel.getDeck().getSoldier());
		assertEquals(2, resultingClientModel.getDeck().getRoadBuilding());
		assertEquals(5, resultingClientModel.getDeck().getMonument());
		
	// Chat
		MessageList emptyList = new MessageList();
		emptyList.setLines(new MessageLine[0]);
		MessageList chatResultingClientModel = new MessageList();
		chatResultingClientModel.setLines(resultingClientModel.getChat().getLines());	
		assertEquals(emptyList.getLines().length, chatResultingClientModel.getLines().length);
		
	// Log
		MessageLine[] testLog = resultingClientModel.getLog().getLines();
		assertEquals("Brooke", testLog[5].getSource());
		assertEquals("Brooke's turn just ended", testLog[5].getMessage());
		
		assertEquals("Sam", testLog[22].getSource());
		assertEquals("Sam built a settlement", testLog[22].getMessage());
		
	// Map
		// Hex
			Hex[] testHexes = resultingClientModel.getMap().getHexes();
			
			HexLocation hexLoc1 = new HexLocation(0, -2);
			assertEquals(hexLoc1, testHexes[0].getLocation());
			HexLocation hexLoc2 = new HexLocation(1, 0);
			assertEquals(hexLoc2, testHexes[10].getLocation());
			assertEquals(HexType.BRICK, testHexes[10].getResource());
			
			// Desert has no resource
			assertNull(testHexes[0].getResource());

		// Port
			Port[] testPorts = resultingClientModel.getMap().getPorts();
		
			HexLocation hexLoc3 = new HexLocation(-3, 2);
			assertEquals(hexLoc3, testPorts[4].getLocation());
			assertEquals(ResourceType.WOOD, testPorts[4].getResourceType());
			assertEquals(EdgeDirection.NorthEast, testPorts[4].getDirection());
			assertEquals(2, testPorts[4].getRatio());
		
		// Roads
			HashMap<EdgeLocation, Player> testRoads = resultingClientModel.getMap().getRoads();

			HexLocation hexLoc4 = new HexLocation(2, -2);
			EdgeLocation testEdgeLoc = new EdgeLocation(hexLoc4, EdgeDirection.SouthWest).getNormalizedLocation();
			assertEquals("Mark", testRoads.get(testEdgeLoc).getName());
			assertEquals(11, testRoads.get(testEdgeLoc).getPlayerID());
			
		// Settlements
			HashMap<VertexLocation, Player> testSettlements = resultingClientModel.getMap().getSettlements();
			
			HexLocation hexLoc5 = new HexLocation(0, 0);
			VertexLocation testVertexLoc = new VertexLocation(hexLoc5, VertexDirection.SouthWest).getNormalizedLocation();
			assertEquals("Pete", testSettlements.get(testVertexLoc).getName());
			assertEquals(10, testSettlements.get(testVertexLoc).getPlayerID());
			
		// Cities
			HashMap<VertexLocation, Player> testCities = resultingClientModel.getMap().getCities();		
			
			assertNotNull(testCities);
			
		// Radius
			int testRadius = resultingClientModel.getMap().getRadius();
			
			assertEquals(3, testRadius);
	
		// Robber
			HexLocation testRobber = resultingClientModel.getMap().getRobber();
			
			HexLocation robberHex = new HexLocation(0, -2);
			assertEquals(robberHex, testRobber);
			
	// Player
		Player[] testPlayers = resultingClientModel.getPlayers();
		
		assertEquals("Sam", testPlayers[0].getName());
		assertEquals("green", testPlayers[0].getColor());
		assertEquals(13, testPlayers[0].getRoads());
		assertEquals(4, testPlayers[0].getCities());
		
		assertEquals("Brooke", testPlayers[1].getName());
		assertEquals("blue", testPlayers[1].getColor());
		assertEquals(13, testPlayers[1].getRoads());
		assertEquals(4, testPlayers[1].getCities());

	// TurnTracker
		TurnTracker testTurnTracker = resultingClientModel.getTurnTracker();
		
		assertEquals("Rolling", testTurnTracker.getStatus());
		assertEquals(0, testTurnTracker.getCurrentTurn());
		assertEquals(-1, testTurnTracker.getLongestRoad());
		assertEquals(-1, testTurnTracker.getLargestArmy());
		
	// Version
		int testVersion = resultingClientModel.getVersion();
		
		assertEquals(0, testVersion);
		
	// Winner 
		int testWinner = resultingClientModel.getWinner();
		
		assertEquals(-1, testWinner);
		
		return;
	}
	
}
