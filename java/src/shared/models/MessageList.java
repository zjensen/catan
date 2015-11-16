package shared.models;

public class MessageList {
	
	private MessageLine[] lines = new MessageLine[0];
	
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


	public void addLine(String name, String content)
	{
		MessageLine[] updatedLines = new MessageLine[lines.length+1];
		for(int i=0;i<lines.length;i++)
		{
			updatedLines[i] = lines[i];
		}
		updatedLines[lines.length] = new MessageLine(name, content);
		this.lines = updatedLines;
	}

}
