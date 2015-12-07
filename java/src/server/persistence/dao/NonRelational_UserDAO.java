package server.persistence.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NonRelational_UserDAO implements IUserDAO {

	public NonRelational_UserDAO()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> loadUsers()
	{
		List<String> users = new ArrayList<String>();
		File f = new File("TextPersistance/users.txt");
		if (f.exists() && !f.isDirectory()) 
		{
			Scanner scan = null;
			try {
				scan = new Scanner(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			while(scan.hasNextLine())
			{
				String s = scan.nextLine();
				users.add(s);
			}
			scan.close();
		}
		return users;
	}

	@Override
	public void addUser(String user, int userID)
	{
		File f = new File("TextPersistance/users.txt");
		PrintWriter out;
		try {
			out = new PrintWriter(new FileOutputStream(f, true));
			out.println(user);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


}
