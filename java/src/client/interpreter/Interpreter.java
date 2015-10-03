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
		JsonObject mainJson = (JsonObject) arg0;
		
	// Get Deck
		JsonObject deckJson = mainJson.getAsJsonObject("deck");
		DevCards deck = new DevCards();
		
		deck.setYearOfPlenty(deckJson.get("yearOfPlenty").getAsInt());
		deck.setMonopoly(deckJson.get("monopoly").getAsInt());
		deck.setSoldier(deckJson.get("soldier").getAsInt());
		deck.setRoadBuilding(deckJson.get("roadBuilding").getAsInt());
		deck.setMonument(deckJson.get("monument").getAsInt());
		
		
	// Get Map	
		JsonObject mapJson = mainJson.getAsJsonObject("map");
		
		
	//Get Hexes
		JsonArray hexesJson = mapJson.getAsJsonArray("hexes");
		Hex[] hexes = new Hex[19];
		
		for(int a = 0; a < 19; a++)
		{
			if (a == 0)
			{
				JsonObject hexJson = hexesJson.get(a).getAsJsonObject();
				JsonObject locationJson = hexJson.getAsJsonObject("location");
				int x = locationJson.get("x").getAsInt();
				int y = locationJson.get("y").getAsInt();
				HexLocation hexLocation = new HexLocation(x, y);
				hexes[a] = new Hex(hexLocation);
			}
			else
			{
				JsonObject hexJson = hexesJson.get(a).getAsJsonObject();
				JsonElement resource = hexJson.get("resource");

				JsonObject locationJson = hexJson.getAsJsonObject("location");
				int x = locationJson.get("x").getAsInt();
				int y = locationJson.get("y").getAsInt();
				HexLocation hexLocation = new HexLocation(x, y);
				int number = hexJson.get("number").getAsInt();
				hexes[a] = new Hex(hexLocation, determineHexType(resource.getAsString()), number);
			}
		}
		
		
	// Get Roads
		JsonArray roadsJson = mapJson.getAsJsonArray("roads");
		ArrayList<EdgeLocation> tempEdgeLocationRoads = new ArrayList<>();
		ArrayList<Integer> ownerListRoads = new ArrayList<>();

		for(int b = 0; b < roadsJson.size(); b++)
		{
				JsonObject roadJson = roadsJson.get(b).getAsJsonObject();
				int owner = roadJson.get("owner").getAsInt();
				ownerListRoads.add(owner);
				JsonObject locationJson = roadJson.getAsJsonObject("location");
				JsonElement direction = locationJson.get("direction");
				int x = locationJson.get("x").getAsInt();
				int y = locationJson.get("y").getAsInt();
				HexLocation hexLocation = new HexLocation(x, y);

				tempEdgeLocationRoads.add(new EdgeLocation(hexLocation, determineEdgeDirection(direction.getAsString())).getNormalizedLocation());
		}
		
		
	// Get Cities
		JsonArray citiesJson = mapJson.getAsJsonArray("cities");
		ArrayList<VertexLocation> tempVertexLocationCities = new ArrayList<>();
		ArrayList<Integer> ownerListCities = new ArrayList<>();
		
		for(int b = 0; b < citiesJson.size(); b++)
		{
				JsonObject cityJson = citiesJson.get(b).getAsJsonObject();
				int owner = cityJson.get("owner").getAsInt();
				ownerListCities.add(owner);
				JsonObject locationJson = cityJson.getAsJsonObject("location");
				JsonElement direction = locationJson.get("direction");
				int x = locationJson.get("x").getAsInt();
				int y = locationJson.get("y").getAsInt();
				HexLocation hexLocation = new HexLocation(x, y);

				tempVertexLocationCities.add(new VertexLocation(hexLocation, determineVertexDirection(direction.getAsString())).getNormalizedLocation());
		}
		
		
	// Get Settlements
		JsonArray settlementsJson = mapJson.getAsJsonArray("settlements");
		ArrayList<VertexLocation> tempVertexLocationSettlements = new ArrayList<>();
		ArrayList<Integer> ownerListSettlements = new ArrayList<>();
		
		for(int b = 0; b < settlementsJson.size(); b++)
		{
				JsonObject settlementJson = settlementsJson.get(b).getAsJsonObject();
				int owner = settlementJson.get("owner").getAsInt();
				ownerListSettlements.add(owner);
				JsonObject locationJson = settlementJson.getAsJsonObject("location");
				JsonElement direction = locationJson.get("direction");
				int x = locationJson.get("x").getAsInt();
				int y = locationJson.get("y").getAsInt();
				HexLocation hexLocation = new HexLocation(x, y);
						
				tempVertexLocationSettlements.add(new VertexLocation(hexLocation, determineVertexDirection(direction.getAsString())).getNormalizedLocation());
		}
		
	// Get Radius
		int radius = mapJson.get("radius").getAsInt();
		
		
	// Get Ports
		JsonArray portsJson = mapJson.getAsJsonArray("ports");
		Port[] ports = new Port[9];
		
		for(int b = 0; b < portsJson.size(); b++)
		{
				JsonObject portJson = portsJson.get(b).getAsJsonObject();
				int ratio = portJson.get("ratio").getAsInt();

				JsonElement resource = portJson.get("resource");
				ResourceType resourceResult = null;
				
				if (resource != null)
				{
					resourceResult = determineResourceType(resource.getAsString());
				}
				
				JsonElement direction = portJson.get("direction");

				JsonObject locationJson = portJson.getAsJsonObject("location");
				int x = locationJson.get("x").getAsInt();
				int y = locationJson.get("y").getAsInt();
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
		JsonObject robberJson = mapJson.getAsJsonObject("robber");
		int x = robberJson.get("x").getAsInt();
		int y = robberJson.get("y").getAsInt();
		HexLocation robber = new HexLocation(x, y);
		
		
	// Get Players
		JsonArray playersJson = mainJson.getAsJsonArray("players");
		Player[] players = new Player[playersJson.size()];
		
		for(int b = 0; b < playersJson.size(); b++)
		{
			ResourceCards resources = new ResourceCards();
			DevCards oldDevCards = new DevCards();
			DevCards newDevCards = new DevCards();
			Player addPlayer = new Player();
			
			
			JsonObject playerItemsJson = playersJson.get(b).getAsJsonObject();
			
			
			JsonObject resourcesJson = playerItemsJson.getAsJsonObject("resources");
			resources.setBrick(resourcesJson.get("brick").getAsInt());
			resources.setWood(resourcesJson.get("wood").getAsInt());
			resources.setSheep(resourcesJson.get("sheep").getAsInt());
			resources.setWheat(resourcesJson.get("wheat").getAsInt());
			resources.setOre(resourcesJson.get("ore").getAsInt());
			
			JsonObject oldDevCardsJson = playerItemsJson.getAsJsonObject("oldDevCards");
			oldDevCards.setYearOfPlenty(oldDevCardsJson.get("yearOfPlenty").getAsInt());
			oldDevCards.setMonopoly(oldDevCardsJson.get("monopoly").getAsInt());
			oldDevCards.setSoldier(oldDevCardsJson.get("soldier").getAsInt());
			oldDevCards.setRoadBuilding(oldDevCardsJson.get("roadBuilding").getAsInt());
			oldDevCards.setMonument(oldDevCardsJson.get("monument").getAsInt());
			
			JsonObject newDevCardsJson = playerItemsJson.getAsJsonObject("newDevCards");
			newDevCards.setYearOfPlenty(newDevCardsJson.get("yearOfPlenty").getAsInt());
			newDevCards.setMonopoly(newDevCardsJson.get("monopoly").getAsInt());
			newDevCards.setSoldier(newDevCardsJson.get("soldier").getAsInt());
			newDevCards.setRoadBuilding(newDevCardsJson.get("roadBuilding").getAsInt());
			newDevCards.setMonument(newDevCardsJson.get("monument").getAsInt());
			
			int playerRoads = playerItemsJson.get("roads").getAsInt();
			int playerCities = playerItemsJson.get("cities").getAsInt();
			int playerSettlements = playerItemsJson.get("settlements").getAsInt();
			int playerSoldiers = playerItemsJson.get("soldiers").getAsInt();
			int playerVictoryPoints = playerItemsJson.get("victoryPoints").getAsInt();
			int playerMonuments = playerItemsJson.get("monuments").getAsInt();
			boolean playerPlayedDevCard = playerItemsJson.get("playedDevCard").getAsBoolean();
			boolean playerDiscarded = playerItemsJson.get("discarded").getAsBoolean();
			int playerId = playerItemsJson.get("playerID").getAsInt();
			int playerIndex = playerItemsJson.get("playerIndex").getAsInt();
			String playerName = playerItemsJson.get("name").getAsString();
			String playerColor = playerItemsJson.get("color").getAsString();
			
			
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
		JsonObject logJson = mainJson.getAsJsonObject("log");
		JsonArray logLinesJson = logJson.getAsJsonArray("lines");

		MessageList log = new MessageList();
		MessageLine[] logLines = new MessageLine[logLinesJson.size()];
		
		for(int a = 0; a < logLinesJson.size(); a++)
		{
			MessageLine newLine = new MessageLine();
			
			JsonObject lineJson = logLinesJson.get(a).getAsJsonObject();
			
			String source = lineJson.get("source").getAsString();
			String message = lineJson.get("message").getAsString();	
			
			newLine.setSource(source);
			newLine.setMessage(message);
			
			logLines[a] = newLine;
		}
		
		log.setLines(logLines);
		
		
	// Get Chat
		JsonObject chatJson = mainJson.getAsJsonObject("chat");
		JsonArray chatLinesJson = chatJson.getAsJsonArray("lines");
		
		MessageList chat = new MessageList();
		MessageLine[] chatLines = new MessageLine[chatLinesJson.size()];
		
		for(int a = 0; a < chatLinesJson.size(); a++)
		{
			MessageLine newLine = new MessageLine();
			
			JsonObject lineJson = chatLinesJson.get(a).getAsJsonObject();
			
			String source = lineJson.get("source").getAsString();
			String message = lineJson.get("message").getAsString();	
			
			newLine.setSource(source);
			newLine.setMessage(message);
			chatLines[a] = newLine;
		}
		
		chat.setLines(chatLines);
		
		
	// Get Bank
		ResourceCards bank = new ResourceCards();
		JsonObject bankJson = mainJson.getAsJsonObject("bank");
		bank.setBrick(bankJson.get("brick").getAsInt());
		bank.setWood(bankJson.get("wood").getAsInt());
		bank.setSheep(bankJson.get("sheep").getAsInt());
		bank.setWheat(bankJson.get("wheat").getAsInt());
		bank.setOre(bankJson.get("ore").getAsInt());
		
		
	// Get TurnTracker
		JsonObject turnTrackerJson = mainJson.getAsJsonObject("turnTracker");
		TurnTracker turnTracker = new TurnTracker();
		
		turnTracker.setStatus(turnTrackerJson.get("status").getAsString());
		turnTracker.setCurrentTurn(turnTrackerJson.get("currentTurn").getAsInt());
		turnTracker.setLongestRoad(turnTrackerJson.get("longestRoad").getAsInt());
		turnTracker.setLargestArmy(turnTrackerJson.get("largestArmy").getAsInt());
		
	// Get Winner
		int winner = mainJson.get("winner").getAsInt();
		
		
	// Get Version
		int version = mainJson.get("version").getAsInt();
		

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
	
	