package de.arthurpicht.configuration;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConfigurationSectionProperties {
	
	private ConfigurationProperties templateSection;
	private Map<String, ConfigurationProperties> sections;
	
	public ConfigurationSectionProperties() {
		this.templateSection = null;
		this.sections = new HashMap<String, ConfigurationProperties>();
	}
	
	public void putSection(ConfigurationProperties configurationProperties) {
		if (configurationProperties.getSectionName().equals("*")) {
			
//			System.out.println("[ConfigSectionProp] template gefunden");
			
			this.templateSection = configurationProperties;
		} else {
			
//			System.out.println("[ConfigSectionProp] setion gefunden: " + configurationProperties.getSectionName());
			
			this.sections.put(configurationProperties.getSectionName(), configurationProperties);
		}
	}
	
	public Set<String> getSectionNames() {
		return this.sections.keySet();
	}
	
	public int getNrOfSections()  {
		return this.sections.size();
	}
	
	public boolean hasTemplateSection() {
		if (this.templateSection == null) {
			return false;
		}
		return true;
	}
	
	public boolean hasSection(String sectionName) {
		return this.sections.containsKey(sectionName);
	}
	
	public ConfigurationProperties getConfigurationProperties(String sectionName) {
		return this.sections.get(sectionName);
	}
	
	public ConfigurationProperties getTemplateSection() {
		return this.templateSection;
	}
	
	/**
	 * Führt ein Override durch. Dabei werden die hier vorliegenden Sektionen und Properties
	 * mit den übergebenen Sektionen und Properties überschrieben. Es gelten folgende Regeln:
	 * <ul><li>Das Überschreiben erfolgt sektionsbasiert und für jede Sektion unabhängig.</li>
	 * <li>Wenn eine übergebene Sektion im lokalen Datenbestand noch nicht exisitert, wird sie
	 * aus den übergebenen Sektionen übernommen.</li>
	 * <li>Wenn im letztgenannten Fall eine Vorlagen-Sektion (Template) vorbestehend ist, dann
	 * wird diese überschrieben.</li>
	 * <li>Enthalten die übergebenen Sektionen eine Vorlagen-Sektion (Template), dann wird diese
	 * als neue Vorlage übernommen. Allerdings erst nach Durchführung der Überschreibungen.</li>
	 * </ul>
	 * 
	 * @param configurationSectionProperties
	 */
	public void override(ConfigurationSectionProperties configurationSectionProperties) {
		
		Set<String> sectionNamesPara = configurationSectionProperties.getSectionNames();
		for (String sectionNamePara : sectionNamesPara) {
			ConfigurationProperties configurationPropertiesPara = configurationSectionProperties.getConfigurationProperties(sectionNamePara);
			
			// Es wird eine vorbestehende Sektion überschrieben
			if (this.sections.containsKey(sectionNamePara)) {
				ConfigurationProperties configurationPropertiesLocal = this.getConfigurationProperties(sectionNamePara);
				configurationPropertiesLocal.override(configurationPropertiesPara);
				
			// Es wird eine nicht vorbestehende Sektion überschrieben/übernommen UND Template exisitert
			// -> Template mit Sektion überschreiben
			} else if (this.hasTemplateSection()) {
				ConfigurationProperties configurationPropertiesLocal = this.templateSection;
				ConfigurationProperties configurationProperteisLocalCopy = configurationPropertiesLocal.getDeepCopy(sectionNamePara);
				configurationProperteisLocalCopy.override(configurationPropertiesPara);
				this.putSection(configurationProperteisLocalCopy);
				
			// Es wird eine nicht vorbestehende Sektion überschrieben/übernommen UND Template exisitert NICHT
			// -> Sektion einfach übernehmen
			} else {
				this.putSection(configurationPropertiesPara);
			}
		}
		
		// Wenn Template mit übergeben wurde, dann jetzt nach Durchführung der Überschreibung
		// akutelles Template übernehmen. 
		if (configurationSectionProperties.hasTemplateSection()) {
			this.templateSection = configurationSectionProperties.getTemplateSection();
		}
		
	}
	
	public void list(PrintStream out) {
		Set<String> sectionNames = this.getSectionNames();
		
		for (String sectionName : sectionNames) {			
			ConfigurationProperties configurationProperties = this.getConfigurationProperties(sectionName);
			configurationProperties.list(out);
		}
		
		
	}
		
}
