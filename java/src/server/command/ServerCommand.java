package server.command;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import server.main.ServerInvalidRequestException;
import shared.utils.StringUtils;

public abstract class ServerCommand {
	
	protected HttpExchange httpObj;
	protected int gameId;
	protected int playerId;
	protected Gson gson = new Gson();
	protected String json;
	
	public ServerCommand(HttpExchange exchange)
	{
		
		httpObj = exchange;
		httpObj.getRequestMethod();
//		System.out.println("Request: " + httpObj.getRequestURI());
		
		//Parse request body
		try 
		{
			json = StringUtils.getString(httpObj.getRequestBody());
		} 
		catch (Exception e) 
		{
			json = "";
		}
		
		Headers headers = httpObj.getRequestHeaders();
		List<String> cookies = headers.get("Cookie");
		if (cookies == null) // No nomnoms
			return;
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
			} else if (string.contains("catan.game")) {
				String decoded = URLDecoder.decode(string, "UTF-8");
				//System.out.println("Decoded gameID: " + decoded);
				String id = decoded.substring(decoded.indexOf("=") + 1);
				id = id.replace("~Path=/~", "");
				//System.out.println("GameID: " + id);
				if(id!=null && !id.equals("") && !id.equals("null")){
					//System.out.println("Game ID not null");
					this.gameId = Integer.parseInt(id);
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
		String output = "[\n\t{\n\t}\n]";
		return output;
	}
	
	public void setGameID (int gameId){
		this.gameId = gameId;
	}

}
