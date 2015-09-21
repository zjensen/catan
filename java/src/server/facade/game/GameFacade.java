package server.facade.game;

import shared.communication.game.AddAI_Input;
import shared.communication.game.AddAI_Output;
import shared.communication.game.GETCommands_Output;
import shared.communication.game.GameModel_Input;
import shared.communication.game.GameModel_Output;
import shared.communication.game.GetCommands_Input;
import shared.communication.game.ListAI_Input;
import shared.communication.game.ListAI_Output;
import shared.communication.game.POSTCommands_Input;
import shared.communication.game.POSTCommands_Output;
import shared.communication.game.ResetGame_Input;
import shared.communication.game.ResetGame_Output;

public class GameFacade {

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
}
