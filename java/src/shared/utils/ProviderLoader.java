package shared.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Scanner;

import server.persistence.provider.IProvider;

public class ProviderLoader 
{
	private String fileName;
	private String className;
	private int delta;
	
	public ProviderLoader()
	{
		
	}
	
	public IProvider initializeProvider() 
	{
		System.out.println("Starting ProviderLoader...");
		IProvider newProvider = registerJarFile();
        System.out.println("Ended loading provider");
        return newProvider;
	}
	
	public IProvider registerJarFile() 
	{
		IProvider newProvider = null;
		// Getting the jar URL which contains target class
		this.grabJarFile();
		URL[] classLoaderUrls;
		try {
			classLoaderUrls = new URL[]{new URL(fileName)};
			// Create a new URLClassLoader
			URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls);
	
			// Load the target class
			Class<?> testClass = urlClassLoader.loadClass(className);
			
			// Creates a new instance of the Persistence Plugin
			newProvider = (IProvider) testClass.newInstance();
			newProvider.setDelta(delta);
			
		} catch (MalformedURLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		};
		return newProvider;
	}

	public void grabJarFile()
	{
		File file = new File("..\\java\\plugins\\config.txt");
		
		int quoteIndexFileName = 0;
		int quoteIndexClassName = 0;
		int quoteIndexDelta = 0;
		String curLine = "";
		
		try (Scanner scanner = new Scanner(file)) 
		{
			while (scanner.hasNextLine()) 
			{
				curLine = scanner.nextLine();
				if (curLine.contains("<!--")) {
					//System.out.println("\tIgnore This Line");
				}
				if (curLine.contains("FilePathName"))
				{
					quoteIndexFileName = curLine.indexOf('\"', 15);
					if (quoteIndexFileName == 15)
					{
						System.out.println("You did not specify a file path name");
					}
					else
					{
						fileName = curLine.substring(15, quoteIndexFileName);
					}
				}
				else if (curLine.contains("ClassName"))
				{
					quoteIndexClassName = curLine.indexOf('\"', 12);
					if (quoteIndexClassName == 12)
					{
						System.out.println("You did not specify a class name");
					}
					else
					{
						className = curLine.substring(12, quoteIndexClassName);
					}
				}
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return;
	}
	
}




