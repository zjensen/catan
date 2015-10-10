package client.communication;

import java.util.*;

import client.base.*;
import client.session.SessionManager;
import shared.definitions.*;
import shared.models.MessageLine;
import shared.models.MessageList;
import shared.models.Player;


/**
 * Game history controller implementation
 */
public class GameHistoryController extends Controller implements IGameHistoryController, Observer {

	public GameHistoryController(IGameHistoryView view) {
		
		super(view);
		
		initFromModel();
		
		SessionManager.instance().addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		MessageList log = SessionManager.instance().clientModel.getLog();

		ArrayList<LogEntry> entries = new ArrayList<>();
		if (log != null) {
			for (MessageLine logMember : log.getLines()) {
				String messageString = logMember.getMessage();
				String messageSource = logMember.getSource();

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
	public IGameHistoryView getView() {
		
		return (IGameHistoryView)super.getView();
	}
	
	private void initFromModel() {
		
		//<temp>
		
		List<LogEntry> entries = new ArrayList<LogEntry>();
		entries.add(new LogEntry(CatanColor.BROWN, "Davis sucks"));
		entries.add(new LogEntry(CatanColor.RED, "Zack sucks"));
		entries.add(new LogEntry(CatanColor.ORANGE, "Trevor sucks"));
		entries.add(new LogEntry(CatanColor.PUCE, "Anthony sucks"));
		entries.add(new LogEntry(CatanColor.GREEN, "Ian sucks"));
		
		getView().setEntries(entries);
	
		//</temp>
	}
	
}

