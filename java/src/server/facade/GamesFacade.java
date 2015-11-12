package server.facade;

import com.google.gson.*;

import server.manager.ServerManager;

import java.util.ArrayList;
import java.util.List;

import shared.communication.games.CreateGame_Input;
import shared.communication.games.JoinGame_Input;
import shared.models.Game;
import shared.models.Player;

//make all methods static
public class GamesFacade implements IGamesFacade {

	/**
	 * Handles a create game request
	 * @param params
	 * @return A new game created with the info specified in the parameters, otherwise an error message in JSON
	 */
	@Override
	public JsonElement create(CreateGame_Input params) {

		int gameID = ServerManager.instance().getGamesManager().addNewGameGetID(params.getName(), params.isRandomTiles(),
				params.isRandomPorts(), params.isRandomNumbers());
		Game newGame = ServerManager.instance().getGamesManager().getGameById(gameID);
        JsonObject gameJSON = (JsonObject) gameToJson(newGame.getId(), newGame.getTitle(), newGame.getPlayerList());
		return gameJSON;
	}
	
	/**
	 * Handles the request to join a game
	 * @param params
	 * @return "SUCCESS" if successful otherwise an error message in JSON
	 */
	@Override
	public JsonElement join(JoinGame_Input params) {
		
		params.toString();
		return null;
	}
	
	/**
	 * Handles a request to see the current players of the game
	 * @return JSON representation of the game. Game Title, ID and players participating inclusive
	 */
	@Override
	public JsonElement list() {
		ArrayList<Game> games = getGamesList();
        JsonArray results = new JsonArray();
        for (int i = 0; i < games.size(); i++) {
        	results.add(gameToJson(games.get(i).getId(), games.get(i).getTitle(), games.get(i).getPlayerList()));
        }
        return results;
	}
	
	private ArrayList<Game> getGamesList() {
		return (ArrayList<Game>)ServerManager.instance().getGamesManager().getGames();
	}
	
	private JsonElement gameToJson(int id, String title, ArrayList<Player> playersArray) {
        JsonObject gameJSON = new JsonObject();
        gameJSON.addProperty("id", id);
        gameJSON.addProperty("title", title);
        
        JsonArray playersJSON = new JsonArray();
        Gson gson = new Gson();
        
        for (int i = 0; i < 4; i++) {
            if (i >= playersArray.size()) {
                playersJSON.add(new JsonObject());
            } else {
                Player player = playersArray.get(i);
                String json = gson.toJson(player);
                JsonParser parser = new JsonParser();
                playersJSON.add(parser.parse(json));
            }
        }
        
        gameJSON.add("players", playersJSON);
        
        return gameJSON;
    }
}
