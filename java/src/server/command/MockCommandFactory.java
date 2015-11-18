package server.command;

import com.sun.net.httpserver.HttpExchange;

public class MockCommandFactory implements ICommandFactory {

	@Override
	public ServerCommand create(HttpExchange arg0) 
	{
		return new MockCommand(new ExchangeWrapper(arg0));
	}

}
