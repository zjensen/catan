package client.data;

import java.util.*;

/**
 * Used to pass game information into views<br>
 * <br>
 * PROPERTIES:<br>
 * <ul>
 * <li>Id: Unique game ID</li>
 * <li>Title: Game title (non-empty string)</li>
 * <li>Players: List of players who have joined the game (can be empty)</li>
 * </ul>
 * 
 */
public class GameInfo
{
	private int id;
	private String title;
	private List<PlayerInfo> players;
	
	public GameInfo()
	{
		setId(-1);
		setTitle("");
		players = new ArrayList<PlayerInfo>();
	}
	
	public GameInfo(String title,int id)
	{
		setId(id);
		setTitle(title);
		players = new ArrayList<PlayerInfo>();
	}
	
	public boolean hasPlayer(int playerID)
	{
		for(PlayerInfo p : players)
		{
			if(p.getId() == playerID)
			{
				return true;
			}
		}
		return false;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public void addPlayer(PlayerInfo newPlayer)
	{
		players.add(newPlayer);
	}
	
	public List<PlayerInfo> getPlayers()
	{
		return Collections.unmodifiableList(players);
	}
	
	public PlayerInfo[] getPlayersArray()
	{
		PlayerInfo[] playersArray = new PlayerInfo[players.size()];
		for(int i=0;i<players.size();i++)
		{
			playersArray[i] = players.get(i);
		}
		return playersArray;
	}

	public void updatePlayer(PlayerInfo pi)
	{
		for(PlayerInfo p : players)
		{
			if(p.getId() == pi.getId())
			{
				p.setName(pi.getName());
				p.setColor(pi.getColor());
				p.setPlayerIndex(pi.getPlayerIndex());
				return;
			}
		}
	}

	public PlayerInfo getPlayerByIndex(int playerIndex)
	{
		for(PlayerInfo p : players)
		{
			if(p.getPlayerIndex() == playerIndex)
			{
				return p;
			}
		}
		return null;
	}
}

