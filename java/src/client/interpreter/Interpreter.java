package client.interpreter;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.*;

import shared.definitions.HexType;
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
	
	public ClientModel deserialize(JsonElement arg0) throws JsonParseException
	{	
		
		JsonObject json1 = (JsonObject) arg0;
		JsonObject json2 = json1.getAsJsonObject("deck");
		DevCards deck = new DevCards();
		
		deck.setYearOfPlenty(json2.get("yearOfPlenty").getAsInt());
		deck.setMonopoly(json2.get("monopoly").getAsInt());
		deck.setSoldier(json2.get("soldier").getAsInt());
		deck.setRoadBuilding(json2.get("roadBuilding").getAsInt());
		deck.setMonument(json2.get("monument").getAsInt());
		
		JsonObject json3 = json1.getAsJsonObject("map");
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
				HexType result = null;
				switch (resource.getAsString()) {
					case "wood":
						result = HexType.WOOD;
						break;
					case "brick":
						result = HexType.BRICK;
						break;
					case "sheep":
						result = HexType.SHEEP;
						break;
					case "wheat":
						result = HexType.WHEAT;
						break;
					case "ore":
						result = HexType.ORE;
						break;
					case "desert":
						result = HexType.DESERT;
						break;
					case "water":
						result = HexType.WATER;
						break;
				}

				JsonObject json3_1_3 = json3_1_1.getAsJsonObject("location");
				int x = json3_1_3.get("x").getAsInt();
				int y = json3_1_3.get("y").getAsInt();
				HexLocation hexLocation = new HexLocation(x, y);
				int number = json3_1_1.get("number").getAsInt();
				hexes[a] = new Hex(hexLocation, result, number);
			}
		}
		
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
						
				EdgeDirection result = null;
				switch (direction.getAsString()) {
					case "N":
						result = EdgeDirection.North;
						break;
					case "NE":
						result = EdgeDirection.NorthEast;
						break;
					case "NW":
						result = EdgeDirection.NorthWest;
						break;
					case "S":
						result = EdgeDirection.South;
						break;
					case "SE":
						result = EdgeDirection.SouthEast;
						break;
					case "SW":
						result = EdgeDirection.SouthWest;
						break;
				}

				tempEdgeLocationRoads.add(new EdgeLocation(hexLocation, result));
		}
		
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
						
				VertexDirection result = null;
				switch (direction.getAsString()) {
					case "W":
						result = VertexDirection.West;
						break;
					case "NW":
						result = VertexDirection.NorthWest;
						break;
					case "NE":
						result = VertexDirection.NorthEast;
						break;
					case "E":
						result = VertexDirection.East;
						break;
					case "SE":
						result = VertexDirection.SouthEast;
						break;
					case "SW":
						result = VertexDirection.SouthWest;
						break;
				}

				tempVertexLocationCities.add(new VertexLocation(hexLocation, result));
		}
		
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
						
				VertexDirection result = null;
				switch (direction.getAsString()) {
					case "W":
						result = VertexDirection.West;
						break;
					case "NW":
						result = VertexDirection.NorthWest;
						break;
					case "NE":
						result = VertexDirection.NorthEast;
						break;
					case "E":
						result = VertexDirection.East;
						break;
					case "SE":
						result = VertexDirection.SouthEast;
						break;
					case "SW":
						result = VertexDirection.SouthWest;
						break;
				}

				tempVertexLocationSettlements.add(new VertexLocation(hexLocation, result));
		}
		
		int radius = json3.get("radius").getAsInt();
		
		JsonArray array7_1 = json3.getAsJsonArray("ports");
		Port[] ports = new Port[9];
		
		for(int b = 0; b < array7_1.size(); b++)
		{
				JsonObject json7_1_1 = array7_1.get(b).getAsJsonObject();
				int ratio = json7_1_1.get("ratio").getAsInt();
				

				JsonElement resource = json7_1_1.get("resource");
				HexType resourceResult = null;
				
				if (resource != null)
				{
					switch (resource.getAsString()) {
					case "wood":
						resourceResult = HexType.WOOD;
						break;
					case "brick":
						resourceResult = HexType.BRICK;
						break;
					case "sheep":
						resourceResult = HexType.SHEEP;
						break;
					case "wheat":
						resourceResult = HexType.WHEAT;
						break;
					case "ore":
						resourceResult = HexType.ORE;
						break;
					case "desert":
						resourceResult = HexType.DESERT;
						break;
					case "water":
						resourceResult = HexType.WATER;
						break;
					}
				}
				
				
				JsonElement direction = json7_1_1.get("direction");

				JsonObject json7_1_3 = json7_1_1.getAsJsonObject("location");
				int x = json7_1_3.get("x").getAsInt();
				int y = json7_1_3.get("y").getAsInt();
				HexLocation hexLocation = new HexLocation(x, y);
				
				EdgeDirection result = null;
				switch (direction.getAsString()) {
					case "N":
						result = EdgeDirection.North;
						break;
					case "NE":
						result = EdgeDirection.NorthEast;
						break;
					case "NW":
						result = EdgeDirection.NorthWest;
						break;
					case "S":
						result = EdgeDirection.South;
						break;
					case "SE":
						result = EdgeDirection.SouthEast;
						break;
					case "SW":
						result = EdgeDirection.SouthWest;
						break;
				}

				if (resourceResult == null)
				{
					ports[b] = new Port(hexLocation, result, ratio);
				}
				else
				{
					ports[b] = new Port(resourceResult, hexLocation, result, ratio);
				}	
		}
		
		
		JsonObject json8 = json3.getAsJsonObject("robber");
		int x = json8.get("x").getAsInt();
		int y = json8.get("y").getAsInt();
		HexLocation robber = new HexLocation(x, y);
		
		
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
		
		
		ResourceCards bank = new ResourceCards();
		JsonObject json11 = json1.getAsJsonObject("bank");
		bank.setBrick(json11.get("brick").getAsInt());
		bank.setWood(json11.get("wood").getAsInt());
		bank.setSheep(json11.get("sheep").getAsInt());
		bank.setWheat(json11.get("wheat").getAsInt());
		bank.setOre(json11.get("ore").getAsInt());
		
		
		JsonObject json12 = json1.getAsJsonObject("turnTracker");
		TurnTracker turnTracker = new TurnTracker();
		
		turnTracker.setStatus(json12.get("status").getAsString());
		turnTracker.setCurrentTurn(json12.get("currentTurn").getAsInt());
		turnTracker.setLongestRoad(json12.get("longestRoad").getAsInt());
		turnTracker.setLargestArmy(json12.get("largestArmy").getAsInt());
		
		
		int winner = json1.get("winner").getAsInt();
		int version = json1.get("version").getAsInt();
		

//=================== Create ClientModel ========================================			
		
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
		//newClientModel.setTradeOffer(tradeOffer);
		newClientModel.setTurnTracker(turnTracker);
		newClientModel.setVersion(version);
		newClientModel.setWinner(winner);

		
//================= Testing the code for accuracy ===============================			
		
		System.out.println("----AFTER PARSING----\n");
		
		System.out.println("----Deck----");
		System.out.println(deck.toString());
		
		System.out.println("----Hexes----");
		for (int b = 0; b < hexes.length; b++)
		{
			System.out.println(b + " " + hexes[b].toString());
		}
		
		System.out.println("----Roads----");
		for (int b = 0; b < tempEdgeLocationRoads.size(); b++)
		{
			System.out.println(b + " " + tempEdgeLocationRoads.get(b).toString());
		}
		
		System.out.println("----Cities----");
		for (int b = 0; b < tempVertexLocationCities.size(); b++)
		{
			System.out.println(b + " " + tempVertexLocationCities.get(b).toString());
		}
		
		System.out.println("----Settlements----");
		for (int b = 0; b < tempVertexLocationSettlements.size(); b++)
		{
			System.out.println(b + " " + tempVertexLocationSettlements.get(b).toString());
		}
		
		System.out.println("----Ports----");
		for (int b = 0; b < ports.length; b++)
		{
			System.out.println(b + " " + ports[b].toString());
		}
		
		System.out.println("----Robber----");
		System.out.println(robber.toString());
	
		System.out.println("----Players----");
		for (int b = 0; b < players.length; b++)
		{
			System.out.println(b + " " + players[b].toString());
		}
		
		System.out.println("----Log----");
		for (int b = 0; b < logLines.length; b++)
		{
			System.out.println(b + " " + logLines[b].toString());
		}
		
		System.out.println("----Chat----");
		for (int b = 0; b < chatLines.length; b++)
		{
			System.out.println(b + " " + chatLines[b].toString());
		}
		
		System.out.println("----Bank----");
		System.out.println(bank.toString());
		
		System.out.println("----TurnTracker");
		System.out.println(turnTracker.toString());
		
		System.out.println("----Winner----");
		System.out.println(winner);
		
		System.out.println("----Version----");
		System.out.println(version);
		
	
		
		return newClientModel;
	}
	
}
	
	

