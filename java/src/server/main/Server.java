package server.main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import server.handler.*;
import server.manager.ServerManager;

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
private Boolean testing;
	
	private Server(Boolean testing) 
	{
		this.testing = testing;
		return;
	}
	private Server(int port, Boolean testing)
	{
		Server.SERVER_PORT_NUMBER = port;
		this.testing = testing;
	}
	private void run() 
	{
		logger.info("Initializing Server Manager");
		if(testing)
			ServerManager.instance().setFakeFacades();
		else
		{
			ServerManager.instance().setFacades();
			ServerManager.instance().initializeProvider();
		}
		logger.info("Initializing HTTP Server");
		
		try {
			server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER),MAX_WAITING_CONNECTIONS);
		} 
		catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);			
			return;
		}

		server.setExecutor(null); // use the default executor
		
		
		//start a handler with a real command factory
		// TODO set up a way to switch out factories
		ServerHandler serverHandler = new ServerHandler(testing);
		server.createContext("/", serverHandler);
		serverHandler.setLogger(logger);
		server.createContext("/docs/api/data", new Handlers.JSONAppender(""));
		server.createContext("/docs/api/view", new Handlers.BasicFile(""));
		
		logger.info("Starting HTTP Server");

		server.start();
	}
	
	public static void main(String[] args) 
	{
		if(args.length != 1)
		{
			new Server(false).run();
		}
		else
		{
			new Server(Integer.valueOf(args[0]), false).run();
		}
	}
}
