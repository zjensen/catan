package server.command.user;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;

import server.command.ServerCommand;
import server.facade.UserFacade;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import shared.communication.user.Register_Input;

public class Register_Command extends ServerCommand {

	private Register_Input params = null;
	
	/**
	 * Command object for registering a new user
	 * @param json
	 */
	public Register_Command(HttpExchange exchange)
	{
		super(exchange);
		//here we will deserialize the JSON into a Register_Input object
	}

	@Override
	public JsonElement execute() throws ServerInvalidRequestException
	{
		Register_Input params = gson.fromJson(json, Register_Input.class);
		try 
		{
			JsonElement response = ServerManager.instance().getUserFacade().register(params);
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
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		// TODO Auto-generated method stub
		return null;
	}

}
