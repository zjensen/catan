package server.facade.game;

import client.server.IServer;
import shared.communication.game.*;
import shared.models.ClientModel;

public class GameFacade {
	
	private ClientModel clientModel;
	private IServer server;
	/**
	 * Constructs a new GameFacade
	 * @param clientModel
	 */
	public GameFacade() {
		this.clientModel = null;
		this.server = null;
	}

	/**
	 * Returns the current state of the game in JSON format
	 * 
	 * @param params
	 * @return GameModel_Output (JSON data)
	 */
	public GameModel_Output model(GameModel_Input params){
		return null;
	}
	
	/**
	 * Clears out the command history of the current game
	 * 
	 * @param params
	 * @return ResetGame_Output
	 */
	public ResetGame_Output reset(ResetGame_Input params){
		return null;
	}
	
	/**
	 * Executes the specified command list in the current game
	 * 
	 * @param params
	 * @return POSTCommands_Output
	 */
	public POSTCommands_Output postCommands(POSTCommands_Input params){
		return null;
	}
	
	/**
	 * Returns a list of commands that have been executed in the current game
	 * 
	 * @param params
	 * @return GETCommands_Output
	 */
	public GETCommands_Output getCommands(GetCommands_Input params){
		return null;
	}
	
	/**
	 * Adds an AI player to the current game
	 * 
	 * @param params
	 * @return AddAI_Output
	 */
	public AddAI_Output addAI(AddAI_Input params){
		return null;
	}
	
	/**
	 * Returns a list of supported AI player types 
	 * (currently, LARGEST_ARMY is the only supported type)
	 * 
	 * @param params
	 * @return ListAI_Output
	 */
	public ListAI_Output listAI(ListAI_Input params){
		return null;
	}

	public ClientModel getClientModel() {
		return clientModel;
	}

	public void setClientModel(ClientModel clientModel) {
		this.clientModel = clientModel;
	}

	public IServer getServer() {
		return server;
	}

	public void setServer(IServer server) {
		this.server = server;
	}
}
