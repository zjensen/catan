package shared.models;

import java.util.ArrayList;

import server.cheats.CheatInterpreter;
import shared.communication.moves.AcceptTrade_Input;
import shared.communication.moves.BuildCity_Input;
import shared.communication.moves.BuildRoad_Input;
import shared.communication.moves.BuildSettlement_Input;
import shared.communication.moves.BuyDevCard_Input;
import shared.communication.moves.DiscardCards_Input;
import shared.communication.moves.FinishTurn_Input;
import shared.communication.moves.MaritimeTrade_Input;
import shared.communication.moves.Monopoly_Input;
import shared.communication.moves.Monument_Input;
import shared.communication.moves.OfferTrade_Input;
import shared.communication.moves.RoadBuilding_Input;
import shared.communication.moves.RobPlayer_Input;
import shared.communication.moves.RollNumber_Input;
import shared.communication.moves.SendChat_Input;
import shared.communication.moves.Soldier_Input;
import shared.communication.moves.YearOfPlenty_Input;
import shared.definitions.DevCardType;
import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class ClientModel {

	private ResourceCards bank;
	private DevCards deck;
	private MessageList chat;
	private MessageList log;
	private Map map;
	private Player[] players;
	private TradeOffer tradeOffer;
	private TurnTracker turnTracker;
	private int version;
	private int winner;
	private int longestRoadIndex;

	public ClientModel() {
		this.bank = null;
		this.deck = null;
		this.chat = null;
		this.log = null;
		this.map = null;
		this.players = null;
		this.tradeOffer = null;
		this.turnTracker = null;
		this.version = -1;
		this.winner = -1;
		this.longestRoadIndex = -1;
	}

	/**
	 * Initializes a new client model with all the default vaules 19 of each of
	 * Resource Cards 14 Soldiers 2 Monopoly, 2 Road Building 2 Year of Plenty 5
	 * Monument Initializes the array of players
	 * 
	 * @param map
	 */
	public ClientModel(Map map) {
		this.bank = new ResourceCards(19, 19, 19, 19, 19);
		this.deck = new DevCards(14, 2, 2, 2, 5);
		this.chat = new MessageList();
		this.log = new MessageList();
		this.map = map;
		this.players = new Player[0];
		this.tradeOffer = null;
		this.turnTracker = new TurnTracker();
		this.version = 0;
		this.winner = -1;
		this.longestRoadIndex = -1;
	}

	/**
	 * parses json to update member variables
	 * 
	 * @param json
	 *            -> json with the new client model information
	 */
	public void updateClient(String json) {

	}

	public void addPlayer(Player player) {
		if (players == null) {
			players = new Player[1];
			players[0] = player;
		} else {
			ArrayList<Player> playerList = new ArrayList<>();
			for (Player p : players) {
				playerList.add(p);
			}
			playerList.add(player);
			players = playerList.toArray(players);
			// players = (Player[]) playerList.toArray();
		}
	}

	/**
	 * grabs player with the specified index
	 * 
	 * @param index
	 * @return Player with the specified index
	 */
	public Player getPlayerByIndex(int index) {
		for (Player p : players) {
			if (p.getIndex() == index) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Gets a player's index in this model
	 * 
	 * @param playerID
	 * @return
	 */
	public int getPlayerIndexByID(int playerID) {
		for (Player p : players) {
			if (p.getPlayerID() == playerID) {
				return p.getIndex();
			}
		}
		return -1;
	}

	/**
	 * can we play road building dev card with these params?
	 * 
	 * @param params
	 * @return
	 */
	public boolean canRoadBuilding(RoadBuilding_Input params) // todo
	{
		Player p = getPlayerByIndex(params.getPlayerIndex());
		if (params.getSpot1() != null && params.getSpot2() != null) {
			if (params.getSpot1().equals(params.getSpot2())) {
				return false;
			}
		}

		return p.canRoadBuilding(params) && map.canRoadBuilding(params);
	}

	/**
	 * make sure player has the cards they are attempting to offer
	 * 
	 * @param params
	 * @return
	 */
	public boolean canOfferTrade(OfferTrade_Input params) {
		Player sender = getPlayerByIndex(params.getPlayerIndex());
		// Player receiver = getPlayerByIndex(params.getReceiver());
		return (sender.canOfferCards(params.getOffer())); // &&
															// receiver.hasResources(params.getOffer()));
	}

	/**
	 * Checks if the player has the cards needed to trade
	 * 
	 * @param params
	 * @return
	 */
	public boolean canAcceptTrade(AcceptTrade_Input params) {
		Player receiver = getPlayerByIndex(params.getPlayerIndex());

		if (params.getPlayerIndex() != this.getTradeOffer().getReceiver()) // this
																			// user
																			// is
																			// incorrect
		{
			return false;
		}
		if (!params.isWillAccept()) // user is declining, so we don't need to
									// check if they have the cards
		{
			return true;
		}

		return receiver.canReceiveCards(this.getTradeOffer().getOffer());
	}

	public boolean canMaritimeTrade(MaritimeTrade_Input params) // todo
	{
		Player p = getPlayerByIndex(params.getPlayerIndex());
		if (!p.canMaritimeTrade(params)) {
			return false;
		} else if (!map.canMaritimeTrade(params)) // todo
		{
			return false;
		} else if (!bank.canChangeResource(params.getOutputResource(), 1)) // does
																			// bank
																			// have
																			// enough
																			// resources?
		{
			return false;
		}
		return true;
	}

	public boolean canSoldier(Soldier_Input params) {
		boolean can = true;
		int playerIndex = params.getPlayerIndex();
		if (!getPlayerByIndex(playerIndex).canSoldier()) // the player is able
															// to build the road
		{
			can = false;
		} else if (!map.canSoldier(params)) {
			can = false;
		}
		return can;
	}

	public boolean canBuyDevCard(BuyDevCard_Input params) {
		if (deck.cardCount() < 1) // does deck have enough remaining cards?
		{
			return false;
		}
		return getPlayerByIndex(params.getPlayerIndex()).canBuyDevCard();
	}

	public boolean canRobPlayer(RobPlayer_Input params) {
		if (params.getVictimIndex() != -1
				&& getPlayerByIndex(params.getVictimIndex()).getNumberOfCards() == 0) {
			return false;
		}
		return map.canRobPlayer(params);
	}

	public boolean canYearOfPlenty(YearOfPlenty_Input params) {
		if (params.getResource().equals(params.getResource1())) // both
																// resources are
																// same, check
																// if bank has 2
																// of them
		{
			if (!bank.canChangeResource(params.getResource(), 2)) // does bank
																	// have
																	// enough
																	// resources?
			{
				return false;
			}
		} else // asking for two different resources
		{
			if (!bank.canChangeResource(params.getResource(), 1)) // does bank
																	// have
																	// enough
																	// resources?
			{
				return false;
			} else if (!bank.canChangeResource(params.getResource1(), 1)) // does
																			// bank
																			// have
																			// enough
																			// resources?
			{
				return false;
			}
		}

		return getPlayerByIndex(params.getPlayerIndex()).canYearOfPlenty();
	}

	public boolean canMonopoly(Monopoly_Input params) {
		return getPlayerByIndex(params.getPlayerIndex()).canMonopoly();
	}

	public boolean canMonument(Monument_Input params) {
		return getPlayerByIndex(params.getPlayerIndex()).canMonument();
	}

	public boolean canDiscardCards(DiscardCards_Input params) {
		return getPlayerByIndex(params.getPlayerIndex())
				.canDiscardCards(params);
	}

	/**
	 * Checks the player and the map to see if a road can be built as requested
	 * 
	 * @param params
	 * @return
	 */
	public boolean canBuildRoad(BuildRoad_Input params) {
		boolean can = true;
		int playerIndex = params.getPlayerIndex();

		if (params.isFree()) // no resources require, roads don't have to be
								// neighbors
		{
			if (!map.canBuildFreeRoad(params)) {
				can = false;
			} else if (getPlayerByIndex(playerIndex).getAvailableRoads() == 0) {
				can = false;
			}
		} else {
			if (!getPlayerByIndex(playerIndex).canBuildRoad()) // the player is
																// able to build
																// the road
			{
				can = false;
			} else if (!map.canBuildRoad(params)) {
				can = false;
			}
		}

		return can;
	}

	/**
	 * Checks the player and the map to see if a city can be built as requested
	 * 
	 * @param params
	 * @return
	 */
	public boolean canBuildCity(BuildCity_Input params) {
		boolean can = true;
		int playerIndex = params.getPlayerIndex();
		if (!getPlayerByIndex(playerIndex).canBuildCity()) // the player is able
															// to build the road
		{
			can = false;
		} else if (!map.canBuildCity(params)) {
			can = false;
		}
		return can;
	}

	/**
	 * Checks the player and the map to see if a settlement can be built as
	 * requested
	 * 
	 * @param params
	 * @return
	 */
	public boolean canBuildSettlement(BuildSettlement_Input params) {
		boolean can = true;
		int playerIndex = params.getPlayerIndex();
		if (!params.isFree()) // player needs resources
		{
			if (!getPlayerByIndex(playerIndex).canBuildSettlement()) // the
																		// player
																		// has
																		// resources
																		// to
																		// build
																		// the
																		// road
			{
				can = false;
			}
		}

		if (!map.canBuildSettlement(params)) // check if this location is
												// available to this user to
												// build on
		{
			can = false;
		}
		return can;
	}

	public ResourceCards getBank() {
		return bank;
	}

	public void setBank(ResourceCards bank) {
		this.bank = bank;
	}

	public MessageList getChat() {
		return chat;
	}

	public void setChat(MessageList chat) {
		this.chat = chat;
	}

	public MessageList getLog() {
		return log;
	}

	public void setLog(MessageList log) {
		this.log = log;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Player[] getPlayers() {
		return players;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}

	public TradeOffer getTradeOffer() {
		return tradeOffer;
	}

	public void setTradeOffer(TradeOffer tradeOffer) {
		this.tradeOffer = tradeOffer;
	}

	public TurnTracker getTurnTracker() {
		return turnTracker;
	}

	public void setTurnTracker(TurnTracker turnTracker) {
		this.turnTracker = turnTracker;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getWinner() {
		return winner;
	}

	public void setWinner(Integer winner) {
		this.winner = winner;
	}

	public DevCards getDeck() {
		return deck;
	}

	public void setDeck(DevCards deck) {
		this.deck = deck;
	}

	public boolean canBuildFirstRoad(BuildRoad_Input params) {
		Player p = getPlayerByIndex(params.getPlayerIndex());
		return map.canBuildInitialRoad(params) && p.getRoadsPlayed() == 0;
	}

	public boolean canBuildSecondRoad(BuildRoad_Input params) {
		Player p = getPlayerByIndex(params.getPlayerIndex());
		return map.canBuildInitialRoad(params) && p.getRoadsPlayed() == 1;
	}

	public boolean canFinishTurnFirstRound(int playerIndex) {
		Player p = getPlayerByIndex(playerIndex);
		return (p.getRoadsPlayed() == 1) && (p.getSettlementsPlayed() == 1);
	}

	public boolean canFinishTurnSecondRound(int playerIndex) {
		Player p = getPlayerByIndex(playerIndex);
		return (p.getRoadsPlayed() == 2) && (p.getSettlementsPlayed() == 2);
	}

	public boolean needsToDiscard(int index) {
		Player p = getPlayerByIndex(index);
		return (p.getNumberOfCards() > 7) && !p.isDiscarded();
	}

	public int cardsToDiscard(int index) {
		Player p = getPlayerByIndex(index);
		return (p.getNumberOfCards() / 2);
	}

	public boolean bankHasCards(ResourceType r, int i) {
		return false;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// METHODS FOR MODIFYING MODEL//
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void acceptTrade(AcceptTrade_Input params) {
		version++;
		Player receiver = getPlayerByIndex(params.getPlayerIndex()); // player
		if(params.isWillAccept())
		{
			
			// on
			// receiving
			// end
			// of
			// trade
			receiver.receiveCards(tradeOffer.getOffer()); // gives receiver the
			// cards he was offered,
			// takes away the cards
			// he agreed to trade
			Player sender = getPlayerByIndex(tradeOffer.getSender()); // player that
					// sent the
					// trade
					// initially
			sender.sendCards(tradeOffer.getOffer()); // gives send the cards he
			// asked for, takes away the
			// cards he offered
		}
		tradeOffer = null; // sets tradeOffer to null
		String content = "";
		if (params.isWillAccept()) {
			content = "The trade was accepted.";
		} else {
			content = "The trade was not accepted.";
		}
		log.addLine(receiver.getName(), content);
	}

	public void buildCity(BuildCity_Input params) {
		version++;
		Player p = getPlayerByIndex(params.getPlayerIndex());
		p.buildCity(); // remove resources from player
		bank.cityBuilt(); // add resources to bank
		map.getSettlements().remove(params.getVertexLocation().getNormalizedLocation());
		map.getCities().put(params.getVertexLocation().getNormalizedLocation(),
				p); // place city on map
		log.addLine(p.getName(), (p.getName() + " upgraded to a city."));
		checkThenSetWinner(); // check if this move made a player win the game
	}

	public void buildRoad(BuildRoad_Input params) {
		version++;
		Player p = getPlayerByIndex(params.getPlayerIndex());
		if(params.isFree())
		{
			p.buildFreeRoad();
		}
		else
		{
			p.buildRoad(); // remove resources from player
		}

		p.addBuiltRoad(new EdgeValue(p, params.getRoadLocation()));
		
		//calc longest road
		int tempIdx = this.getLongestRoadIndex();
		if(this.longestRoadIndex != tempIdx)
		{
			this.longestRoadIndex = tempIdx;
			updateLongestRoadPoints();
		}

		map.getRoads().put(params.getRoadLocation().getNormalizedLocation(), p); // add
																					// road
																					// to
		if(!params.isFree())														// map
			bank.roadBuilt(); // add resources to bank
		log.addLine(p.getName(), (p.getName() + " built a road."));
		checkThenSetWinner(); // check if this move made a player win the game
	}

	public void buildSettlement(BuildSettlement_Input params) {
		version++;
		Player p = getPlayerByIndex(params.getPlayerIndex());
		if(params.isFree())
		{
			p.buildFreeSettlement(); // remove resources from player
		}
		else
		{
			p.buildSettlement();
		}
		map.getSettlements().put(
				params.getVertexLocation().getNormalizedLocation(), p); // add
																		// settlement
																		// to
																		// map
		if(!params.isFree())
			bank.settlementBuilt(); // add resources to bank
		checkThenSetWinner(); // check if this move made a player win the game
		log.addLine(p.getName(), (p.getName() + " built a settlement."));
		if(turnTracker.getStatus().equals("secondround"))
		{
			VertexLocation v = params.getVertexLocation().getNormalizedLocation();
			HexLocation h1 = v.getHexLoc();
			VertexDirection d = v.getDir();
			
			if(d.equals(VertexDirection.NorthEast))
			{
				HexLocation h2 = h1.getNeighborLoc(EdgeDirection.North);
				HexLocation h3 = h1.getNeighborLoc(EdgeDirection.NorthEast);
				for(Hex h : map.getHexes())
				{
					if(h.getLocation().equals(h1))
					{
						if(h.getResource()!=null && !h.getResource().equals(HexType.DESERT) && !h.getResource().equals(HexType.WATER))
						{
							bank.subtractOne(ResourceType.valueOf(h.getResource().toString()));
							p.getResources().addOne(ResourceType.valueOf(h.getResource().toString()));
						}
					}
					else if(h.getLocation().equals(h2))
					{
						if(h.getResource()!=null && !h.getResource().equals(HexType.DESERT) && !h.getResource().equals(HexType.WATER))
						{
							bank.subtractOne(ResourceType.valueOf(h.getResource().toString()));
							p.getResources().addOne(ResourceType.valueOf(h.getResource().toString()));
						}
					}
					else if(h.getLocation().equals(h3))
					{
						if(h.getResource()!=null && !h.getResource().equals(HexType.DESERT) && !h.getResource().equals(HexType.WATER))
						{
							bank.subtractOne(ResourceType.valueOf(h.getResource().toString()));
							p.getResources().addOne(ResourceType.valueOf(h.getResource().toString()));
						}
					}
				}
			}
			else if(d.equals(VertexDirection.NorthWest))
			{
				HexLocation h2 = h1.getNeighborLoc(EdgeDirection.North);
				HexLocation h3 = h1.getNeighborLoc(EdgeDirection.NorthWest);
				for(Hex h : map.getHexes())
				{
					if(h.getLocation().equals(h1))
					{
						if(h.getResource()!=null && !h.getResource().equals(HexType.DESERT) && !h.getResource().equals(HexType.WATER))
						{
							bank.subtractOne(ResourceType.valueOf(h.getResource().toString()));
							p.getResources().addOne(ResourceType.valueOf(h.getResource().toString()));
						}
					}
					else if(h.getLocation().equals(h2))
					{
						if(h.getResource()!=null && !h.getResource().equals(HexType.DESERT) && !h.getResource().equals(HexType.WATER))
						{
							bank.subtractOne(ResourceType.valueOf(h.getResource().toString()));
							p.getResources().addOne(ResourceType.valueOf(h.getResource().toString()));
						}
					}
					else if(h.getLocation().equals(h3))
					{
						if(h.getResource()!=null && !h.getResource().equals(HexType.DESERT) && !h.getResource().equals(HexType.WATER))
						{
							bank.subtractOne(ResourceType.valueOf(h.getResource().toString()));
							p.getResources().addOne(ResourceType.valueOf(h.getResource().toString()));
						}
					}
				}
			}
		}
	}

	public void buyDevCard(BuyDevCard_Input params) {
		version++;
		Player p = getPlayerByIndex(params.getPlayerIndex());
		p.getResources().buyDevCard();
		bank.devCardBought();
		// takes random devcard from deck and adds to player's new dev card deck
		p.getNewDevCards().addDevCard(deck.getRandomCard());
		log.addLine(p.getName(), (p.getName() + " bought a development card."));
	}

	public void discardCards(DiscardCards_Input params) {
		version++;
		Player p = getPlayerByIndex(params.getPlayerIndex());
		p.sendCards(params.getDiscardedCards()); // removes all cards in list
		bank.addCards(params.getDiscardedCards()); // add cards from player to
													// bank
		p.setDiscarded(true); // player has now discarded cards
		boolean doneDiscarding = true; // has every player discarded that needs
										// to?
		for (Player p2 : players) {
			if (needsToDiscard(p2.getIndex())) {
				doneDiscarding = false;
			}
		}
		if (doneDiscarding) // every player that needs to discard has done so
		{
			turnTracker.setStatus("robbing"); // let the player rob
			for (Player p2 : players) {
				p2.setDiscarded(false); // reset every players "discarded"
										// variable to false
			}
		}
	}

	public void finishTurn(FinishTurn_Input params) {
		version++;
		Player p = getPlayerByIndex(params.getPlayerIndex()); // player ending
																// turn
		p.getOldDevCards().addCards(p.getNewDevCards()); // adds new devCards to
															// old cards
		p.setNewDevCards(new DevCards()); // resets new cards
		p.setPlayedDevCard(false); // reset playedDevCard variable
		turnTracker.endTurn(); // causes turn tracker to update
		log.addLine(p.getName(), (p.getName() + "'s turn just ended."));
	}

	public void maritimeTrade(MaritimeTrade_Input params) {
		version++;
		getPlayerByIndex(params.getPlayerIndex()).maritimeTrade(params); // updates
																			// player's
																			// resources
		bank.changeResource(params.getOutputResource(), -1); // remove resource
																// from bank
																// that player
																// received
		bank.changeResource(params.getInputResource(), params.getRatio()); // adds
																			// resource
																			// to
																			// bank
																			// that
																			// player
																			// traded
	}

	public void monopoly(Monopoly_Input params) {
		version++;
		Player receiver = getPlayerByIndex(params.getPlayerIndex()); // player
																		// that
																		// played
																		// this
																		// card
		receiver.getOldDevCards().subtractDevCard(DevCardType.MONOPOLY); // take
																			// dev
																			// card
																			// out
																			// of
																			// this
																			// player's
																			// hand
		receiver.setPlayedDevCard(true); // this player has played a card this
											// round, so let it be known
		for (Player p : players) {
			if (p.getIndex() == params.getPlayerIndex()) // current player
															// doesnt give up
															// his resources
			{
				continue;
			}
			int num = 0;
			switch (params.getResource()) // take the requested resource from
											// all the other players
			{
			case WOOD:
				num = p.getResources().getWood();
				if (num > 0)// if player has the requested resource
				{
					p.getResources().changeWood(-num); // take resource from
														// other player
					receiver.getResources().changeWood(num); // give resource to
																// player using
																// this card
				}
				break;
			case BRICK:
				num = p.getResources().getBrick();
				if (num > 0)// if player has the requested resource
				{
					p.getResources().changeBrick(-num);// take resource from
														// other player
					receiver.getResources().changeBrick(num);// give resource to
																// player using
																// this card
				}
				break;
			case SHEEP:
				num = p.getResources().getSheep();
				if (num > 0)// if player has the requested resource
				{
					p.getResources().changeSheep(-num);// take resource from
														// other player
					receiver.getResources().changeSheep(num);// give resource to
																// player using
																// this card
				}
				break;
			case WHEAT:
				num = p.getResources().getWheat();
				if (num > 0)// if player has the requested resource
				{
					p.getResources().changeWheat(-num);// take resource from
														// other player
					receiver.getResources().changeWheat(num);// give resource to
																// player using
																// this card
				}
				break;
			case ORE:
				num = p.getResources().getOre();
				if (num > 0) // if player has the requested resource
				{
					p.getResources().changeOre(-num);// take resource from other
														// player
					receiver.getResources().changeOre(num);// give resource to
															// player using this
															// card
				}
				break;
			}
		}
		log.addLine(receiver.getName(), (receiver.getName()
				+ " is the absolute worst, and stole everyone's "
				+ params.getResource().toString().toLowerCase() + "."));
	}

	public void monument(Monument_Input params) {
		version++;
		Player p = getPlayerByIndex(params.getPlayerIndex());
		// subtracts 1 monument card from player's hand, adds victory point, and
		// sets playedDevCard to true
		p.playMonumentCard();
		log.addLine(
				p.getName(),
				(p.getName() + " played a monument card, and gained a victory point."));
		checkThenSetWinner(); // check if this move made a player win the game
	}

	public void offerTrade(OfferTrade_Input params) {
		version++;
		tradeOffer = new TradeOffer(params.getPlayerIndex(),
				params.getReceiver(), params.getOffer()); // sets trade offer
		Player sender = getPlayerByIndex(params.getPlayerIndex());
		Player receiver = getPlayerByIndex(params.getReceiver());
		log.addLine(sender.getName(), (sender.getName()
				+ " sent a trade offer to " + receiver.getName() + "."));
	}

	public void roadBuilding(RoadBuilding_Input params) {
		version++;
		Player p = getPlayerByIndex(params.getPlayerIndex());
		p.getOldDevCards().subtractDevCard(DevCardType.ROAD_BUILD); // remove
																	// card from
																	// player's
																	// hand
		p.setPlayedDevCard(true); // let it be known in the land that this
									// player has been playing dev cards
		p.setRoads(p.getRoads() - 2); // take away the two roads they just
										// player
		p.setLongestRoad(p.getRoadsPlayed());
		map.getRoads().put(params.getSpot1().getNormalizedLocation(), p); // add
																			// first
																			// road
		p.addBuiltRoad(new EdgeValue(p, params.getSpot1().getNormalizedLocation()));
		map.getRoads().put(params.getSpot2().getNormalizedLocation(), p); // add
																			// second
																			// road
		p.addBuiltRoad(new EdgeValue(p, params.getSpot2().getNormalizedLocation()));
		
		//calc longest road
		int tempIdx = this.getLongestRoadIndex();
		if(this.longestRoadIndex != tempIdx)
		{
			this.longestRoadIndex = tempIdx;
			updateLongestRoadPoints();
		}
		
		log.addLine(p.getName(), (p.getName() + " built two roads."));
		checkThenSetWinner(); // check if this move made a player win the game
	}

	public void robPlayer(RobPlayer_Input params) {
		version++;
		Player player = getPlayerByIndex(params.getPlayerIndex()); // player
																	// that did
																	// the
																	// robbing
		if (params.getVictimIndex() == -1) // no victim
		{
			log.addLine(
					player.getName(),
					(player.getName() + " moved the robber, but was unable to rob anyone."));
		} else {
			Player victim = getPlayerByIndex(params.getVictimIndex()); // victim
																		// of
																		// the
																		// robbing
			player.addCard(victim.getResources().getRandomCard()); // take
																	// random
																	// card from
																	// victim,
																	// give to
																	// player
			log.addLine(player.getName(),
					(player.getName() + " moved the robber, and robbed "
							+ victim.getName() + "."));
		}
		map.setRobber(params.getLocation()); // set robber to new location
		turnTracker.setStatus("playing"); // change status of game
	}

	public void rollNumber(RollNumber_Input params) {
		version++;
		Player player = getPlayerByIndex(params.getPlayerIndex());

		if (params.getNumber() == 7) {
			log.addLine(player.getName(), (player.getName() + " rolled a "
					+ params.getNumber() + "."));
			boolean needToDiscard = false; // can we skip discarding?
			for (Player p : players) {
				if (needsToDiscard(p.getIndex())) {
					needToDiscard = true;
				}
			}
			if (needToDiscard) // at least one player needs to discard
			{
				turnTracker.setStatus("discarding");
			} else // no player needs to discard, skip to robbing
			{
				turnTracker.setStatus("robbing");
			}
		} else {
			for (Hex h : map.getHexes()) {
				Player p = null;
				if (h.getNumber() == params.getNumber()
						&& !h.getLocation().equals(map.getRobber())) {
					if (map.getSettlements().containsKey(
							new VertexLocation(h.getLocation(),
									VertexDirection.NorthWest))) {
						if (bank.canChangeResource(ResourceType.valueOf(h
								.getResource().toString()), 1))
							;
						{
							p = map.getSettlements().get(
									new VertexLocation(h.getLocation(),
											VertexDirection.NorthWest));
							p.getResources().addOne(
									ResourceType.valueOf(h.getResource()
											.toString()));
							bank.subtractOne(ResourceType.valueOf(h
									.getResource().toString()));
						}
					} else if (map.getCities().containsKey(
							new VertexLocation(h.getLocation(),
									VertexDirection.NorthWest))) {
						if (bank.canChangeResource(ResourceType.valueOf(h
								.getResource().toString()), 2))
							;
						{
							p = map.getCities().get(
									new VertexLocation(h.getLocation(),
											VertexDirection.NorthWest));
							p.getResources().addTwo(
									ResourceType.valueOf(h.getResource()
											.toString()));
							bank.subtractTwo(ResourceType.valueOf(h
									.getResource().toString()));
						}
					}

					if (map.getSettlements().containsKey(
							new VertexLocation(h.getLocation(),
									VertexDirection.NorthEast))) {
						if (bank.canChangeResource(ResourceType.valueOf(h
								.getResource().toString()), 1))
							;
						{
							p = map.getSettlements().get(
									new VertexLocation(h.getLocation(),
											VertexDirection.NorthEast));
							p.getResources().addOne(
									ResourceType.valueOf(h.getResource()
											.toString()));
							bank.subtractOne(ResourceType.valueOf(h
									.getResource().toString()));
						}
					} else if (map.getCities().containsKey(
							new VertexLocation(h.getLocation(),
									VertexDirection.NorthWest))) {
						if (bank.canChangeResource(ResourceType.valueOf(h
								.getResource().toString()), 2))
							;
						{
							p = map.getCities().get(
									new VertexLocation(h.getLocation(),
											VertexDirection.NorthWest));
							p.getResources().addTwo(
									ResourceType.valueOf(h.getResource()
											.toString()));
							bank.subtractTwo(ResourceType.valueOf(h
									.getResource().toString()));
						}
					}

					if (map.getSettlements().containsKey(
							new VertexLocation(h.getLocation().getNeighborLoc(
									EdgeDirection.South),
									VertexDirection.NorthEast))) {
						if (bank.canChangeResource(ResourceType.valueOf(h
								.getResource().toString()), 1))
							;
						{
							p = map.getSettlements().get(
									new VertexLocation(
											h.getLocation().getNeighborLoc(
													EdgeDirection.South),
											VertexDirection.NorthEast));
							p.getResources().addOne(
									ResourceType.valueOf(h.getResource()
											.toString()));
							bank.subtractOne(ResourceType.valueOf(h
									.getResource().toString()));
						}
					} else if (map.getCities().containsKey(
							new VertexLocation(h.getLocation(),
									VertexDirection.NorthWest))) {
						if (bank.canChangeResource(ResourceType.valueOf(h
								.getResource().toString()), 2))
							;
						{
							p = map.getCities().get(
									new VertexLocation(h.getLocation(),
											VertexDirection.NorthWest));
							p.getResources().addTwo(
									ResourceType.valueOf(h.getResource()
											.toString()));
							bank.subtractTwo(ResourceType.valueOf(h
									.getResource().toString()));
						}
					}

					if (map.getSettlements().containsKey(
							new VertexLocation(h.getLocation().getNeighborLoc(
									EdgeDirection.South),
									VertexDirection.NorthWest))) {
						if (bank.canChangeResource(ResourceType.valueOf(h
								.getResource().toString()), 1))
							;
						{
							p = map.getSettlements().get(
									new VertexLocation(
											h.getLocation().getNeighborLoc(
													EdgeDirection.South),
											VertexDirection.NorthWest));
							p.getResources().addOne(
									ResourceType.valueOf(h.getResource()
											.toString()));
							bank.subtractOne(ResourceType.valueOf(h
									.getResource().toString()));
						}
					} else if (map.getCities().containsKey(
							new VertexLocation(h.getLocation(),
									VertexDirection.NorthWest))) {
						if (bank.canChangeResource(ResourceType.valueOf(h
								.getResource().toString()), 2))
							;
						{
							p = map.getCities().get(
									new VertexLocation(h.getLocation(),
											VertexDirection.NorthWest));
							p.getResources().addTwo(
									ResourceType.valueOf(h.getResource()
											.toString()));
							bank.subtractTwo(ResourceType.valueOf(h
									.getResource().toString()));
						}
					}

					if (map.getSettlements().containsKey(
							new VertexLocation(h.getLocation().getNeighborLoc(
									EdgeDirection.SouthWest),
									VertexDirection.NorthEast))) {
						if (bank.canChangeResource(ResourceType.valueOf(h
								.getResource().toString()), 1))
							;
						{
							p = map.getSettlements().get(
									new VertexLocation(h.getLocation()
											.getNeighborLoc(
													EdgeDirection.SouthWest),
											VertexDirection.NorthEast));
							p.getResources().addOne(
									ResourceType.valueOf(h.getResource()
											.toString()));
							bank.subtractOne(ResourceType.valueOf(h
									.getResource().toString()));
						}
					} else if (map.getCities().containsKey(
							new VertexLocation(h.getLocation(),
									VertexDirection.NorthWest))) {
						if (bank.canChangeResource(ResourceType.valueOf(h
								.getResource().toString()), 2))
							;
						{
							p = map.getCities().get(
									new VertexLocation(h.getLocation(),
											VertexDirection.NorthWest));
							p.getResources().addTwo(
									ResourceType.valueOf(h.getResource()
											.toString()));
							bank.subtractTwo(ResourceType.valueOf(h
									.getResource().toString()));
						}
					}

					if (map.getSettlements().containsKey(
							new VertexLocation(h.getLocation().getNeighborLoc(
									EdgeDirection.SouthEast),
									VertexDirection.NorthWest))) {
						if (bank.canChangeResource(ResourceType.valueOf(h
								.getResource().toString()), 1))
							;
						{
							p = map.getSettlements().get(
									new VertexLocation(h.getLocation()
											.getNeighborLoc(
													EdgeDirection.SouthEast),
											VertexDirection.NorthWest));
							p.getResources().addOne(
									ResourceType.valueOf(h.getResource()
											.toString()));
							bank.subtractOne(ResourceType.valueOf(h
									.getResource().toString()));
						}
					} else if (map.getCities().containsKey(
							new VertexLocation(h.getLocation(),
									VertexDirection.NorthWest))) {
						if (bank.canChangeResource(ResourceType.valueOf(h
								.getResource().toString()), 2))
							;
						{
							p = map.getCities().get(
									new VertexLocation(h.getLocation(),
											VertexDirection.NorthWest));
							p.getResources().addTwo(
									ResourceType.valueOf(h.getResource()
											.toString()));
							bank.subtractTwo(ResourceType.valueOf(h
									.getResource().toString()));
						}
					}

				}
			}
			turnTracker.setStatus("playing");
			if (params.getNumber() == 8 || params.getNumber() == 11) {
				log.addLine(player.getName(), (player.getName() + " rolled an "
						+ params.getNumber() + "."));
			} else {
				log.addLine(player.getName(), (player.getName() + " rolled a "
						+ params.getNumber() + "."));
			}
		}
	}

	public void sendChat(SendChat_Input params) {
		version++;
		if(params.getContent().startsWith("!"))
		{
			Player p = getPlayerByIndex(params.getPlayerIndex());
			CheatInterpreter.instance().interpret(params.getContent(), p, this);
		}
		else
		{
			chat.addLine(getPlayerByIndex(params.getPlayerIndex()).getName(),
					params.getContent());
		}
	}

	public void soldier(Soldier_Input params) {
		version++;
		Player player = getPlayerByIndex(params.getPlayerIndex()); // player
																	// that did
																	// the
		player.setPlayedDevCard(true);								// robbing
		log.addLine(player.getName(),
				(player.getName() + " played a soldier card."));

		if (params.getVictimIndex() == -1) // no victim
		{
			log.addLine(
					player.getName(),
					(player.getName() + " moved the robber, but was unable to rob anyone."));
		} else {
			Player victim = getPlayerByIndex(params.getVictimIndex()); // victim
																		// of
																		// the
																		// robbing
			player.addCard(victim.getResources().getRandomCard()); // take
																	// random
																	// card from
																	// victim,
																	// give to
																	// player
			log.addLine(player.getName(),
					(player.getName() + " moved the robber, and robbed "
							+ victim.getName() + "."));
		}

		map.setRobber(params.getLocation()); // set robber to new location
		player.setSoldiers(player.getSoldiers() + 1);
		player.getOldDevCards().subtractDevCard(DevCardType.SOLDIER);
		if (player.getSoldiers() >= 3) // player could possible have largest
										// army
		{
			int mostSoldiers = 0; // most soldiers any of the other players have
			for (Player p : players) {
				if (p.getIndex() != player.getIndex()
						&& p.getSoldiers() > mostSoldiers) {
					mostSoldiers = p.getSoldiers();
				}
			}
			if (player.getSoldiers() > mostSoldiers) // player has more soldiers
														// than any other player
			{
				if (turnTracker.getLargestArmy() != -1) // if someone currently
														// has largestArmy
				{
					// remove victory points from previous largestArmy holder
					getPlayerByIndex(turnTracker.getLargestArmy())
							.subtract2VictoryPoints();
				}
				player.add2VictoryPoints(); // add victory points to player
				turnTracker.setLargestArmy(player.getIndex()); // update the
																// turn tracker
																// to reflect
																// new largest
																// army holder
			}
		}
		checkThenSetWinner(); // check if this move made a player win the game
	}

	public void yearOfPlenty(YearOfPlenty_Input params) {
		version++;
		Player p = getPlayerByIndex(params.getPlayerIndex());
		bank.subtractOne(params.getResource()); // remove resource 1 from bank
		bank.subtractOne(params.getResource1()); // remove resource 2 from bank
		p.addCard(params.getResource()); // add resource 1 to player
		p.addCard(params.getResource1()); // add resource 2 to player
		p.getOldDevCards().subtractDevCard(DevCardType.YEAR_OF_PLENTY); // remove
																		// dev
																		// card
																		// from
																		// player
		p.setPlayedDevCard(true); // let it be known they have played a dev card
		log.addLine(p.getName(), (p.getName()
				+ " played a Year of Plenty card, and got "
				+ params.getResource().toString().toLowerCase() + " and "
				+ params.getResource().toString().toLowerCase() + "."));
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// HELPTER METHODS FOR MODIFYING MODEL//
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * checks to see if the current game has a winner, if so updates the model
	 */
	public void checkThenSetWinner() {
		for (Player p : players) {
			if (p.getVictoryPoints() >= 10) {
				log.addLine(p.getName(), (p.getName() + " won the game. Nice."));
				setWinner(p.getIndex());
				return;
			}
		}
	}
	
	/**
	 * determines which player has the longest road
	 */
	public int getLongestRoadIndex(){
		int index = -1;
		int longestRoad = 0;
		
		//finds longest road of each player
		for(Player player: players)
		{
			System.out.println("Checking longest road for: " + player.getName());
			if(player.getRoadsPlayed()<5)
			{
				System.out.println("\tIgnored " + player.getName() + " (not enought roads built)");
				continue; //don't check until the player has 5+ roads
			}
			//checks for longest road beginning at each road
			try
			{
				System.out.println("\t" + player.getName() + " has at leat 5 roads");
				for(EdgeValue e : player.getBuiltRoads())
				{
					ArrayList<EdgeValue> edges = excludeEdge(player.getBuiltRoads(),e);
					VertexLocation[] vertices = e.getLocation().getAdjacentVertices();
					int temp = Math.max(rGetLongestRoad(vertices[0], edges), rGetLongestRoad(vertices[1], edges));
					if(temp > longestRoad)
					{
						longestRoad = temp;
						index = player.getIndex();
					}
					else if (temp == longestRoad && player.getIndex()==longestRoadIndex)
					{
						index = player.getIndex(); //keeps previous longest as winner
					}
				}
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		
		// finally, return the winner
		if(longestRoad < 5)
			return -1;
		else
			return index;
	}
	
	/**
	 * removes the provided edge from a list and returns a new list
	 * @param l - previous list
	 * @param e - edge to be removed
	 * @return
	 */
	private ArrayList<EdgeValue> excludeEdge(ArrayList<EdgeValue> l, EdgeValue e){
		ArrayList<EdgeValue> list = new ArrayList<EdgeValue>();
		for(EdgeValue edge : l){
			if(!edge.getLocation().equals(e.getLocation())){
				list.add(edge);
			}
		}
		return list;
	}
	
	/**
	 * recursive check for longest road from a given point
	 * @param v - current vertex to search from
	 * @param edges - remaining roads to search through
	 * @return
	 */
	private int rGetLongestRoad(VertexLocation v, ArrayList<EdgeValue> edges) {
		if(edges.size()==0){
			return 1; 			//base case, accounts for road just removed
		}
		ArrayList<Integer> permutations = new ArrayList<Integer>();
		for(EdgeValue edge : edges){
			//this checks whether the given edge is connected to our vertex of interest, if so, uses that edge as the next recursive call
			VertexLocation[] adjacentVertices = edge.getLocation().getAdjacentVertices();
			
			if(adjacentVertices[0].equals(v))
				permutations.add(1 + rGetLongestRoad(adjacentVertices[1], this.excludeEdge(edges, edge)));
			else if(adjacentVertices[1].equals(v))
				permutations.add(1 + rGetLongestRoad(adjacentVertices[0], this.excludeEdge(edges, edge)));
			else
				permutations.add(1);
		}
		
		// get max road length from all calls
		int output = 0;
		for(int i : permutations){
			if(i>output)
				output = i;
		}
		return output;
	}
	
	private void updateLongestRoadPoints()
	{
		// remove vp from previous player
		if(turnTracker.getLongestRoad() != -1)
			getPlayerByIndex(turnTracker.getLongestRoad()).subtract2VictoryPoints();
		try
		{
			// give points to new player
			getPlayerByIndex(this.longestRoadIndex).add2VictoryPoints();
			turnTracker.setLongestRoad(this.longestRoadIndex);
		}
		catch(NullPointerException e)
		{
			System.out.println("No player has built 5 roads yet");
		}
	}
}
