package de.arthurpicht.configuration;

public class NoSuchKeyException extends ConfigurationRuntimeException {

	private static final long serialVersionUID = -1328146227566845915L;
	
	public NoSuchKeyException() {
		super();
	}
	
	public NoSuchKeyException(String message) {
		super(message);
	}
	
	public NoSuchKeyException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public NoSuchKeyException(Throwable cause) {
		super(cause);
	}

}
