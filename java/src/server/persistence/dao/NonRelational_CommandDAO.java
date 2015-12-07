package server.persistence.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NonRelational_CommandDAO implements ICommandDAO {

	public NonRelational_CommandDAO()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> loadCommands()
	{
		List<String> commands = new ArrayList<String>();
		
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
				if(f.exists())
				{
					Scanner scan2 = null;
					try {
						scan2 = new Scanner(f);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					while(scan2.hasNextLine())
					{
						commands.add(scan2.nextLine());
					}
					scan2.close();
				}
			}
			scan.close();
		}
		
		return commands;
	}

	@Override
	public void addCommand(String command, int gameID)
	{
		File f = new File("TextPersistance/commands"+gameID+".txt");
		PrintWriter out;
		try {
			out = new PrintWriter(new FileOutputStream(f, true));
			out.println(command);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void purge(int gameID)
	{
		File f = new File("TextPersistance/commands"+gameID+".txt");
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

	@Override
	public int getNumberOfSavedCommands(int gameID)
	{
		int count = 0;
		
		File f = new File("TextPersistance/commands"+gameID+".txt");
		if (f.exists() && !f.isDirectory()) 
		{
			Scanner scan = null;
			try {
				scan = new Scanner(f);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while(scan.hasNextLine())
			{
				count++;
				scan.nextLine();
			}
			scan.close();
		}
		
		return count;
	}

}
