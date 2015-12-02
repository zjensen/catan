package server.command.user;

import server.command.ExchangeWrapper;
import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import server.persistence.provider.IProvider;
import shared.communication.user.Register_Input;
import shared.models.User;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class Register_Command extends ServerCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3822300854313482942L;
	private Register_Input params = null;
	
	/**
	 * Command object for registering a new user
	 * @param json
	 */
	public Register_Command(ExchangeWrapper exchange)
	{
		super(exchange);
		//here we will deserialize the JSON into a Register_Input object
	}

	@Override
	public JsonElement execute() throws ServerInvalidRequestException
	{
		params = gson.fromJson(json, Register_Input.class);
		try 
		{
			JsonElement response = ServerManager.instance().getUserFacade().register(params);
			JsonObject responseObject = response.getAsJsonObject();
			
			if (responseObject.has("id")) 
			{
				if (httpObj.getExchange() == null)
					return new JsonPrimitive("Success");
				int id = responseObject.get("id").getAsInt();
				String encoded = getEncodedLoginCookie(params.getUsername(), params.getPassword(), Integer.toString(id));
				httpObj.getExchange().getResponseHeaders().add("Set-cookie", encoded);
				addUser(params);
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
	
	public void addUser(Register_Input params)
	{
		if(ServerManager.instance().getProvider()==null)
			return;
		IProvider p = ServerManager.instance().getProvider();
		p.startTransaction();
		try
		{
			int userID = ServerManager.instance().getUsersManager().getNewestUserID();
			p.addUser(new User(params.getUsername(),params.getPassword(),userID));
			p.endTransaction(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			p.endTransaction(false);
		}
	}

}
