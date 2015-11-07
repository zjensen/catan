package server.main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import server.handler.ServerHandler;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server 
{
	private static int SERVER_PORT_NUMBER = 8081;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	
	private static Logger logger;
	
	static 
	{
		try 
		{
			initLog();
		}
		catch (IOException e) 
		{
			System.out.println("Could not initialize log: " + e.getMessage());
		}
	}
	
	private static void initLog() throws IOException {
		
		Level logLevel = Level.FINE;
		
		logger = Logger.getLogger("server"); 
		logger.setLevel(logLevel);
		logger.setUseParentHandlers(false);
		
		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(logLevel);
		consoleHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(consoleHandler);

		FileHandler fileHandler = new FileHandler("log.txt", false);
		fileHandler.setLevel(logLevel);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
	}
	
private HttpServer server;
	
	private Server() 
	{
		return;
	}
	private Server(int port)
	{
		Server.SERVER_PORT_NUMBER = port;
	}
	private void run() 
	{
		logger.info("Initializing HTTP Server");
		
		try {
			server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER),MAX_WAITING_CONNECTIONS);
		} 
		catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);			
			return;
		}

		server.setExecutor(null); // use the default executor
		
		server.createContext("/", handler);
		
		logger.info("Starting HTTP Server");

		server.start();
	}

	//start a server with a real command factory
	// TODO set up a way to switch out factories
	private HttpHandler handler = new ServerHandler(true);
	
	public static void main(String[] args) 
	{
		if(args.length != 1)
		{
			new Server().run();
		}
		else
		{
			new Server(Integer.valueOf(args[0])).run();
		}
	}
}
