package testingProvider;

import java.util.List;

import server.persistence.provider.SQLProvider;

public class TestProvider {
	
	public static void main(String[] args) {
		SQLProvider p = new SQLProvider();
		p.setDelta(5);
		p.startTransaction();
		
		p.getUserDAO().addUser("Somestring11", 0);
		p.getUserDAO().addUser("Somestring1", 0);
		p.getUserDAO().addUser("Somestring2", 0);
		p.getUserDAO().addUser("Somestring3", 0);
		p.getUserDAO().addUser("Somestring4", 0);
		p.getUserDAO().addUser("Somestring5", 0);
		
		List<String> userList = p.getUserDAO().loadUsers();
		for(String s : userList)
			System.out.println(s);
		
		p.getCommandDAO().addCommand("Somestring1", 1);
		p.getCommandDAO().addCommand("Somestring2", 1);
		p.getCommandDAO().addCommand("Somestring3", 1);
		p.getCommandDAO().addCommand("Somestring4", 1);
		p.getCommandDAO().addCommand("Somestring1", 5);
		p.getCommandDAO().addCommand("Somestring2", 5);
		p.getCommandDAO().addCommand("Somestring3", 5);
		p.getCommandDAO().addCommand("Somestring1", 3);
		p.getCommandDAO().addCommand("Somestring2", 3);
		p.getCommandDAO().addCommand("Somestring3", 3);
		p.getCommandDAO().addCommand("Somestring4", 3);
		
		List<String> commandList = p.getCommandDAO().loadCommands();
		for(String s : commandList)
			System.out.println(s);
		
		System.out.println(p.getCommandDAO().getNumberOfSavedCommands(5));
		
		p.getCommandDAO().purge(5);
		
		System.out.println(p.getCommandDAO().getNumberOfSavedCommands(5));
		
		p.endTransaction(true);
		
		
	}
}
