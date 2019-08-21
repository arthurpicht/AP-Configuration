package de.arthurpicht.configuration;

public class ParseException extends ConfigurationRuntimeException {

	private static final long serialVersionUID = -1328146227566845915L;
	
	public ParseException() {
		super();
	}
	
	public ParseException(String message) {
		super(message);
	}
	
	public ParseException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ParseException(Throwable cause) {
		super(cause);
	}

}
