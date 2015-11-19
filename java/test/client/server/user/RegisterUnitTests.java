package client.server.user;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.manager.ServerManager;
import shared.communication.user.Register_Input;
import shared.communication.user.Register_Output;
import client.server.Server;

public class RegisterUnitTests 
{
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
		return;
	}
		
	private Server server;

	@Before
	public void setUp() throws Exception 
	{
		this.server = new Server();
	}

	@After
	public void tearDown() throws Exception 
	{
		
	}
	
	@Test
	public void testLogin() 
	{
		// test valid input
		ServerManager.instance().reset();
		Register_Input register_input = new Register_Input("new_user", "new_user");
		Register_Output register_result = server.register(register_input);
		
		// test invalid input
		Register_Input bad_input = new Register_Input("Sam", "sam");
		Register_Output bad_result = server.register(bad_input);
	}
}
