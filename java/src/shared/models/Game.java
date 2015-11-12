package shared.models;

import java.util.ArrayList;

/**
 * FOR LOGIN PAGE. Displays all games going on
 * @author zsjensen
 *
 */
public class Game {

	private ClientModel clientModel;
	private String title;
	private int id;
	
	public Game(ClientModel clientModel, String title, int id)
	{
		this.clientModel = clientModel;
		this.title = title;
		this.id = id;
	}
	
	public Game()
	{
		
	}
	
	/**
	 * check if there is space in game
	 * @return true if user can join game, else false
	 */
	public boolean canJoin()
	{
		return false;
	}
	
	/**
	 * check if user is in this game
	 * @return true if user is in this game, else false
	 */
	public boolean inGame()
	{
		return false;
	}
	
	/**
	 * 
	 * @return string of each player name in game
	 */
	public String listPlayers()
	{
		StringBuilder sb = new StringBuilder();
		Player[] players = clientModel.getPlayers();
		for(Player p : players)
		{
			sb.append(p.getName());
			sb.append(", ");
		}
		
		if(sb.length() > 0)
		{
			sb.setLength(sb.length() - 2);
		}
		return sb.toString();
	}
	
	public ArrayList<Player> getPlayerList() 
	{
		ArrayList<Player> playerList = new ArrayList<Player>();
		Player[] players = clientModel.getPlayers();
		for(Player p : players)
		{
			playerList.add(p);
		}
		return playerList;
	}

	/**
	 * @return the clientModel
	 */
	public ClientModel getClientModel() {
		return clientModel;
	}

	/**
	 * @param clientModel the clientModel to set
	 */
	public void setClientModel(ClientModel clientModel) {
		this.clientModel = clientModel;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	//is in game(id)
	
}
