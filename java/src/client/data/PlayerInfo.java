package client.data;

import shared.definitions.*;

/**
 * Used to pass player information into views<br>
 * <br>
 * PROPERTIES:<br>
 * <ul>
 * <li>Id: Unique player ID</li>
 * <li>PlayerIndex: Player's order in the game [0-3]</li>
 * <li>Name: Player's name (non-empty string)</li>
 * <li>Color: Player's color (cannot be null)</li>
 * </ul>
 * 
 */
public class PlayerInfo
{
	private int id;
	private int playerIndex;
	private String name;
	private CatanColor color;
	
	public PlayerInfo()
	{
		setId(-1);
		setPlayerIndex(-1);
		setName("");
		setColor(CatanColor.WHITE);
	}
	
	public PlayerInfo(String name, int id)
	{
		setId(id);
		setPlayerIndex(-1);
		setName(name);
		setColor(CatanColor.WHITE);
	}
	public PlayerInfo(String name, int id, CatanColor color)
	{
		setId(id);
		setPlayerIndex(-1);
		setName(name);
		setColor(color);
	}
	
	public PlayerInfo(String name, int id, CatanColor color, int index)
	{
		setId(id);
		setPlayerIndex(index);
		setName(name);
		setColor(color);
	}

	
	
	
	
	
	@Override
	public String toString() {
		return "PlayerInfo "
				+ "\nid=" + id 
				+ "\nplayerIndex=" + playerIndex 
				+ "\nname=" + name 
				+ "\ncolor=" + color + "   ]";
	}

	public int getId()
	{
		return id;
	}
	
	public void setId(int ID)
	{
		this.id = ID;
	}
	
	public int getPlayerIndex()
	{
		return playerIndex;
	}
	
	public void setPlayerIndex(int playerIndex)
	{
		this.playerIndex = playerIndex;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public CatanColor getColor()
	{
		return color;
	}
	
	public void setColor(CatanColor color)
	{
		this.color = color;
	}
	
	public void setColor(String color)
	{
		this.color = CatanColor.BLUE;
	}

	@Override
	public int hashCode()
	{
		return 31 * this.id;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final PlayerInfo other = (PlayerInfo) obj;
		
		return this.id == other.id;
	}
}

