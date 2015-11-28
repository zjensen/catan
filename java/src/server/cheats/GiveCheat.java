package server.cheats;

import server.cheats.helpers.GiveCheatHelper;
import shared.models.ClientModel;
import shared.models.Player;

public class GiveCheat implements Cheat 
{
	GiveCheatHelper helper;
	
	public GiveCheat(String[] params, Player player, ClientModel model)
	{
		this.helper = new GiveCheatHelper(params, player, model);
	}
	
	@Override
	public void execute() 
	{
		this.helper.execute();
	}

}
