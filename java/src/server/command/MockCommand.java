package server.command;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.main.ServerInvalidRequestException;

public class MockCommand extends ServerCommand {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1830040186671755302L;
	boolean list = false;

	public MockCommand(ExchangeWrapper arg0) 
	{
		super(arg0);
		String uri = arg0.getExchange().getRequestURI().toString();
		if(uri.equals("/user/register")||uri.equalsIgnoreCase("/user/login"))
		{
			//include login cookie in JSON
			try
			{
				String encoded = getEncodedLoginCookie("Davis", "davis", "0");
				arg0.getExchange().getResponseHeaders().add("Set-cookie", encoded);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else if(uri.equals("games/list"))
		{
			list = true;
		}
		else if(uri.equals("/games/join"))
		{
			//include join game cookie in JSON
			try
			{
				String encoded = this.getEncodedJoinGameCookie("1");
				arg0.getExchange().getResponseHeaders().add("Set-cookie", encoded);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public JsonElement execute() throws ServerInvalidRequestException 
	{
		JsonElement output;
		Gson gson = new Gson();
		output = gson.fromJson("test", JsonElement.class);
		if(list)
			output = gson.fromJson(this.getExampleListString(), JsonElement.class);
		return output;
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
