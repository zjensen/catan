package client.communication;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import shared.communication.moves.SendChat_Input;
import shared.definitions.CatanColor;
import shared.models.MessageLine;
import shared.models.MessageList;
import shared.models.Player;
import client.base.Controller;
import client.session.SessionManager;

/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController,
		Observer {

	public ChatController(IChatView view) {

		super(view);

		initFromModel();

		SessionManager.instance().addObserver(this);
	}

	private void initFromModel() {

		update(SessionManager.instance(), null);
	}

	@Override
	public void update(Observable o, Object arg) {
		MessageList chats = SessionManager.instance().clientModel.getChat();

		ArrayList<LogEntry> entries = new ArrayList<>();
		if (chats != null) {
			for (MessageLine chatMessage : chats.getLines()) {
				String messageString = chatMessage.getMessage();
				String messageSource = chatMessage.getSource();

				CatanColor messageColor = null;
				for (Player player : SessionManager.instance()
						.getClientFacade().getClientModel().getPlayers()) {
					if (player.getName().equals(messageSource)) {
						messageColor = player.getCatanColor();
					}
				}

				entries.add(new LogEntry(messageColor, messageString));
			}
		}
		getView().setEntries(entries);
	}

	@Override
	public IChatView getView() {
		return (IChatView) super.getView();
	}

	@Override
	public void sendMessage(String message) {
		
		if(message.equals("!help"))
		{
			System.out.println("\n"+(char)27+"[42;30mWelcome to Catan's cheat mode:"+(char)27+"[0m");
			System.out.println("\tPossible commands are: "+(char)27+"[35m!give"+(char)27+"[0m"+" and "+(char)27+"[35m!roll"+(char)27+"[0m");
			System.out.println("\t\t"+(char)27+"[35;4m!give"+(char)27+"[0m must be followed by the object desired and the number desired");
			System.out.println("\t\t\tTo see a detailed list of possible objects, type "+(char)27+"[36m!help give"+(char)27+"[0m in chat");
			System.out.println("\t\t"+(char)27+"[35;4m!roll"+(char)27+"[0m must be followed by the number to be rolled");
			System.out.println("\t\t\tThis number will be rolled immediately");
			System.out.println("");
			System.out.println("\t"+(char)27+"[4mAny errors in cheat input will be ignored and not displayed"+(char)27+"[0m");
			System.out.println("\t\t"+(char)27+"[2mThat way nobody knows you are trying to cheat ;)"+(char)27+"[0m");
		}
		else if(message.equals("!help give"))
		{
			System.out.println("\n"+(char)27+"[42;30mDetails on "+(char)27+"[31mGIVE"+(char)27+"[30m cheat"+(char)27+"[0m");
			System.out.println("\tTo give yourself any number of resources, type "+(char)27+"[36m!give <resource_type> <number>"+(char)27+"[0m");
			System.out.println("\tTo give yourself any number of development cards, type "+(char)27+"[36m!give <dev_card> <number>"+(char)27+"[0m");
			System.out.println("\t\tDevelopment card types must match the following:");
			System.out.println("\t\t\t"+(char)27+"[33mknight, monument, monopoly, road_build, yop"+(char)27+"[0m");
			System.out.println("\tTo give yourself any number of victory points, type "+(char)27+"[36m!give vp <number>"+(char)27+"[0m");
			System.out.println("");
			System.out.println("\t"+(char)27+"[4mIf you attempt to take more from the bank "
					+ "or deck than possible, the request will be denied"+(char)27+"[0m");
		}
		else
		{
			int index = SessionManager.instance().getPlayerIndex();
	
			SendChat_Input chatMessage = new SendChat_Input(index, message);
	
			SessionManager.instance().getClientFacade().sendChat(chatMessage);
		}
	}

}
