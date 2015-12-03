package shared.utils;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;


public class ProviderLoader 
{
	public static void main(String[] args) throws Exception {
        System.out.println("Starting ProviderLoader...");
		
        
        
        
        
        
		// Getting the jar URL which contains target class
        //URL[] classLoaderUrls = new URL[]{new URL("file:\\C:\\Users\\Trevor\\Desktop\\testClass01.jar")};
        URL[] classLoaderUrls = new URL[]{new URL("file:\\C:\\Users\\Trevor\\Desktop\\testClass01.jar")};
        
        // Create a new URLClassLoader
        URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls);
        
        // Load the target class
        Class<?> testClass = urlClassLoader.loadClass("shared.utils.TestClass");
        
        // Create a new instance from the loaded class
        Constructor<?> constructor = testClass.getConstructor();
        Object testObj = constructor.newInstance();
        
        // Getting a method from the loaded class and invoke it
        Method method = testClass.getMethod("sayHello");
        method.invoke(testObj);
	
	}

	public void moveFileToPluginFolder()
	{
		try
		{
			File afile = new File("C:\\Users\\Trevor\\Desktop\\testClass01.jar");
			
			if(afile.renameTo(new File("C:\\folderB\\" + afile.getName())))
			{
				System.out.println("File is moved successful!");
			}
			else
			{
				System.out.println("File is failed to move!");
			}
		}   
    	catch(Exception e)
		{
    		e.printStackTrace();
    	}
		
		
		
		return;
	}
	
	
	
	
	
	
	
	
}




