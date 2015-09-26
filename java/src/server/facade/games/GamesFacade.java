package server.facade.games;

import shared.communication.games.*;

/**
 * 
 * GamesFacade class will manage the games and make requests to load the correct game
 *
 */
public class GamesFacade {

	
	/**
	 * Returns the list of games 
	 * 
	 * @param params
	 * @return ListGames_Output
	 */
	public ListGames_Output list(ListGames_Input params){
		return null;
	}
	
	/**
	 * Creates a new game
	 * 
	 * @param params
	 * @return
	 */
	public CreateGame_Output create(CreateGame_Input params){
		
		return null;
	}
	
	/**
	 * Joins a game that has was started by another player or a game 
	 * the player started
	 * 
	 * @param params
	 * @return JoinGame_Output
	 */
	public JoinGame_Output join(JoinGame_Input params){
		
		return null;
	}
	
	/**
	 * Saves the current game and all associated details about the game state 
	 * 
	 * @param params
	 * @return SaveGame_Output
	 */
	public SaveGame_Output save(SaveGame_Input params){
		return null;
	}
	
	/**
	 * Loads a game that was previously saved
	 * 
	 * @param params
	 * @return LoadGame_Output
	 */
	public LoadGame_Output load(LoadGame_Input params){
		
		return null;
	}
	
}
