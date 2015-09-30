package client.interpreter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.gson.*;

import shared.definitions.HexType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.models.*;


public class Interpreter {

	GsonBuilder gsonBuilder = new GsonBuilder();
	Gson gson;
	
	
	public Interpreter() {	
		gson = new Gson();
		
		gson = gsonBuilder.create();
	}
	

	JsonDeserializer<?> BankDeserialize = new JsonDeserializer<ResourceCards>()
	{
		@Override
		public ResourceCards deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
			
			System.out.println("ClientModel Conversion");
			ResourceCards bank = new ResourceCards();
			DevCards deck = new DevCards();
			MessageList chat = new MessageList();
			MessageList log = new MessageList();
			Map map = new Map(null, null, null, null, null, 0, null);
			Player[] players = new Player[4];
			Hex[] hexes = new Hex[19];
			HashMap<EdgeLocation, Player> roads;
			ArrayList<EdgeLocation> tempEdgeLocationRoads = new ArrayList<>();
			HashMap<VertexLocation, Player> settlements;
			ArrayList<VertexLocation> tempVertexLocationSettlements = new ArrayList<>();
			HashMap<VertexLocation, Player> cities;
			ArrayList<VertexLocation> tempVertexLocationCities = new ArrayList<>();
			Port[] ports = new Port[9];
			TradeOffer tradeoffer = new TradeOffer();
			TurnTracker turntracker = new TurnTracker();
			int version = 0;
			int winner = 0;

			// 
			JsonObject json1 = (JsonObject) arg0;
			JsonObject json2 = json1.getAsJsonObject("deck");


			deck.setYearOfPlenty(json2.get("yearOfPlenty").getAsInt());
			deck.setMonopoly(json2.get("monopoly").getAsInt());
			deck.setSoldier(json2.get("soldier").getAsInt());
			deck.setRoadBuilding(json2.get("roadBuilding").getAsInt());
			deck.setMonument(json2.get("monument").getAsInt());
			
			JsonObject json3 = json1.getAsJsonObject("map");
			JsonArray array3_1 = json3.getAsJsonArray("hexes");
			
			
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
			
			for(int b = 0; b < array4_1.size(); b++)
			{
				
					JsonObject json4_1_1 = array4_1.get(b).getAsJsonObject();
					int owner = json4_1_1.get("owner").getAsInt();
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
			
			for(int b = 0; b < array5_1.size(); b++)
			{
				
					JsonObject json5_1_1 = array5_1.get(b).getAsJsonObject();
					int owner = json5_1_1.get("owner").getAsInt();
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
			
			for(int b = 0; b < array6_1.size(); b++)
			{
				
					JsonObject json6_1_1 = array6_1.get(b).getAsJsonObject();
					int owner = json6_1_1.get("owner").getAsInt();
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
			
			
			System.out.println("----AFTER----\n");
			System.out.println(bank.toString());
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
			
		
			return bank;
		}

	};
	
//	JsonObject json2 = json1.getAsJsonObject("bank");
//	bank.setBrick(json2.get("brick").getAsInt());
//	bank.setWood(json2.get("wood").getAsInt());
//	bank.setSheep(json2.get("sheep").getAsInt());
//	bank.setWheat(json2.get("wheat").getAsInt());
//	bank.setOre(json2.get("ore").getAsInt());
	
}

