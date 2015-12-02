package server.command;

import java.io.Serializable;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

public class ExchangeWrapper implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3674216438370104287L;
	private HttpExchange exchange;
	private JsonElement json;
	private String jsonString;
	
	/**
	 * Wrapper class that holds http exchange. If no http exchange is provided,
	 * a default json can be added to simulate the json provided by the http
	 * exchange object. To do this, simply pass null to the constructor.
	 * @param exchange
	 */
	public ExchangeWrapper(HttpExchange exchange)
	{
		this.setExchange(exchange);
	}

	public HttpExchange getExchange() {
		return exchange;
	}

	public void setExchange(HttpExchange exchange) {
		this.exchange = exchange;
	}
	
	public JsonElement getJson()
	{
		assert(exchange == null);
		return this.json;
	}
	
	public void setJson(JsonElement json)
	{
		this.json = json;
		this.setJsonString(json.toString());
	}

	public void setJsonString(String jsonStr)
	{
		this.jsonString = jsonStr;
	}
	
	public String getJsonString() {
		return this.jsonString;
	}
}
