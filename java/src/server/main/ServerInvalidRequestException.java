package server.main;

@SuppressWarnings("serial")
public class ServerInvalidRequestException extends Exception {
	
	public ServerInvalidRequestException() {
		return;
	}

	public ServerInvalidRequestException(String message) {
		super(message);
	}

	public ServerInvalidRequestException(Throwable cause) {
		super(cause);

	}

	public ServerInvalidRequestException(String message, Throwable cause) {
		super(message, cause);
	}
}