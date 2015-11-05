package server.command;

import com.google.gson.JsonElement;

public interface ICommand {
	
	/**
	 * Executes command
	 * @return JSON element of response
	 */
	public abstract JsonElement execute();

}
