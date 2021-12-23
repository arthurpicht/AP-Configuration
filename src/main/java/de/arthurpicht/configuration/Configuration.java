package de.arthurpicht.configuration;

import java.util.List;
import java.util.Set;

/**
 * Ein Objekt der Klasse Configuration repräsentiert den Inhalt einer oder mehrerer
 * Konfigurationsdateien. Ein Objekt vom Typ Configuration sollte durch Nutzung der
 * Klasse ConfigurationFactory erzeugt werden.
 * Die repräsentierten Konfigurationsdateien entsprechend erweiterten Properties-Files.
 * Sie sind in folgender Weise spezifiziert:
 * 1. Sie enthalten Schlüssel/Werte-Paare mit '=' als Zuweisungsoperator.
 * Z.B. 'port = 4711'.
 * 2. Ein führendes '#'-Zeichen zeichnet die Zeile als Kommentar aus.
 * 3. Führendes oder folgendes White-Space bleibt unberücksichtigt.
 * 4. Unicode-Zeichen werden durch /u???? mit '????' als Code-Wert ausgezeichnet.
 * 5. Es lassen sich listenartige Zuordnungen von Schlüssel und Wertelisten erzeugen.
 * Das Trennzeichen ist per Voreinstellung auf ',' festgelegt und kann geändert werden.
 * Z.B. 'colors = yellow, grey, green'
 * 6. Es können im 'Ant-Style' bei der Definition von Werten Referenzen auf andere Schlüssel
 * genutzt werden. Z.B. 'welcomeString = Server XY in der Version ${version}', wobei 'version'
 * einem definierten Schlüssel entspricht. ACHTUNG: Keine zirkulären Referenzen definieren!
 * <br/>
 * Änderungshistorie: <ul>
 * <li>2007 erstellt (Mallorca)</li>
 * <li>2013 erweitert: Sektionen</li>
 * </ul>  
 *   
 * @author Arthur Picht, Arthur Picht GmbH, (c) 2007 - 2013
 * 
 * 
 *
 */
public interface Configuration {
	
	/**
	 * Liefert alle in der Konfiguration vertretenen Schlüssel
	 * 
	 * @return Menge alle Schlüssel
	 */
	public Set<String> getKeys();
	
	/**
	 * Prüft, ob der übergebene Schlüssel in der Konfiguration
	 * vertreten ist.
	 * 
	 * @param key Schlüssel
	 * @return
	 */
	public boolean containsKey(String key);
	
	/**
	 * Liefert einen Konfigurationswert vom Basisdatentyp 'boolean' für den
	 * übergebenen Schlüssel. 
	 * 
	 * @param key Schlüssel
	 * @return
	 */
	public boolean getBoolean(String key);
	
	/**
	 * Liefert den Konfigurationswert vom Basistyp 'boolean' für die übergebene
	 * Konfigurationsvariable. Der übergebene Defaultwert wird zurück geliefert, 
	 * falls die Konfigurationsvariable nicht definiert oder deren Wert nicht 
	 * korrekt geparst werden kann.
	 * 
	 * @param configVarName
	 * @param defaultValue
	 * @return
	 */
	public boolean getBoolean(String configVarName, boolean defaultValue);
	
	/**
	 * Liefert einen Konfigurationswert vom Basisdatentyp 'int' für den
	 * übergebenen Schlüssel.
	 * 
	 * @param key Schlüssel
	 * @return
	 */
	public int getInt(String key);
	
	/**
	 * Liefert den Konfigurationswert vom Basistyp 'int' für die übergebene
	 * Konfigurationsvariable. Der übergebene Defaultwert wird zurück geliefert, 
	 * falls die Konfigurationsvariable nicht definiert oder deren Wert nicht 
	 * korrekt geparst werden kann.
	 * 
	 * @param configVarName
	 * @param defaultValue
	 * @return
	 */
	public int getInt(String configVarName, int defaultValue);
	
	/**
	 * Liefert einen Konfigurationswert vom Typ 'String' für den
	 * übergebenen Schlüssel.
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key);
	
	/**
	 * Liefert den Konfigurationswert vom Type 'String' für die übergebene
	 * Konfigurationsvariable. Der übergebene Defaultwert wird zurück geliefert,
	 * falls die Konfigurationsvariable nicht definiert ist.
	 *
	 * @param configVarName
	 * @param defaultValue
	 * @return
	 */
	public String getString(String configVarName, String defaultValue);

	/**
	 * Liefert einen Konfigurationswert vom Basisdatentyp 'double' für den
	 * übergebenen Schlüssel.
	 * 
	 * @param key
	 * @return
	 */
	public double getDouble(String key);
	
	/**
	 * Liefert den Konfigurationswert vom Basistyp 'double' für die übergebene
	 * Konfigurationsvariable. Der übergebene Defaultwert wird zurück geliefert, 
	 * falls die Konfigurationsvariable nicht definiert oder deren Wert nicht 
	 * korrekt geparst werden kann.
	 * 
	 * @param configVarName
	 * @param defaultValue
	 * @return
	 */
	public double getDouble(String configVarName, double defaultValue);
	
	/**
	 * Liefert eine Liste mit Elementen vom Typ String für den
	 * übergebenen Schlüssel.
	 * 
	 * @param key Schlüssel
	 * @return Werteliste mit Elementen vom Typ String
	 */
	public List<String> getStringList(String key);
	
	/**
	 * Liefert eine Liste mit Elementen vom Typ String für den übergebenen Schlüssel.
	 * Falls ein entsprechender Konfigurationsschlüssel nicht definiert ist oder
	 * nicht korrekt geparst werden kann, wird eine Liste mit dem übergebenen Default-
	 * Wert als einzigem Element zurückgeliefert.
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public List<String> getStringList(String key, String defaultValue);

	/**
	 * Liefert eine Liste mit Elementen vom Typ String für den übergebenen Schlüssel.
	 * Falls ein entsprechender Konfigurationsschlüssel nicht definiert ist oder
	 * nicht korrekt geparst werden kann, wird eine Liste mit den übergebenen
	 * Defaultwerten zurückgeliefert.
	 *
	 * @param key
	 * @param defaultValues
	 * @return
	 */
	public List<String> getStringList(String key, String... defaultValues);

	/**
	 * Liefert den Namen der Sektion der dieses Konfigurationsobjekt entspricht.
	 * 
	 * @return
	 */
	public String getSectionName();
	
}
