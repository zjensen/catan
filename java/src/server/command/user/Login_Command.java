package server.command.user;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;

import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.user.Login_Input;

public class Login_Command extends ServerCommand{
	
	private Login_Input params = null;
	
	/**
	 * Command object for logging in
	 * @param exchange
	 */
	public Login_Command(HttpExchange exchange)
	{
		super(exchange);
	}

	@Override
	public JsonElement execute() throws ServerInvalidRequestException
	{
		Login_Input params = gson.fromJson(json, Login_Input.class);
		try 
		{
			JsonElement response = ServerManager.instance().getUserFacade().login(params);
			JsonObject responseObject = response.getAsJsonObject();
			
			if (responseObject.has("id")) 
			{
				int id = responseObject.get("id").getAsInt();
				String encoded = getEncodedLoginCookie(params.getUsername(), params.getPassword(), Integer.toString(id));
				httpObj.getResponseHeaders().add("Set-cookie", encoded);
				return new JsonPrimitive("Success");
			} 
			else 
			{
				throw new ServerInvalidRequestException("Internal Error");
			}
			
		} 
		catch (ServerInvalidRequestException e) 
		{
			throw e;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new ServerInvalidRequestException("Internal Error");
		}
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException 
	{
		return null;
	}
}
