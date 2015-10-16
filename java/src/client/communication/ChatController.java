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

		int index = SessionManager.instance().getPlayerIndex();

		SendChat_Input chatMessage = new SendChat_Input(index, message);

		SessionManager.instance().getClientFacade().sendChat(chatMessage);
	}

}
