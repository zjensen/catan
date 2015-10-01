package client.server.user;

import static org.junit.Assert.*;

import org.junit.*;

import client.server.Server;
import shared.communication.user.Login_Input;
import shared.communication.user.Login_Output;

public class LoginUnitTests 
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
		Login_Input login_input = new Login_Input("Sam", "sam");
		Login_Output login_result = server.login(login_input);
		assertEquals(login_result.getResponse(), "Success");
		
		// test invalid input
		Login_Input bad_input = new Login_Input("bad_input", "bad_input");
		Login_Output bad_result = server.login(bad_input);
		assertEquals(bad_result.getResponse(), "Failed to login - bad username or password.");
	}
}
