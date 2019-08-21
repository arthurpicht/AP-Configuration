package de.arthurpicht.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ValueExpander {
	
	private static final int INTERNAL = 1;
	private static final int ENVIRONMENT = 2;
	
	
	/**
	 * Löst die Verwendung von Variablenreferenzen innerhalb der Wertedefinitionen
	 * nach Ant-Style auf.
	 */
	public static void expandVariables(ConfigurationProperties configurationProperties) {
		Set<String> keys = configurationProperties.getKeys();
		for (String key : keys) {
			List<String> valueList = configurationProperties.getPropertyList(key);
			List<String> expandedValueList = new ArrayList<String>();
			for (String value : valueList) {
				String expandedValue = substitute(configurationProperties, value, INTERNAL);
				expandedValue = substitute(configurationProperties, expandedValue, ENVIRONMENT);
				expandedValueList.add(expandedValue);
			}
			valueList = expandedValueList;
			configurationProperties.put(key, expandedValueList);
		}
	}
	
	/**
	 * Löst innerhalb einer Wertedefinition Referenzen auf Umgebungsvariablen auf.
	 * 
	 * @param value
	 * @return
	 */
	public static String expandEnvironmentProperties(String value) {
		// Übergabe von 'null' für configurationProperties, die hier nicht gebraucht werden
		// und auch nicht zur Verfügung stehen ist 'dreckig' programmiert.
		
		return substitute(null, value, ENVIRONMENT);
		
	}
	
	/**
	 * Führt eine Ersetzung von Variablen-Referenzen innerhalb der Variablenwertdefinition
	 * durch die entsprechenden Werte durch. Es können per Escape-Sequenz '${myvar}' innerhalb
	 * der Konfigurationsdatei definierte Variablen (hier 'myvar') referenziert werden.
	 * Ebenso können Umgebungsvariablen per Escape-Sequenz '$env{myvar}' referenziert werden.
	 * 
	 * @param configurationProperties Configuration, auf deren Basis die Substitution erfolgen soll.
	 * @param value Konfigurationswert, der verarbeitet werden soll.
	 * @param varType Typ der Referenz, die aufgelöst werden soll.
	 * @return Substituierter Konfigurationswert.
	 */
	private static String substitute(ConfigurationProperties configurationProperties, String value, int varType) {
		
		// Zunächst den Typ der zu substituierenden Variablen prüfen.
		// Bei Erweiterung darauf achten, dass 'varType' unter allen Umständen
		// einen konsistenten Wert hat, im Zweifel immer 'INTERNAL'.
		String escapeStartSequence = "";
		if (varType == ENVIRONMENT) {
			escapeStartSequence = "$env{";
		} else {
			varType = INTERNAL;
			escapeStartSequence = "${";
		}
		
//		System.out.println("Value: " + value + "; varType: " + varType + "; eSS: " + escapeStartSequence);
		
		int i = 0;
		while (i >= 0) {
			// Suche Start-Escape-Sequenz für Variablen '${'
			i = value.indexOf(escapeStartSequence, i);
			
//			System.out.println(escapeStartSequence + " gefunden, Index :" + i);
			
			if (i >= 0) {
				// Start-Escape-Sequenz gefunden!
				// Suche Stop-Escape-Sequenz für Variablen '}'
				// ab Fundstelle der Start-Sequenz
				int j = value.indexOf("}", i);
				if (j >= 0) {
					// Stop-Escape-Sequenz gefunden!
					// Ermittle Variablen-Namen
					String varName = value.substring(i + escapeStartSequence.length(), j);
					if (!varName.equals("")) {
						// Variablen-Namen ist nicht leer!
						// Löse Variable auf und ersetze vollständige Escape-Sequenz 
						// durch Variablen-Wert
						
//						System.out.println("var gefunden: '" + varName + "'");
						
						String varValue = new String();
						if (varType == INTERNAL) {
							varValue = configurationProperties.getProperty(varName);
//							String varValue = (String) this.properties.get(varName);														
						} else if (varType == ENVIRONMENT) {
							varValue = System.getenv(varName);
							if (varValue == null) {
								varValue = new String();
							}
						}
						
//						System.out.println("varValue: " + varValue);
//						System.out.println(System.getProperty("test_var"));
						
						value = value.substring(0, i) + varValue + value.substring(j + 1, value.length());
						
					}					
				} else {
					// Stop-Escape-Sequenz nicht gefunden!
					// Rücke Zähler eins vor, damit die aktuelle Start-Sequenz nicht
					// wieder gefunden wird (Endlosschleife).
					// Im Fund-Fall (s.o.) wird der Zähler nicht verändert, da die Escape-Sequenz ja
					// vollständig ersetzt wird.
					i++;
				}
			}
		}
		
//		System.out.println("ValueExp.substitute Rückgabe: " + value);
		return value;
	}

	

}
