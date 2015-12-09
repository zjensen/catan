package server.command;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import server.persistence.provider.IProvider;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.utils.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.Headers;

public abstract class ServerCommand implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ExchangeWrapper httpObj;
	protected int gameId;
	protected int playerId;
	public Gson gson = new Gson();
	protected String json;
	protected boolean hasUserCookie = false;
	protected boolean hasGameCookie = false;
	
	public ServerCommand(ExchangeWrapper exchange)
	{	
		httpObj = exchange;
		if (exchange.getExchange() == null){
			json = exchange.getJsonString();
			return;
		}
		httpObj.getExchange().getRequestMethod();
//		System.out.println("Request: " + httpObj.getRequestURI());
		
		//Parse request body
		try 
		{
			json = StringUtils.getString(httpObj.getExchange().getRequestBody());
		} 
		catch (Exception e) 
		{
			json = "";
		}
		
		Headers headers = httpObj.getExchange().getRequestHeaders();
		List<String> cookies = headers.get("Cookie");
		if (cookies == null)// No nomnoms
		{
			return;
		}
			
		String catanCookie = cookies.get(cookies.size()-1);
		try 
		{
			parseCookie(catanCookie);
		} 
		catch (UnsupportedEncodingException e1) 
		{
			e1.printStackTrace();
		} 
		catch (NumberFormatException e2)
		{
			e2.printStackTrace();
		}		
	}
	
	public void addCommand()
	{
		if(ServerManager.instance().getProvider()==null)
			return;
		IProvider p = ServerManager.instance().getProvider();
		p.startTransaction();
		try
		{
			p.addCommand(serialize(), this.gameId);
			p.endTransaction(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			p.endTransaction(false);
		}
	}

	/**
	 * Action to execute. Override this method
	 */
	public abstract JsonElement execute() throws ServerInvalidRequestException;
	
	public abstract JsonElement execute(String json) throws ServerInvalidRequestException;
	
	private void parseCookie(String cookie) throws UnsupportedEncodingException {
		String[] parameters = cookie.split(";");
		
		for (String string : parameters) {
			if (string.contains("catan.user")) {
				String decoded = URLDecoder.decode(string, "UTF-8");
				String finalChunk = decoded.substring(decoded.indexOf("playerID"));
				String id = finalChunk.substring(finalChunk.indexOf(":") + 1, finalChunk.indexOf("}"));
				//System.out.println("PlayerIndex : " + id);
				this.playerId = Integer.parseInt(id);
				this.hasUserCookie = true;
			} else if (string.contains("catan.game")) {
				String decoded = URLDecoder.decode(string, "UTF-8");
				//System.out.println("Decoded gameID: " + decoded);
				String id = decoded.substring(decoded.indexOf("=") + 1);
				id = id.replace("~Path=/~", "");
				//System.out.println("GameID: " + id);
				if(id!=null && !id.equals("") && !id.equals("null")){
					//System.out.println("Game ID not null");
					this.gameId = Integer.parseInt(id);
					this.hasGameCookie = true;
				}
			}
		}
	}
	
	
	/**
	 * Name pretty much says it all. Creates an encoded cookie for us, the required fields are in the paramaters
	 * @param name
	 * @param password
	 * @param playerID
	 * @return a string representation of the encoded login cookie
	 * @throws UnsupportedEncodingException
	 */
	protected String getEncodedLoginCookie(String name, String password, String playerID) throws UnsupportedEncodingException{
		String plaintext = "{\"name\":\"" + name + "\",\"password\":\"" + password + "\",\"playerID\":" + playerID + "}";
		String encoded = URLEncoder.encode(plaintext, "UTF-8");
		encoded = "catan.user=" + encoded + ";Path=/;";
		return encoded;
	}
	
	protected String getEncodedJoinGameCookie(String gameID){
		return "catan.game=" + gameID + ";Path=/;";
	}
	
	protected String getExampleListString(){
		return "[\n\t{\n\t}\n]";
	}
	
	protected VertexLocation extractVertexLocation(JsonObject v)
	{
		int x = v.get("x").getAsInt();
		int y = v.get("y").getAsInt();
		String dir = v.get("direction").getAsString();
		dir = convertDirection(dir);
		VertexLocation result = new VertexLocation(new HexLocation(x, y), VertexDirection.valueOf(dir));
		return result;
	}
	
	protected EdgeLocation extractEdgeLocation(JsonObject e)
	{
		int x = e.get("x").getAsInt();
		int y = e.get("y").getAsInt();
		String dir = e.get("direction").getAsString();
		dir = convertDirection(dir);
		EdgeLocation result = new EdgeLocation(new HexLocation(x, y), EdgeDirection.valueOf(dir));
		return result;
	}
	
	private String convertDirection(String dir)
	{
		switch(dir.toLowerCase())
		{
			case "n":
				return "North";
			case "ne":
				return "NorthEast";
			case "e":
				return "East";
			case "se":
				return "SouthEast";
			case "s":
				return "South";
			case "sw":
				return "SouthWest";
			case "w":
				return "West";
			case "nw":
				return "NorthWest";
		}
		return null;
	}
	
	public void setGameID (int gameId){
		this.gameId = gameId;
		this.hasGameCookie = true;
	}
	
	public void setPlayerID(int playerId)
	{
		this.playerId = playerId;
		this.hasUserCookie = true;
	}

	public void test()
	{
		this.httpObj = null;
		this.gson = null;
		
		 String serializedObject = "";

		 try {
			 //serialize
		     ByteArrayOutputStream bo = new ByteArrayOutputStream();
		     ObjectOutputStream so = new ObjectOutputStream(bo);
		     so.writeObject(this);
		     so.flush();
		     serializedObject = bo.toString("ISO-8859-1");
		    
		     //deserialize
		     byte b[] = serializedObject.getBytes("ISO-8859-1");
		     ByteArrayInputStream bi = new ByteArrayInputStream(b);
		     ObjectInputStream si = new ObjectInputStream(bi);
		     ServerCommand obj = (ServerCommand) si.readObject();
		     obj.gson = new Gson();
		 } catch (Exception e) {
		     e.printStackTrace();
		 }
	}
	
	public String serialize()
	{
		this.httpObj = null;
		this.gson = null;
		
		 String serializedObject = "";

		 // serialize the object
		 try {
		     ByteArrayOutputStream bo = new ByteArrayOutputStream();
		     ObjectOutputStream so = new ObjectOutputStream(bo);
		     so.writeObject(this);
		     so.flush();
		     so.close();
		     serializedObject = bo.toString("ISO-8859-1");
		 } catch (Exception e) {
		     e.printStackTrace();
		 }
		 return serializedObject;
	}
	
	public void deserialize(String s)
	{
		 // deserialize the object
		 try {
			 byte b[] = s.getBytes("ISO-8859-1");
		     ByteArrayInputStream bi = new ByteArrayInputStream(b);
		     ObjectInputStream si = new ObjectInputStream(bi);
		     ServerCommand obj = (ServerCommand) si.readObject();
		     obj.gson = new Gson();
		 } catch (Exception e) {
		     e.printStackTrace();
		 }
	}

}
