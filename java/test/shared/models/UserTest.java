package shared.models;

import org.junit.*;
import static org.junit.Assert.*;

public class UserTest {
	
	User myUser;
	
	@Before
	public void init() {
		String name = "test", password = "test";
		int ID = 12345;
		
		myUser = new User(name, password, ID);
	}
	
	@Test
	public void testUserComponents(){
		assertEquals("test", myUser.getName());
		
		assertEquals("test", myUser.getPassword());
		
		assertEquals(12345, myUser.getPlayerID());
		
	}

}
