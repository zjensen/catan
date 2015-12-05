package testingProvider;

import server.persistence.provider.SQLProvider;

public class TestProvider {
	
	public static void main(String[] args) {
		SQLProvider p = new SQLProvider(5);
		p.startTransaction();
		
		p.getUserDAO().addUser("Somestring", 0);
		
		p.endTransaction(true);
		
		
	}
}
