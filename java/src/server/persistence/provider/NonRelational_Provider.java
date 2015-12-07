package server.persistence.provider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import server.persistence.dao.*;

public class NonRelational_Provider extends IProvider {
	
	
	public NonRelational_Provider(int delta)
	{
		super.userDAO = new NonRelational_UserDAO();
		super.gameDAO = new NonRelational_GameDAO();
		super.commandDAO = new NonRelational_CommandDAO();
		super.delta = delta;
		
		File f = new File("TextPersistance/master.txt");
		if (!f.exists()) 
		{
			new File("TextPersistance").mkdir();
			PrintWriter out;
			try {
				out = new PrintWriter(f);
				out.println("");
				out.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void startTransaction()
	{
		return;
	}

	@Override
	public void endTransaction(boolean commit)
	{
		return;
	}
	
}
