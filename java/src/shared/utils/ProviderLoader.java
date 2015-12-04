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

public class ProviderLoader 
{
	private String fileName;
	private String className;
	
	public ProviderLoader()
	{
		
	}
	
	public static void main(String[] args) throws Exception {
        System.out.println("Starting ProviderLoader...");
	
        ProviderLoader loader = new ProviderLoader();
        loader.registerJarFile();
	
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void registerJarFile() throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
			
/* ATTEMPT 1. I was unable to get this to work when I had a function with parameters
		// Getting the jar URL which contains target class
		URL[] classLoaderUrls = this.grabJarFiles();
      
		// Create a new URLClassLoader
		URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls);

		// Load the target class
		Class<?> testClass = urlClassLoader.loadClass(className);
		
		
		// Create a new instance from the loaded class
		Constructor<?> constructor = testClass.getConstructor();
		Object testObj = constructor.newInstance();
		

		// Getting a method from the loaded class and invoke it
		Method function5Method = testClass.getMethod("function5");
		
		function5Method.invoke(testObj, new Object[] { "BILLY", 99 });

		Method function3Method = testClass.getMethod("function3");
		Method function4Method = testClass.getMethod("function4");
			
		int returnedInt = (int) function3Method.invoke(testObj);
		String returnedName = (String) function4Method.invoke(testObj);
		System.out.println("returnedName=" + returnedName);
		System.out.println("returnedInt =" + returnedInt);
*/		

		ClassLoader myClassLoader = ClassLoader.getSystemClassLoader();
		this.grabJarFiles();
		
		String classNameToBeLoaded = className;
		  
		Class myClass = myClassLoader.loadClass(classNameToBeLoaded);
		   
		Object whatInstance = myClass.newInstance();
		   
		String methodParameter = "Billy";
		   
		Method myMethod = myClass.getMethod("function6", new Class[] { String.class });
		Method getValueMethod = myClass.getMethod("getTestName");
		
		String returnValue = (String) myMethod.invoke(whatInstance, new Object[] { methodParameter });
		System.out.println("returnedValue=" + returnValue);
		
		String testClassValue = (String) getValueMethod.invoke(whatInstance);
		System.out.println("testClassValue=" + testClassValue);
	}

	public void grabJarFiles()
	{
		File file = new File("..\\java\\plugins\\config.txt");
		
		int quoteIndexFileName = 0;
		int quoteIndexClassName = 0;
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
				if (curLine.contains("ClassName"))
				{
					quoteIndexClassName = curLine.indexOf('\"', 12);
					if (quoteIndexClassName == 12)
					{
						System.out.println("You did not specify a file path name");
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




