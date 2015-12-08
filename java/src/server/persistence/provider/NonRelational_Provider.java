package server.persistence.provider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import server.persistence.dao.*;

public class NonRelational_Provider extends IProvider {
	
	
	public NonRelational_Provider()
	{
		super.userDAO = new NonRelational_UserDAO();
		super.gameDAO = new NonRelational_GameDAO();
		super.commandDAO = new NonRelational_CommandDAO();
//		super.delta = delta;
		
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

	@Override
	public void clean()
	{
		File master = new File("TextPersistance/master.txt");
		if (master.exists() && !master.isDirectory()) 
		{
			Scanner scan = null;
			try {
				scan = new Scanner(master);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while(scan.hasNextInt())
			{
				int gameID = scan.nextInt();
				File f = new File("TextPersistance/commands	"+gameID+".txt");
				if (f.exists() && !f.isDirectory()) 
				{
					PrintWriter out;
					try {
						out = new PrintWriter(new FileOutputStream(f, false));
						out.write("");
						out.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
			scan.close();
			
			PrintWriter out;
			try {
				out = new PrintWriter(new FileOutputStream(master, false));
				out.write("");
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		File user = new File("TextPersistance/users.txt");
		if (user.exists() && !user.isDirectory()) 
		{
			PrintWriter out;
			try {
				out = new PrintWriter(new FileOutputStream(user, false));
				out.write("");
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
}
