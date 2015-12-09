package shared.utils;

import java.io.File;
import java.io.IOException;
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
	private String persistenceType;
	
	public ProviderLoader()
	{
		
	}
	
	public IProvider initializeProvider(String persistenceType, int delta) 
	{
		System.out.println("Initializing Provider...");
		this.delta = delta;
		this.persistenceType = persistenceType;
		IProvider newProvider = registerJarFile();
        return newProvider;
	}
	
	public IProvider registerJarFile() 
	{
		IProvider newProvider = null;
		// Getting the jar URL which contains target class
		this.grabJarFile();
		URL[] classLoaderUrls;
		try {
			
			System.out.println("FN=" + fileName);
			System.out.println("CN=" + className);
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
		System.out.println("Starting grabJarFile");
		//File newFile = new File(".");
		//System.out.println("FILE: " + newFile.getAbsolutePath());

		String filePath = "plugins" + File.separator + "config.txt";
		
		File file = new File(filePath);
		
		int quoteIndexFileName = 0;
		int quoteIndexClassName = 0;
		String curLine = "";
		String fileNameLine = "";
		String classNameLine = "";
					
		try (Scanner scanner = new Scanner(file)) 
		{
			while (scanner.hasNextLine()) 
			{
				curLine = scanner.nextLine();
				if (curLine.contains("<!--")) {
					//System.out.println("\tIgnore This Line");
				}
				else if (curLine.contains("PersistenceType") && curLine.contains(persistenceType))
				{
					quoteIndexFileName = curLine.indexOf('\"', 16);
					quoteIndexClassName = curLine.indexOf('\"', 13);
					
					fileNameLine = scanner.nextLine();
					classNameLine = scanner.nextLine();
					
					fileName = fileNameLine.substring(15, quoteIndexFileName);
					className = curLine.substring(12, quoteIndexClassName);
					
					System.out.println("fileName=" + fileName);
					System.out.println("className=" + className);
					System.out.println("persistencetype=" + persistenceType);
				}
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return;
	}
	
}




