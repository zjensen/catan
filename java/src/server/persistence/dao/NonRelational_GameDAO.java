package server.persistence.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NonRelational_GameDAO implements IGameDAO {

	public NonRelational_GameDAO()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> loadGames()
	{
		List<String> games = new ArrayList<String>();
		
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
				File f = new File("TextPersistance/game"+gameID+".txt");
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
						games.add(scan2.nextLine());
					}
					scan2.close();
				}
			}
			scan.close();
		}
		return games;
	}

	@Override
	public void updateGame(String game, int gameID)
	{	
		File f = new File("TextPersistance/game"+gameID+".txt");
		PrintWriter out;
		try {
			out = new PrintWriter(f);
			out.write(game);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addGame(String game, int gameID)
	{
		File master = new File("TextPersistance/master.txt");
		PrintWriter out1;
		try {
			out1 = new PrintWriter(new FileOutputStream(master, true));
			out1.println(gameID); //add ID of game, so we know to look for file later
			out1.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		File f = new File("TextPersistance/game"+gameID+".txt");
		PrintWriter out;
		try {
			out = new PrintWriter(f);
			out.write(game);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	

}
