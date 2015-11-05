package server.manager;

import java.util.HashMap;

import shared.models.ClientModel;

public class ServerManager {
	
	HashMap<Integer, ClientModel> models;
	public static ServerManager _instance;
	
	public static ServerManager instance() 
	{
		if (_instance == null)
		{
			_instance = new ServerManager();
		}
		return _instance;
	}

}
