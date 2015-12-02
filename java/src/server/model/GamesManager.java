package server.model;

import java.util.ArrayList;
import java.util.List;
import shared.models.ClientModel;
import shared.models.Game;
import shared.models.Map;
import shared.models.Player;
import shared.models.User;

/**
 * This manager class stores all the games on the current server This class
 * provides the server access to these games This will only be accessed by the
 * ServerManager singleton class
 * 
 * @author Ian
 *
 */
public class GamesManager {
	
	private MapGenerator mapGenerator = new MapGenerator();
	
	/**
	 * A list of games stored on the server
	 */
	private List<Game> games = new ArrayList<Game>();

	/**
	 * new GamesManager constructor should add default games?
	 */
	public GamesManager() {
		
//		loadFirstGame();

	}
	
//	private void loadFirstGame()
//	{
//		StringBuilder result = new StringBuilder("");
//	    File file = new File("MovesFacadeTestJSON.txt");
//		
//		try (Scanner scanner = new Scanner(file)) {
//
//			while (scanner.hasNextLine()) {
//				String line = scanner.nextLine();
//				result.append(line).append("\n");
//			}
//			scanner.close();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		String jsonString = result.toString();
//				
//		JsonElement jsonToParse = new JsonParser().parse(jsonString).getAsJsonObject();
//
//		ClientModel newModel = new Interpreter().deserialize(jsonToParse);
//		
//		addNewGame(newModel,"Default Game");
//	}

	/**
	 * copy constructor
	 * 
	 * @param games
	 */
	public GamesManager(List<Game> games) {
		this.games = games;
	}

	/**
	 * Adds a new game to the existing list
	 * 
	 * @param newGame
	 *            : the game to add to the current list
	 */
	public void addGame(Game newGame) {
		games.add(newGame);
	}
	
	/**
	 * adds a game that is brand new by setting it's id
	 * @param newGame
	 */
	public void addNewGame(ClientModel model, String name) {
		// Checks to give a unique ID
		int highestID = games.stream().mapToInt(game -> game.getId()).reduce(-1, Integer::max);
		int newID = highestID + 1;
		Game newGame = new Game(model, name, newID);
		addGame(newGame);
	}
	
	/**
	 * adds game and returns it's ID
	 * @param model
	 * @param name
	 * @return id of game just added
	 */
	public int addNewGameGetID(ClientModel model, String name) {
		// Checks to give a unique ID
		int highestID = games.stream().mapToInt(game -> game.getId()).reduce(-1, Integer::max);
		int newID = highestID + 1;
		Game newGame = new Game(model, name, newID);
		addGame(newGame);
		return newID;
	}
	
	public int addNewGameGetID(String gameName, boolean randHexes, boolean randPorts, boolean randNumbers) {
		int highestID = games.stream().mapToInt(game -> game.getId()).reduce(-1, Integer::max);
		int newID = highestID + 1;
		Map newGameMap = mapGenerator.generateMap(randHexes, randPorts, randNumbers);
		ClientModel gameModel = new ClientModel(newGameMap);
		Game newGame = new Game(gameModel, gameName, newID);
		addGame(newGame);
		return newID;
	}
	
	/**
	 * With the given user and gameId attempts to add the user to the game
	 * pre - User and Game must exist
	 * @param user
	 * @param gameId
	 * @return true if the user was added to the game, false otherwise
	 */
	public boolean joinGame(User user, int gameId, String color) {
		Game game = getGameById(gameId);
		
		//check if the player is already in the game
		ArrayList<Player> currentPlayers = game.getPlayerList();
		for(Player p : currentPlayers) {
			if(user.getPlayerID() == p.getPlayerID()) {
				p.setColor(color); //Sets the players new color in case he has chose differently				
				game.getClientModel().setVersion(game.getClientModel().getVersion() + 1);
				return true;
			}
		}

		//if the player is not already in the game check if he can join the game
		if(currentPlayers.size() >= 4)
			return false;
		
		
		//create Player object and add him to the game's client model
		Player player = new Player(user.getName(), color, user.getPlayerID(), currentPlayers.size());
		game.getClientModel().addPlayer(player);
		game.getClientModel().setVersion(game.getClientModel().getVersion() + 1);
		return true;
	}

	/**
	 * Get a game by its id
	 * 
	 * @param id
	 *            : The unique ID of the game to retrieve
	 * @return
	 */
	public Game getGameById(int id) {
		for (Game game : games) {
			if (game.getId() == id) {
				return game;
			}
		}
		System.out.println("game id not found, getGameById is returning null");
		return null;
	}
	
	public ClientModel getClientModelById(int id) {
		for (Game game : games) {
			if (game.getId() == id) {
				return game.getClientModel();
			}
		}
		System.out.println("game id not found, getGameById is returning null");
		return null;
	}

	/**
	 * Checks by id to see if the game exists
	 * @param gameId
	 * @return true if the game exists, otherwise false
	 */
	public boolean gameExists(int gameId) {
		for(Game g : games)
			if(g.getId() == gameId) return true;
		return false;
	}
	
	/**
	 * Checks to see if the player's color has already been chosen by another player
	 * @param gameId
	 * @param color
	 * @return true if the color is already taken, otherwise false
	 */
	public boolean colorTaken(int gameId, int playerId, String color) {
		for(Player p : getGameById(gameId).getPlayerList())
			if(p.getColor().equals(color))
				if(p.getPlayerID() != playerId)
					return true;
		return false;
	}
	
	/**
	 * Returns the list of existing games
	 * 
	 * @return
	 */
	public List<Game> getGames() {
		return games;
	}
	
	/**
	 * Sets the game list
	 */
	public void setGames(List<Game> games) {
		this.games = games;
	}

	public Game getMostRecent()
	{
		return games.get(games.size()-1);
	}
}
