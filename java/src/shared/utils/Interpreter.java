package shared.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import client.data.GameInfo;
import client.data.PlayerInfo;

import com.google.gson.*;

import shared.definitions.CatanColor;
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
	
	public String serializeGame(Game game)
	{
		JsonObject gameJSON = new JsonObject();
		gameJSON.addProperty("id", game.getId());
		gameJSON.addProperty("title", game.getTitle());
		gameJSON.addProperty("clientModel", serialize(game.getClientModel()).toString());
		return gameJSON.toString();
	}
	
	public Game deserializeGame(String json)
	{
		JsonObject game = (JsonObject) new JsonParser().parse(json).getAsJsonObject();
		
		String title = game.get("title").getAsString();
		int id = game.get("id").getAsInt();
		String modelString = game.get("clientModel").getAsString();
		ClientModel clientModel = deserialize(modelString);
		
		Game g = new Game(clientModel,title,id);
		
		return g;
	}
	
	public HexType determineHexType(String resourceType)
	{
		HexType result = null;
		switch (resourceType.toLowerCase()) {
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
		switch (resourceType.toLowerCase()) {
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
		switch (edgeDirection.toLowerCase()) {
			case "n":
			case "north":
				return result = EdgeDirection.North;
			case "ne":
			case "northeast":
				return result = EdgeDirection.NorthEast;
			case "nw":
			case "northwest":
				return result = EdgeDirection.NorthWest;
			case "s":
			case "south":
				return result = EdgeDirection.South;
			case "se":
			case "southeast":
				return result = EdgeDirection.SouthEast;
			case "sw":
			case "southwest":
				return result = EdgeDirection.SouthWest;
		}
		return result;
	}	
	
	public VertexDirection determineVertexDirection (String vertexDirection)
	{
		VertexDirection result = null;
		switch (vertexDirection.toLowerCase()) {
			case "w":
			case "west":
				return result = VertexDirection.West;
			case "nw":
			case "northwest":
				return result = VertexDirection.NorthWest;
			case "ne":
			case "northeast":
				return result = VertexDirection.NorthEast;
			case "e":
			case "east":
				return result = VertexDirection.East;
			case "se":
			case "southeast":
				return result = VertexDirection.SouthEast;
			case "sw":
			case "southwest":
				return result = VertexDirection.SouthWest;
		}
		return result;
	}
	
	public CatanColor determineCatanColor (String color)
	{
		color = color.toUpperCase();
		return CatanColor.valueOf(color);
	}
	
	public PlayerInfo deserializePlayerInfo(String jsonString)
	{
		JsonObject player = (JsonObject) new JsonParser().parse(jsonString).getAsJsonObject();
		
		String name = player.get("name").getAsString();
		int playerID = player.get("playerID").getAsInt();
		PlayerInfo p = new PlayerInfo(name,playerID);
		
		return p;
	}
	
	public GameInfo deserializeGameInfo(String jsonString)
	{
		JsonObject game = (JsonObject) new JsonParser().parse(jsonString).getAsJsonObject();
		
		String title = game.get("title").getAsString();
		int gameID = game.get("id").getAsInt();
		GameInfo g = new GameInfo(title,gameID);
		JsonArray players = game.get("players").getAsJsonArray();
		for (int j = 0; j < players.size(); j++) 
		{
			JsonObject player = (JsonObject) players.get(j);
			if(!player.toString().equals("{}"))
			{
				String color = player.get("color").getAsString();
				CatanColor cc = determineCatanColor(color);
				String name = player.get("name").getAsString();
				int playerID = player.get("id").getAsInt();
				PlayerInfo p = new PlayerInfo(name,playerID,cc);
				g.addPlayer(p);
			}
		}
		
		return g;
	}
	
	public GameInfo[] deserializeGameInfoList(String jsonString)
	{
		JsonArray games = (JsonArray) new JsonParser().parse(jsonString);
		if(games.isJsonArray())
		{ 
			GameInfo[] gamesArray = new GameInfo[games.size()];
			for (int i = 0; i < games.size(); i++) 
			{
				  JsonObject game = (JsonObject) games.get(i);
				  String title = game.get("title").getAsString();
				  int gameID = game.get("id").getAsInt();
				  GameInfo g = new GameInfo(title,gameID);
				  JsonArray players = game.get("players").getAsJsonArray();
				  for (int j = 0; j < players.size(); j++) 
				  {
					  JsonObject player = (JsonObject) players.get(j);
					  if(!player.toString().equals("{}"))
					  {
						  String color = player.get("color").getAsString();
						  CatanColor cc = determineCatanColor(color);
						  String name = player.get("name").getAsString();
						  int playerID = player.get("id").getAsInt();
						  PlayerInfo p = new PlayerInfo(name,playerID,cc);
						  g.addPlayer(p);
					  }
				  }
				  gamesArray[i] = g;
			}
			return gamesArray;
		}
		return null;
	}
	
	public ClientModel deserialize(String jsonString) throws JsonParseException
	{
		return deserialize(new JsonParser().parse(jsonString).getAsJsonObject());
	}

	public ClientModel deserialize(JsonElement arg0) throws JsonParseException
	{	
		JsonObject mainJson = (JsonObject) arg0;
		ClientModel newClientModel = new ClientModel();
		
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
            JsonObject hexJson = hexesJson.get(a).getAsJsonObject();
            
            if (hexJson.has("resource"))
            {
                JsonElement resource = hexJson.get("resource");
                
                JsonObject locationJson = hexJson.getAsJsonObject("location");
                int x = locationJson.get("x").getAsInt();
                int y = locationJson.get("y").getAsInt();
                HexLocation hexLocation = new HexLocation(x, y);
                int number = hexJson.get("number").getAsInt();
                hexes[a] = new Hex(hexLocation, determineHexType(resource.getAsString()), number);
            }
            else
            {
                JsonObject locationJson = hexJson.getAsJsonObject("location");
                int x = locationJson.get("x").getAsInt();
                int y = locationJson.get("y").getAsInt();
                HexLocation hexLocation = new HexLocation(x, y);
                hexes[a] = new Hex(hexLocation);
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
			
			
			if(playersJson.get(b).isJsonNull())
				break;
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
	
	// Get TradeOffer	
		if (mainJson.has("tradeOffer"))
		{
			System.out.println("Interpreter: Grabbing Trade Offer");
			TradeOffer tradeOffer = new TradeOffer();
			ResourceCards offer = new ResourceCards();
			JsonObject tradeOfferJson = mainJson.getAsJsonObject("tradeOffer");
			
			JsonElement senderElement = tradeOfferJson.get("sender");
			JsonElement receiverElement = tradeOfferJson.get("receiver");
			
			int sender = senderElement.getAsInt();
			int receiver = receiverElement.getAsInt();
			
			JsonObject offerJson = tradeOfferJson.getAsJsonObject("offer");
			offer.setBrick(offerJson.get("brick").getAsInt());
			offer.setWood(offerJson.get("wood").getAsInt());
			offer.setSheep(offerJson.get("sheep").getAsInt());
			offer.setWheat(offerJson.get("wheat").getAsInt());
			offer.setOre(offerJson.get("ore").getAsInt());
			
			tradeOffer.setSender(sender);
			tradeOffer.setReceiver(receiver);
			tradeOffer.setOffer(offer);			
			
			System.out.println("Interpreter: Offer currently is....");
			System.out.println(offer.toString());
			
			newClientModel.setTradeOffer(tradeOffer);
		}
		
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
	
	public JsonElement serialize(ClientModel model)
	{
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonObject result = new JsonObject();
		
		result.add("deck",parser.parse(model.getDeck().toJsonString()));
		
		JsonObject map = new JsonObject();
		
		JsonElement hexes = gson.toJsonTree(model.getMap().getHexes());
		map.add("hexes", hexes);
		
		JsonArray roads = new JsonArray();
		for (Entry<EdgeLocation, Player> entry : model.getMap().getRoads().entrySet()) //find this person's settlements
		{
			JsonObject road = new JsonObject();
			JsonObject location = new JsonObject();
			location.addProperty("direction",entry.getKey().toServerFormattedString());
			location.addProperty("x",entry.getKey().getHexLoc().getX());
			location.addProperty("y",entry.getKey().getHexLoc().getY());
		    road.add("owner", new JsonPrimitive(entry.getValue().getIndex()));
		    road.add("location", location);
		    roads.add(road);
		}
		map.add("roads", roads);
		
		JsonArray cities = new JsonArray();
		for (Entry<VertexLocation, Player> entry : model.getMap().getCities().entrySet()) //find this person's settlements
		{
			JsonObject city = new JsonObject();
			JsonObject location = new JsonObject();
			location.addProperty("direction",entry.getKey().toServerFormattedString());
			location.addProperty("x",entry.getKey().getHexLoc().getX());
			location.addProperty("y",entry.getKey().getHexLoc().getY());
			city.add("owner", new JsonPrimitive(entry.getValue().getIndex()));
			city.add("location", location);
		    cities.add(city);
		}
		map.add("cities", cities);
		
		JsonArray settlements = new JsonArray();
		for (Entry<VertexLocation, Player> entry : model.getMap().getSettlements().entrySet()) //find this person's settlements
		{
			JsonObject settlement = new JsonObject();
			JsonObject location = new JsonObject();
			location.addProperty("direction",entry.getKey().toServerFormattedString());
			location.addProperty("x",entry.getKey().getHexLoc().getX());
			location.addProperty("y",entry.getKey().getHexLoc().getY());
			settlement.add("owner", new JsonPrimitive(entry.getValue().getIndex()));
			settlement.add("location", location);
			settlements.add(settlement);
		}
		map.add("settlements", settlements);
		
		map.addProperty("radius", model.getMap().getRadius());
		
		JsonArray ports = new JsonArray();
		for(Port p :model.getMap().getPorts())
		{
			JsonObject port = new JsonObject();
			port.addProperty("ratio", p.getRatio());
			if(p.getResourceType()!=null)
			{
				port.addProperty("resource", p.getResourceType().toString());
			}
			port.addProperty("direction", p.getDirection().toString());
			JsonObject location = new JsonObject();
			location.addProperty("x",p.getLocation().getX());
			location.addProperty("y",p.getLocation().getY());
			port.add("location", location);
			ports.add(port);
		}
		map.add("ports", ports);
		
		JsonObject robber = new JsonObject();
		robber.addProperty("x",model.getMap().getRobber().getX());
		robber.addProperty("y",model.getMap().getRobber().getY());
		map.add("robber", robber);
		
		result.add("map", map);
		
		JsonArray players = new JsonArray();
		for(Player p:model.getPlayers())
		{
			JsonObject player = new JsonObject();
			if(p == null)
			{
				continue;
			}
			player.add("resources", parser.parse(p.getResources().toJsonString()));
			player.add("oldDevCards", parser.parse(p.getOldDevCards().toJsonString()));
			player.add("newDevCards", parser.parse(p.getNewDevCards().toJsonString()));
			player.addProperty("roads", p.getRoads());
			player.addProperty("cities", p.getCities());
			player.addProperty("settlements", p.getSettlements());
			player.addProperty("soldiers", p.getSoldiers());
			player.addProperty("victoryPoints", p.getVictoryPoints());
			player.addProperty("monuments", p.getMonuments());
			player.addProperty("playedDevCard", p.isPlayedDevCard());
			player.addProperty("discarded", p.isDiscarded());
			player.addProperty("playerID", p.getPlayerID());
			player.addProperty("playerIndex", p.getIndex());
			player.addProperty("name", p.getName());
			player.addProperty("color", p.getColor().toLowerCase());
			
			players.add(player);
		}
		result.add("players", players);
		
		JsonArray lines = new JsonArray();
		for(MessageLine m : model.getLog().getLines())
		{
			JsonObject message = new JsonObject();
			message.addProperty("source", m.getSource());
			message.addProperty("message", m.getMessage());
			lines.add(message);
		}
		JsonObject log = new JsonObject();
		log.add("lines", lines);
		
		result.add("log", log);
		
		JsonArray lines2 = new JsonArray();
		for(MessageLine m : model.getChat().getLines())
		{
			JsonObject message = new JsonObject();
			message.addProperty("source", m.getSource());
			message.addProperty("message", m.getMessage());
			lines2.add(message);
		}
		JsonObject chat = new JsonObject();
		chat.add("lines", lines2);
		
		result.add("chat", chat);
		
		result.add("bank", parser.parse(model.getBank().toJsonString()));
		
		JsonObject turnTracker = new JsonObject();
		turnTracker.addProperty("status", model.getTurnTracker().getStatus());
		turnTracker.addProperty("currentTurn", model.getTurnTracker().getCurrentTurn());
		turnTracker.addProperty("longestRoad", model.getTurnTracker().getLongestRoad());
		turnTracker.addProperty("largestArmy", model.getTurnTracker().getLargestArmy());
		
		result.add("turnTracker", turnTracker);
		
		if(model.getTradeOffer()!=null)
		{
			JsonObject tradeOffer = new JsonObject();
			tradeOffer.addProperty("sender", model.getTradeOffer().getSender());
			tradeOffer.addProperty("receiver", model.getTradeOffer().getReceiver());
			tradeOffer.add("offer", parser.parse(model.getTradeOffer().getOffer().toJsonString()));
			result.add("tradeOffer", tradeOffer);
		}
		
		result.addProperty("winner", model.getWinner());
		result.addProperty("version", model.getVersion());
		
		String s = result.toString();
		
		return parser.parse(s);
	}
	
	public Map deserializeDefaultMap(JsonElement arg0) throws JsonParseException {

		// Get Map	
		JsonObject mapJson = (JsonObject) arg0;
		
		//Get Hexes
        JsonArray hexesJson = mapJson.getAsJsonArray("hexes");
        Hex[] hexes = new Hex[19];
        
        for(int a = 0; a < 19; a++)
        {
            JsonObject hexJson = hexesJson.get(a).getAsJsonObject();
            
            if (hexJson.has("resource"))
            {
                JsonElement resource = hexJson.get("resource");
                
                JsonObject locationJson = hexJson.getAsJsonObject("location");
                int x = locationJson.get("x").getAsInt();
                int y = locationJson.get("y").getAsInt();
                HexLocation hexLocation = new HexLocation(x, y);
                int number = hexJson.get("number").getAsInt();
                hexes[a] = new Hex(hexLocation, determineHexType(resource.getAsString()), number);
            }
            else
            {
                JsonObject locationJson = hexJson.getAsJsonObject("location");
                int x = locationJson.get("x").getAsInt();
                int y = locationJson.get("y").getAsInt();
                HexLocation hexLocation = new HexLocation(x, y);
                hexes[a] = new Hex(hexLocation);
            }
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
		
		HashMap<VertexLocation, Player> settlements = new HashMap<>();
		HashMap<VertexLocation, Player> cities = new HashMap<>();
		HashMap<EdgeLocation, Player> roads = new HashMap<>();
		
		Map map = new Map(hexes, ports, roads, settlements, cities, radius, robber);
		
		return map;
	}
	
}
	
	