package de.arthurpicht.configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class ConfigurationParser {
	
	// Sections der Konfiguration: Key = SectionName, Values = Zeilen der Konfigurationsdatei
	private Map<String, List<String>> configSections;
	
	private ConfigurationSectionProperties configurationSectionProperties;
	
	
	public ConfigurationParser(InputStream inputStream) throws IOException {
		
		this.configSections = new HashMap<>();
		this.configurationSectionProperties = new ConfigurationSectionProperties();
		
		this.splitToSectionsAndLines(inputStream);
		
		this.buildConfigurationSections();
		
//		this.debugOutSectionsAndLines();
		
	}
	
	public ConfigurationSectionProperties getConfigurationSectionProperties() {
		return this.configurationSectionProperties;
	}
	
	/**
	 * Zerlegt die Konfigurationsdatei in benannte Sektionen (sections)
	 * und zugehörige Zeilen. Bei der Zuordnung der Zeilen werden Kommentare
	 * und leere Zeilen aussortiert.
	 * 
	 * @param inputStream
	 * @throws IOException
	 */
	private void splitToSectionsAndLines(InputStream inputStream) throws IOException {
		
		List<String> configLines = new ArrayList<String>();
		String sectionName = new String();
		
		if (inputStream == null) {
			// Wenn der übergebene Stream null ist wird einfach
			// die leere Liste zurückgegeben.
			this.configSections.put(sectionName, configLines);
			return;
		}
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String line = new String();
		
		while(line != null) {
			line = bufferedReader.readLine();
			if (line != null) {
				line = line.trim();
				if (line.startsWith("[") && line.endsWith("]")) {
					String sectionNameNext = line.substring(1, line.length() - 1);
					
					// Unter folgenden Bedingungen werden gespeicherte Zeilen als ConfigSection gespeichert.
					// 1. Es existiert eine SectionName - ob Zeilen bestehen ist egal
					// 2. Es existiert kein SectionName, Anzahl der Zeilen ist > 0.
					//    Es kann sich in diesem Falle nur um die erste Section handeln,
					//    da jede weitere durch einen Namen ausgezeichnet ist.
					
					if (!sectionNameNext.equals("")) {
						
						if (!sectionName.equals("") || (configLines.size() > 0)) {
							this.configSections.put(sectionName, configLines);							
							configLines = new ArrayList<String>();
						}
						
						sectionName = sectionNameNext;
						
					} else {
						// Kein gültiger Name für eine Section da leer
						// System.out.println("Leerer SectionName -> ignoriert.");
					}
				} else if ((!line.startsWith(Character.toString(Parameters.getCommentChar()))) && (!line.equals(""))) {
					configLines.add(line);
				}
			}
		}
		
		// Zum Abschluss letzte Section hinzufügen, wenn sie ein benannte Section ist ODER
		// Wenn es die erste Section ist und Zeilen vorliegen.
		
		if (!sectionName.equals("") || (this.configSections.size() == 0 && configLines.size() > 0)) {
			this.configSections.put(sectionName, configLines);
		} 
		
	}
	
	// Baut aus jeder Sektion und der ihr zugeordneten Zeilen
	// ein ConfigurationProperties-Objekt
	private void buildConfigurationSections() {
		
		Set<String> sectionNames = this.configSections.keySet();
		
		for (String sectionName : sectionNames) {
			List<String> configLines = this.configSections.get(sectionName);
			ConfigurationProperties configurationProperties = new ConfigurationProperties(sectionName, configLines);
			
			this.configurationSectionProperties.putSection(configurationProperties);
			
		}
	}
	
//	private void debugOutSectionsAndLines() {
//		Set<String> keySet = this.configSections.keySet();
//		
//		for (String sectionName : keySet) {
//			
//			System.out.println("Section [" + sectionName + "]");
//			
//			List<String> configLines = this.configSections.get(sectionName);
//			for (String configLine : configLines) {
//				System.out.println(configLine);
//			}
//		}
//	}

}
