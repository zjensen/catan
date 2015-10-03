package client.interpreter;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.*;

import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.models.*;


public class Interpreter 
{
	
	public Interpreter() {	

	}
	
	public HexType determineHexType(String resourceType)
	{
		HexType result = null;
		switch (resourceType) {
		case "wood":
			return result = HexType.WOOD;
		case "brick":
			return result = HexType.BRICK;
		case "sheep":
			return result = HexType.SHEEP;
		case "wheat":
			return result = HexType.WHEAT;
		case "ore":
			return result = HexType.ORE;
		case "desert":
			return result = HexType.DESERT;
		case "water":
			return result = HexType.WATER;
		}
		return result;
	}
	
	public ResourceType determineResourceType(String resourceType)
	{
		ResourceType result = null;
		switch (resourceType) {
		case "wood":
			return result = ResourceType.WOOD;
		case "brick":
			return result = ResourceType.BRICK;
		case "sheep":
			return result = ResourceType.SHEEP;
		case "wheat":
			return result = ResourceType.WHEAT;
		case "ore":
			return result = ResourceType.ORE;
		}
		return result;
	}

	public EdgeDirection determineEdgeDirection (String edgeDirection)
	{
		EdgeDirection result = null;
		switch (edgeDirection) {
			case "N":
				return result = EdgeDirection.North;
			case "NE":
				return result = EdgeDirection.NorthEast;
			case "NW":
				return result = EdgeDirection.NorthWest;
			case "S":
				return result = EdgeDirection.South;
			case "SE":
				return result = EdgeDirection.SouthEast;
			case "SW":
				return result = EdgeDirection.SouthWest;
		}
		return result;
	}	
	
	public VertexDirection determineVertexDirection (String vertexDirection)
	{
		VertexDirection result = null;
		switch (vertexDirection) {
			case "W":
				return result = VertexDirection.West;
			case "NW":
				return result = VertexDirection.NorthWest;
			case "NE":
				return result = VertexDirection.NorthEast;
			case "E":
				return result = VertexDirection.East;
			case "SE":
				return result = VertexDirection.SouthEast;
			case "SW":
				return result = VertexDirection.SouthWest;
		}
		return result;
	}
	
	public ClientModel deserialize(JsonElement arg0) throws JsonParseException
	{	
		
		JsonObject json1 = (JsonObject) arg0;
		
	// Get Deck
		JsonObject json2 = json1.getAsJsonObject("deck");
		DevCards deck = new DevCards();
		
		deck.setYearOfPlenty(json2.get("yearOfPlenty").getAsInt());
		deck.setMonopoly(json2.get("monopoly").getAsInt());
		deck.setSoldier(json2.get("soldier").getAsInt());
		deck.setRoadBuilding(json2.get("roadBuilding").getAsInt());
		deck.setMonument(json2.get("monument").getAsInt());
		
		
	// Get Map	
		JsonObject json3 = json1.getAsJsonObject("map");
		
		
	//Get Hexes
		JsonArray array3_1 = json3.getAsJsonArray("hexes");
		Hex[] hexes = new Hex[19];
		
		for(int a = 0; a < 19; a++)
		{
			if (a == 0)
			{
				JsonObject json3_1_1 = array3_1.get(a).getAsJsonObject();
				JsonObject json3_1_2 = json3_1_1.getAsJsonObject("location");
				int x = json3_1_2.get("x").getAsInt();
				int y = json3_1_2.get("y").getAsInt();
				HexLocation hexLocation = new HexLocation(x, y);
				hexes[a] = new Hex(hexLocation);
			}
			else
			{
				JsonObject json3_1_1 = array3_1.get(a).getAsJsonObject();
				JsonElement resource = json3_1_1.get("resource");

				JsonObject json3_1_3 = json3_1_1.getAsJsonObject("location");
				int x = json3_1_3.get("x").getAsInt();
				int y = json3_1_3.get("y").getAsInt();
				HexLocation hexLocation = new HexLocation(x, y);
				int number = json3_1_1.get("number").getAsInt();
				hexes[a] = new Hex(hexLocation, determineHexType(resource.getAsString()), number);
			}
		}
		
		
	// Get Roads
		JsonArray array4_1 = json3.getAsJsonArray("roads");
		ArrayList<EdgeLocation> tempEdgeLocationRoads = new ArrayList<>();
		ArrayList<Integer> ownerListRoads = new ArrayList<>();

		for(int b = 0; b < array4_1.size(); b++)
		{
			
				JsonObject json4_1_1 = array4_1.get(b).getAsJsonObject();
				int owner = json4_1_1.get("owner").getAsInt();
				ownerListRoads.add(owner);
				JsonObject json4_1_3 = json4_1_1.getAsJsonObject("location");
				JsonElement direction = json4_1_3.get("direction");
				int x = json4_1_3.get("x").getAsInt();
				int y = json4_1_3.get("y").getAsInt();
				HexLocation hexLocation = new HexLocation(x, y);

				tempEdgeLocationRoads.add(new EdgeLocation(hexLocation, determineEdgeDirection(direction.getAsString())).getNormalizedLocation());
		}
		
		
	// Get Cities
		JsonArray array5_1 = json3.getAsJsonArray("cities");
		ArrayList<VertexLocation> tempVertexLocationCities = new ArrayList<>();
		ArrayList<Integer> ownerListCities = new ArrayList<>();
		
		for(int b = 0; b < array5_1.size(); b++)
		{
			
				JsonObject json5_1_1 = array5_1.get(b).getAsJsonObject();
				int owner = json5_1_1.get("owner").getAsInt();
				ownerListCities.add(owner);
				JsonObject json5_1_3 = json5_1_1.getAsJsonObject("location");
				JsonElement direction = json5_1_3.get("direction");
				int x = json5_1_3.get("x").getAsInt();
				int y = json5_1_3.get("y").getAsInt();
				HexLocation hexLocation = new HexLocation(x, y);

				tempVertexLocationCities.add(new VertexLocation(hexLocation, determineVertexDirection(direction.getAsString())).getNormalizedLocation());
		}
		
		
	// Get Settlements
		JsonArray array6_1 = json3.getAsJsonArray("settlements");
		ArrayList<VertexLocation> tempVertexLocationSettlements = new ArrayList<>();
		ArrayList<Integer> ownerListSettlements = new ArrayList<>();
		
		for(int b = 0; b < array6_1.size(); b++)
		{
			
				JsonObject json6_1_1 = array6_1.get(b).getAsJsonObject();
				int owner = json6_1_1.get("owner").getAsInt();
				ownerListSettlements.add(owner);
				JsonObject json6_1_3 = json6_1_1.getAsJsonObject("location");
				JsonElement direction = json6_1_3.get("direction");
				int x = json6_1_3.get("x").getAsInt();
				int y = json6_1_3.get("y").getAsInt();
				HexLocation hexLocation = new HexLocation(x, y);
						
				tempVertexLocationSettlements.add(new VertexLocation(hexLocation, determineVertexDirection(direction.getAsString())).getNormalizedLocation());
		}
		
	// Get Radius
		int radius = json3.get("radius").getAsInt();
		
		
	// Get Ports
		JsonArray array7_1 = json3.getAsJsonArray("ports");
		Port[] ports = new Port[9];
		
		for(int b = 0; b < array7_1.size(); b++)
		{
				JsonObject json7_1_1 = array7_1.get(b).getAsJsonObject();
				int ratio = json7_1_1.get("ratio").getAsInt();
				

				JsonElement resource = json7_1_1.get("resource");
				ResourceType resourceResult = null;
				
				if (resource != null)
				{
					resourceResult = determineResourceType(resource.getAsString());
				}
				
				JsonElement direction = json7_1_1.get("direction");

				JsonObject json7_1_3 = json7_1_1.getAsJsonObject("location");
				int x = json7_1_3.get("x").getAsInt();
				int y = json7_1_3.get("y").getAsInt();
				HexLocation hexLocation = new HexLocation(x, y);

				if (resourceResult == null)
				{
					ports[b] = new Port(hexLocation, determineEdgeDirection(direction.getAsString()), ratio);
				}
				else
				{
					ports[b] = new Port(resourceResult, hexLocation, determineEdgeDirection(direction.getAsString()), ratio);
				}	
		}
		
		
	// Get Robber
		JsonObject json8 = json3.getAsJsonObject("robber");
		int x = json8.get("x").getAsInt();
		int y = json8.get("y").getAsInt();
		HexLocation robber = new HexLocation(x, y);
		
		
	// Get Players
		JsonArray array8 = json1.getAsJsonArray("players");
		Player[] players = new Player[array8.size()];
		
		for(int b = 0; b < array8.size(); b++)
		{
			ResourceCards resources = new ResourceCards();
			DevCards oldDevCards = new DevCards();
			DevCards newDevCards = new DevCards();
			Player addPlayer = new Player();
			
			
			JsonObject json8_1 = array8.get(b).getAsJsonObject();
			
			
			JsonObject json8_1_1 = json8_1.getAsJsonObject("resources");
			resources.setBrick(json8_1_1.get("brick").getAsInt());
			resources.setWood(json8_1_1.get("wood").getAsInt());
			resources.setSheep(json8_1_1.get("sheep").getAsInt());
			resources.setWheat(json8_1_1.get("wheat").getAsInt());
			resources.setOre(json8_1_1.get("ore").getAsInt());
			
			JsonObject json8_1_2 = json8_1.getAsJsonObject("oldDevCards");
			oldDevCards.setYearOfPlenty(json8_1_2.get("yearOfPlenty").getAsInt());
			oldDevCards.setMonopoly(json8_1_2.get("monopoly").getAsInt());
			oldDevCards.setSoldier(json8_1_2.get("soldier").getAsInt());
			oldDevCards.setRoadBuilding(json8_1_2.get("roadBuilding").getAsInt());
			oldDevCards.setMonument(json8_1_2.get("monument").getAsInt());
			
			JsonObject json8_1_3 = json8_1.getAsJsonObject("newDevCards");
			newDevCards.setYearOfPlenty(json8_1_3.get("yearOfPlenty").getAsInt());
			newDevCards.setMonopoly(json8_1_3.get("monopoly").getAsInt());
			newDevCards.setSoldier(json8_1_3.get("soldier").getAsInt());
			newDevCards.setRoadBuilding(json8_1_3.get("roadBuilding").getAsInt());
			newDevCards.setMonument(json8_1_3.get("monument").getAsInt());
			
			int playerRoads = json8_1.get("roads").getAsInt();
			int playerCities = json8_1.get("cities").getAsInt();
			int playerSettlements = json8_1.get("settlements").getAsInt();
			int playerSoldiers = json8_1.get("soldiers").getAsInt();
			int playerVictoryPoints = json8_1.get("victoryPoints").getAsInt();
			int playerMonuments = json8_1.get("monuments").getAsInt();
			boolean playerPlayedDevCard = json8_1.get("playedDevCard").getAsBoolean();
			boolean playerDiscarded = json8_1.get("discarded").getAsBoolean();
			int playerId = json8_1.get("playerID").getAsInt();
			int playerIndex = json8_1.get("playerIndex").getAsInt();
			String playerName = json8_1.get("name").getAsString();
			String playerColor = json8_1.get("color").getAsString();
			
			
			addPlayer.setResources(resources);
			addPlayer.setOldDevCards(oldDevCards);
			addPlayer.setNewDevCards(newDevCards);
			addPlayer.setRoads(playerRoads);
			addPlayer.setCities(playerCities);
			addPlayer.setSettlements(playerSettlements);
			addPlayer.setSoldiers(playerSoldiers);
			addPlayer.setVictoryPoints(playerVictoryPoints);
			addPlayer.setMonuments(playerMonuments);
			addPlayer.setPlayedDevCard(playerPlayedDevCard);
			addPlayer.setDiscarded(playerDiscarded);
			addPlayer.setPlayerID(playerId);
			addPlayer.setIndex(playerIndex);
			addPlayer.setName(playerName);
			addPlayer.setColor(playerColor);
			
			players[b] = addPlayer;
		}
		
		
	// Get Log
		JsonObject json9 = json1.getAsJsonObject("log");
		JsonArray array9_1 = json9.getAsJsonArray("lines");

		MessageList log = new MessageList();
		MessageLine[] logLines = new MessageLine[array9_1.size()];
		
		for(int a = 0; a < array9_1.size(); a++)
		{
			MessageLine newLine = new MessageLine();
			
			JsonObject json9_1_1 = array9_1.get(a).getAsJsonObject();
			
			String source = json9_1_1.get("source").getAsString();
			String message = json9_1_1.get("message").getAsString();	
			
			newLine.setSource(source);
			newLine.setMessage(message);
			
			logLines[a] = newLine;
		}
		
		log.setLines(logLines);
		
		
	// Get Chat
		JsonObject json10 = json1.getAsJsonObject("chat");
		JsonArray array10_1 = json10.getAsJsonArray("lines");
		
		MessageList chat = new MessageList();
		MessageLine[] chatLines = new MessageLine[array10_1.size()];
		
		for(int a = 0; a < array10_1.size(); a++)
		{
			MessageLine newLine = new MessageLine();
			
			JsonObject json10_1_1 = array10_1.get(a).getAsJsonObject();
			
			String source = json10_1_1.get("source").getAsString();
			String message = json10_1_1.get("message").getAsString();	
			
			newLine.setSource(source);
			newLine.setMessage(message);
			chatLines[a] = newLine;
		}
		
		chat.setLines(chatLines);
		
		
	// Get Bank
		ResourceCards bank = new ResourceCards();
		JsonObject json11 = json1.getAsJsonObject("bank");
		bank.setBrick(json11.get("brick").getAsInt());
		bank.setWood(json11.get("wood").getAsInt());
		bank.setSheep(json11.get("sheep").getAsInt());
		bank.setWheat(json11.get("wheat").getAsInt());
		bank.setOre(json11.get("ore").getAsInt());
		
		
	// Get TurnTracker
		JsonObject json12 = json1.getAsJsonObject("turnTracker");
		TurnTracker turnTracker = new TurnTracker();
		
		turnTracker.setStatus(json12.get("status").getAsString());
		turnTracker.setCurrentTurn(json12.get("currentTurn").getAsInt());
		turnTracker.setLongestRoad(json12.get("longestRoad").getAsInt());
		turnTracker.setLargestArmy(json12.get("largestArmy").getAsInt());
		
	// Get Winner
		int winner = json1.get("winner").getAsInt();
		
		
	// Get Version
		int version = json1.get("version").getAsInt();
		

//=================== Create ClientModel From Pieces ========================================			
		
		HashMap<VertexLocation, Player> settlements = new HashMap<>();
		HashMap<VertexLocation, Player> cities = new HashMap<>();
		HashMap<EdgeLocation, Player> roads = new HashMap<>();
		
		
		for (int b = 0; b < tempEdgeLocationRoads.size(); b++)
		{
			roads.put(tempEdgeLocationRoads.get(b), players[ownerListRoads.get(b)]);
		}

		for (int b = 0; b < tempVertexLocationCities.size(); b++)
		{
			cities.put(tempVertexLocationCities.get(b), players[ownerListCities.get(b)]);
		}

		for (int b = 0; b < tempVertexLocationSettlements.size(); b++)
		{
			settlements.put(tempVertexLocationSettlements.get(b), players[ownerListSettlements.get(b)]);	
		}
			
		Map map = new Map(hexes, ports, roads, settlements, cities, radius, robber);

		ClientModel newClientModel = new ClientModel();
		
		newClientModel.setBank(bank);
		newClientModel.setDeck(deck);
		newClientModel.setChat(chat);
		newClientModel.setLog(log);
		newClientModel.setMap(map);
		newClientModel.setPlayers(players);
		newClientModel.setTurnTracker(turnTracker);
		newClientModel.setVersion(version);
		newClientModel.setWinner(winner);

		return newClientModel;
	}
	
}
	
	