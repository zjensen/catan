package shared.models;
import static org.junit.Assert.*;

import org.junit.*;

import shared.communication.moves.DiscardCards_Input;

public class PlayerTest {

	public Player ian;
	
	@Before
	public void init(){
		ian = new Player();
	}
	
	@Test
	public void testCanOfferTrade() throws Exception {
		
		ResourceCards myCards = new ResourceCards();
		
		myCards.setBrick(2);
		myCards.setSheep(4);
		
		assertFalse(ian.canOfferCards(myCards));
		
		myCards.setBrick(-2);
		myCards.setSheep(-4);
		
		ian.setResources(myCards);
		
		assertTrue(ian.canOfferCards(myCards));
	}
	
	@Test
	public void testCanBuyDevCard(){
		
		ResourceCards myCards = new ResourceCards();
		
		assertFalse(ian.canBuyDevCard());
		
		myCards.setWheat(2);
		myCards.setSheep(4);
		myCards.setOre(1);
		
		ian.setResources(myCards);
		assertTrue(ian.canBuyDevCard());
	}
	
	@Test
	public void testCanPlayDevCard(){
		
		assertTrue(ian.canPlayDevCard()); 
		ian.setPlayedDevCard(true);
		assertFalse(ian.canPlayDevCard());
		ian.setPlayedDevCard(false);
		
		assertFalse(ian.canMonopoly());
		assertFalse(ian.canSoldier());
		assertFalse(ian.canMonument());
		assertFalse(ian.canYearOfPlenty());
		assertFalse(ian.canRoadBuilding());
		
		DevCards myDeck = new DevCards();
		myDeck.setMonopoly(1);
		myDeck.setMonument(1);
		myDeck.setRoadBuilding(1);
		myDeck.setSoldier(1);
		myDeck.setYearOfPlenty(1);
		
		ian.setOldDevCards(myDeck);
		
		assertTrue(ian.canMonopoly());
		assertTrue(ian.canSoldier());
		ian.setVictoryPoints(10-ian.getMonuments());
		assertTrue(ian.canMonument());
		assertTrue(ian.canYearOfPlenty());
		assertTrue(ian.canRoadBuilding());
		
	}
	
	
//	@Test
//	public void testCanPlayYearOfPlenty(){
//		
//	}
	
	@Test
	public void testCanDiscardCards(){
		// no cards to discard
		ResourceCards myCards = new ResourceCards();
		myCards.setBrick(2);
		myCards.setWheat(2);
		myCards.setWood(2);
		DiscardCards_Input params = new DiscardCards_Input(0, myCards);
		assertFalse(ian.canDiscardCards(params));
		
		//add some cards
		myCards.setOre(2);
		myCards.setSheep(2);
		ian.setResources(myCards);
		params.setDiscardedCards(myCards);
		
		assertTrue(ian.canDiscardCards(params));
	}
	
	@Test
	public void testCanBuildRoad(){
		// no resources
		ResourceCards myCards = new ResourceCards();
		assertFalse(ian.canBuildRoad());
		
		//add some cards
		myCards.setBrick(2);
		myCards.setWheat(2);
		myCards.setWood(2);
		myCards.setOre(2);
		myCards.setSheep(2);
		ian.setResources(myCards);
		
		assertTrue(ian.canBuildRoad());
	}
	
	@Test
	public void testCanBuildSettlement(){
		// no resources
		ResourceCards myCards = new ResourceCards();
		assertFalse(ian.canBuildSettlement());
		
		//add some cards
		myCards.setBrick(2);
		myCards.setWheat(2);
		myCards.setWood(2);
		myCards.setOre(2);
		myCards.setSheep(2);
		ian.setResources(myCards);
		
		assertTrue(ian.canBuildSettlement());
	}
	
	@Test
	public void testCanBuildCity(){
		// no resources
		ResourceCards myCards = new ResourceCards();
		assertFalse(ian.canBuildCity());
		
		//add some cards
		myCards.setWheat(2);
		myCards.setOre(3);
		ian.setResources(myCards);
		// have resources to build city
		assertTrue(ian.canBuildCity());
		
		// no cities left
		ian.setCities(0);
		assertFalse(ian.canBuildCity());
	}
	
}
