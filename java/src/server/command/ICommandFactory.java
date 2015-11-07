package server.command;

import com.sun.net.httpserver.HttpExchange;

public interface ICommandFactory {
	ServerCommand create(HttpExchange arg0);
}
