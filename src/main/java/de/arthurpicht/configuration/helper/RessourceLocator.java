package de.arthurpicht.configuration.helper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * ACHTUNG: Diese Klasse ist eine Kopie von AP-Util/de/arthurpicht/util/io/, erzeugt beim
 * Rausziehen des AP-Configuration-Projektes.
 * 
 * Lokalisiert eine Datei über ihre Pfadangabe im Dateisystem oder alternativ über ihre Pfadangabe innerhalb des Klassnepfades.
 * 
 * @author Arthur Picht, Arthur Picht GmbH, 2008
 *
 */
public class RessourceLocator {
	
	private URL url;
	
	/**
	 * Erzeugt RessourceLocator. Zur sinnvollen Anwendung Factorymethoden nutzen.<p>
	 * Gültige URLs sind z.B.: "http://example.org/myFile.xml" oder "file:c://myschemas/myschema.xsd" oder "jar:file:/path-to-jar-file!/path-within-jar-file".
	 * 
	 * @param url
	 */
	public RessourceLocator(URL url) {
		this.url = url;
	}
	
	/**
	 * Factorymethode. Liefert RessourceLocator-Instanz über Pfadangabe innerhalb des Klassenpfades.
	 * 
	 * @param pathname
	 * @return
	 * @throws RessourceLocatorException
	 */
	public static RessourceLocator bindRessourceFromClasspath(String pathname) throws RessourceLocatorException {
		URL classpathURL = RessourceLocator.class.getClassLoader().getResource(pathname);
		if (classpathURL == null) {
			throw new RessourceLocatorException("Ressource '" + pathname + "' konnte nicht im Klassenpfad gefunden werden.");
		}
		return new RessourceLocator(classpathURL);
	}
	
	/**
	 * Factorymethode. Liefert RessourceLocator-Instanz über die Angabe einer Ressource, die vom Classloader
	 * eines spezifizierten Ojektes geladen wird. Ist geeignet um z.B. Ressourcen im selben Package einer bestimmten
	 * Klasse zu binden.
	 * 
	 * @param object
	 * @param name
	 * @return
	 * @throws RessourceLocatorException
	 */
	public static RessourceLocator bindRessourceFromObjectClasspath(Object object, String name) throws RessourceLocatorException {
		URL classpathURL = object.getClass().getResource(name);
		if (classpathURL == null) {
			throw new RessourceLocatorException("Ressource '" + name + "' konnte nicht vom Classloader der Klasse " + object.getClass().getCanonicalName() + " gefunden werden.");
		}
		return new RessourceLocator(classpathURL);
	}
	
	/**
	 * Factorymethode. Liefert RessourceLocator-Instanz über Pfadangabe innerhalb des Dateisystems.
	 * 
	 * @param pathname
	 * @return
	 * @throws RessourceLocatorException
	 */
	public static RessourceLocator bindRessourceFromFileSystem(String pathname) throws RessourceLocatorException {
		
		File file = new File(pathname);
		try {
			URL url = file.toURI().toURL();
//			URL url = file.toURL();
			return new RessourceLocator(url);
		} catch (MalformedURLException e) {
			throw new RessourceLocatorException(e);
		}
		
	}
	
	/**
	 * Liefert URL der Ressource.
	 * 
	 * @return
	 */
	public URL getURL() {
		return this.url;
	}
	
	/**
	 * Liefert einen geöffneten InputStream auf die referenzierte Ressource zurück.
	 * 
	 * @return
	 * @throws IOException
	 */
	public InputStream getInputStream() throws IOException {
		return this.url.openStream();
	}
	
	// Das hier funktioniert NICHT für Files z.B. in JARS, deshalb kommentiert.
//	/**
//	 * Liefert File-Instanz der Ressource.
//	 * 
//	 * @return
//	 * @throws RessourceLocatorException
//	 */
//	public File getFile() throws RessourceLocatorException {
//		try {
//			return new File(this.url.toURI());
//		} catch (URISyntaxException e) {
//			throw new RessourceLocatorException(e);
//		}
//	}

	
	public static void main(String[] args) {
		
//		URL url = RessourceLocator.class.getClassLoader().getResource("de/arthurpicht/util/io/RessourceLocator.class");
//		System.out.println(url);
//		System.exit(0);
		
		try {
//			RessourceLocator locator1 = RessourceLocator.bindRessourceFromFileSystem("/CibRegSvr.log");
//			RessourceLocator locator1 = RessourceLocator.bindRessourceFromClasspath("AnalysisTest1.xml");
			RessourceLocator locator1 = RessourceLocator.bindRessourceFromClasspath("de/arthurpicht/util/io/RessourceLocator.class");
//			System.out.println("exists? " + locator1.getFile().exists());
			System.out.println("url: " + locator1.getURL().toString());
		} catch (RessourceLocatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
