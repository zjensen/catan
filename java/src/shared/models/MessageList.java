package shared.models;

public class MessageList {
	
	private MessageLine[] lines;
	
	/**
	 * update any new notifications
	 * 
	 * @param lines -> new messageList contents
	 */
	public void update(MessageLine[] lines)
	{
		
	}

	public MessageLine[] getLines() {
		return lines;
	}

	public void setLines(MessageLine[] lines) {
		this.lines = lines;
	}

}
