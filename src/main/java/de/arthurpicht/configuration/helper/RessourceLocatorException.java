package de.arthurpicht.configuration.helper;

/**
 * ACHTUNG: Diese Klasse ist eine Kopie von AP-Util/de/arthurpicht/util/io/, erzeugt beim
 * Rausziehen des AP-Configuration-Projektes.
 * 
 * @author Arthur Picht, (c) 2017
 *
 */
public class RessourceLocatorException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5774751228682794310L;

	public RessourceLocatorException(String message) {
		super(message);
	}
	
	public RessourceLocatorException(Exception cause) {
		super(cause);
	}
	
	public RessourceLocatorException(Exception cause, String message) {
		super(message, cause);
	}

}
