package server.handler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import server.command.CommandFactory;
import server.command.ICommandFactory;
import server.command.MockCommandFactory;
import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;

public class ServerHandler implements HttpHandler{

	ICommandFactory factory;
	private Logger logger = null;
	
	public ServerHandler(boolean testing)
	{
		if(testing)
			factory = new MockCommandFactory();
		else
			factory = new CommandFactory();
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		ServerCommand event = factory.create(exchange);
		String uri = exchange.getRequestURI().toString();
		String[] arguments = uri.split("/");
		String request = arguments[arguments.length-1];
		if(!request.startsWith("model"))
			this.logInfo("Request: " + exchange.getRequestURI().toString());
		try{
			//Check if it is a valid request
			if (event == null) {
				throw new ServerInvalidRequestException("Invalid request");
			}
			JsonElement response = event.execute();
			//Check the type of response
			if (response.getClass() == JsonPrimitive.class)
				exchange.getResponseHeaders().add("Content-Type", "application/text");
			else
				exchange.getResponseHeaders().add("Content-Type", "application/json");
			//Set up Body
			String stringResponse = response.toString();
			if(stringResponse.equals("\"Success\""))
				stringResponse = "Success";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, stringResponse.length());
			exchange.getResponseBody().write(stringResponse.getBytes());
			exchange.getResponseBody().close();
			exchange.close();
		} catch(ServerInvalidRequestException e1){
			e1.printStackTrace();
			String errorMessage = e1.getMessage();
			this.logError("Bad request " + errorMessage);
			exchange.getResponseHeaders().add("Content-Type", "application/text");
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(errorMessage.getBytes());
			exchange.getResponseBody().close();
			exchange.close();
		} catch(Exception e2){
			e2.printStackTrace();
			String errorMessage = e2.getMessage();
			this.logError("Bad Gateway " + errorMessage);
			exchange.getResponseHeaders().add("Content-Type", "application/text");
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_GATEWAY, 0);
			exchange.getResponseBody().write(errorMessage.getBytes());
			exchange.getResponseBody().close();
			exchange.close();
		}
	}
	
	public void setLogger(Logger l){
		logger = l;
	}
	
	private void logInfo(String s){
		if(logger!=null){
			logger.info(s);
		}
		else{
			System.out.println("WARNING: Logger never intialized");
		}
	}
	
	private void logError(String s){
		if(logger!=null){
			logger.log(Level.SEVERE, s);
		}
		else{
			System.out.println("WARNING: Logger never intialized");
		}
	}

}
