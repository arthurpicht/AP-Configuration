package de.arthurpicht.configuration;

/**
 * Common superclass of all RuntimeExceptions within Configuration project.
 * 
 * @author Arthur Picht
 *
 */
public class ConfigurationRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -3816933003393027420L;

	public ConfigurationRuntimeException() {
		super();
	}
	
	public ConfigurationRuntimeException(String message) {
		super(message);
	}
	
	public ConfigurationRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ConfigurationRuntimeException(Throwable cause) {
		super(cause);
	}

}
