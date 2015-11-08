package shared.models;

public class MessageLine {
	
	private String source;
	private String message;
	
	public MessageLine(String source, String message)
	{
		this.source = source;
		this.message = message;
	}
	
	public MessageLine()
	{
		
	}
	
	@Override
	public String toString() {
		return "MessageLine  [source=" + source 
							+ "\n\t\tmessage=" + message + "   ]";
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}

}
