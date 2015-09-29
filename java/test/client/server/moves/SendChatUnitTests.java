package client.server.moves;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import client.server.Server;
import shared.communication.moves.SendChat_Output;
import shared.communication.moves.SendChat_Input;

public class SendChatUnitTests 
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
	public void testSendChat() 
	{
		// test w/o cookies for user
		SendChat_Input no_user_input = new SendChat_Input(0, "User isn't set");
		SendChat_Output no_user_result = server.sendChat(no_user_input);
		assertEquals(no_user_result.getResponse(), "The catan.user HTTP cookie is missing.  You must login before calling this method.");
		
		// test w/o cookies for game
		server.setCatanUser("%7B%22name%22%3A%22Sam%22%2C%22password%22%3A%22sam%22%2C%22playerID%22%3A0%7D");
		SendChat_Input no_game_input = new SendChat_Input(0, "Game isn't set");
		SendChat_Output no_game_result = server.sendChat(no_game_input);
		assertEquals(no_game_result.getResponse(), "The catan.game HTTP cookie is missing.  You must join a game before calling this method.");
		
		// test valid input
		server.setCatanGame("0");
		SendChat_Input send_chat_input = new SendChat_Input(0, "Hello Game!");
		SendChat_Output send_chat_result = server.sendChat(send_chat_input);
		// create regex to make sure our result contains the message
		Matcher m = Pattern.compile("\"source\":\"Sam\",\"message\":\"Hello Game!\"").matcher(send_chat_result.getResponse());
		assertTrue(m.find());
	}
}
