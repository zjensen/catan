package client.server;

import org.junit.*;

import static org.junit.Assert.*;

public class ServerUnitTests 
{
	
	@Before
	public void setup() 
	{
	}
	
	@After
	public void teardown() 
	{
		
	}
	
	@Test
	public void test_1() 
	{		
		assertEquals("OK", "OK");
		assertTrue(true);
		assertFalse(false);
	}

	public static void main(String[] args) 
	{

		String[] testClasses = new String[] 
		{
				"client.server.ServerUnitTests",
				"client.server.LoginUnitTests",
				"client.server.RegisterUnitTests"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
}