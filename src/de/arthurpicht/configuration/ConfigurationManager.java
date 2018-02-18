package de.arthurpicht.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConfigurationManager implements Configuration {
	
	private static final String KEY_IS_NULL_EXCEPTION = "Key ist null or empty.";
	
	private ConfigurationProperties configurationProperties;
	
	public ConfigurationManager(ConfigurationProperties configurationProperties) {
		this.configurationProperties = configurationProperties;
//		System.out.println("Initialisierung ConfigurationManager. configProperties.size = " + this.configurationProperties.getKeys().size());
	}

	public Set<String> getKeys() {
		return this.configurationProperties.getKeys();
	}
	
	public boolean containsKey(String key) {
		return this.configurationProperties.containsKey(key);
	}

	public boolean getBoolean(String key) {
		this.keyConsistencyCheck(key);		
		String value = this.configurationProperties.getProperty(key);		
		try {
			return Boolean.parseBoolean(value);				
		} catch (NumberFormatException nfEx) {
			throw new ParseException("Cannot parse '" + value + "' to boolean.");
		}
	}

	public double getDouble(String key) {
		this.keyConsistencyCheck(key);
		
		String value = this.configurationProperties.getProperty(key);
		try {
			return Double.parseDouble(value);				
		} catch (NumberFormatException nfEx) {
			throw new ParseException("Cannot parse '" + value + "' to double.");
		}
	}

	public int getInt(String key) {
		this.keyConsistencyCheck(key);
		
		String value = this.configurationProperties.getProperty(key);
		try {
			return Integer.parseInt(value);				
		} catch (NumberFormatException nfEx) {
			throw new ParseException("Cannot parse '" + value + "' to interger.");
		}
	}

	public String getString(String key) {
		this.keyConsistencyCheck(key);
		return this.configurationProperties.getProperty(key);
	}
	
	public List<String> getStringList(String key) {
		this.keyConsistencyCheck(key);
		
		return this.configurationProperties.getPropertyList(key);
	}
	
	/**
	 * Prüft ob Schlüsse konsistent ist: nicht 'null', nicht leer
	 * und in der Konfiguration vorhanden.
	 * 
	 * @param key
	 */
	private void keyConsistencyCheck(String key) {
		if (key == null || key.equals("")) {
			throw new ParseException(KEY_IS_NULL_EXCEPTION);
		}
		if (!this.configurationProperties.containsKey(key)) {
			throw new NoSuchKeyException("Variable '" + key + "' in dieser Konfiguration nicht definiert.");
		}
	}

	public String getString(String configVarName, String defaultValue) {
		try {
			String configValue = this.getString(configVarName);
			return configValue;
		} catch (NoSuchKeyException ex) {
			return defaultValue;
		}
	}
	
	public boolean getBoolean(String configVarName, boolean defaultValue) {
		try {
			boolean configVarValue = this.getBoolean(configVarName);
//			System.out.println("parsed: " + configVarValue);
			return configVarValue;
		} catch (NoSuchKeyException ex) {
			return defaultValue;
		} catch (ParseException ex) {
			return defaultValue;
		}
	}

	public double getDouble(String configVarName, double defaultValue) {
		try {
			double configVarValue = this.getDouble(configVarName);
			return configVarValue;
		} catch (NoSuchKeyException ex) {
			return defaultValue;
		} catch (ParseException ex) {
			return defaultValue;
		}
	}

	public int getInt(String configVarName, int defaultValue) {
		try {
			int configVarValue = this.getInt(configVarName);
			return configVarValue;
		} catch (NoSuchKeyException ex) {
			return defaultValue;
		} catch (ParseException ex) {
			return defaultValue;
		}
	}
	
	public List<String> getStringList(String key, String defaultValue) {
		try {
			List<String> stringList = this.getStringList(key);
			return stringList;
		} catch (NoSuchKeyException ex) {
			List<String> stringList = new ArrayList<String>();
			stringList.add(defaultValue);
			return stringList;
		} catch (ParseException ex) {
			List<String> stringList = new ArrayList<String>();
			stringList.add(defaultValue);
			return stringList;
		}
	}

//	public OrderedTree<HierarchyContainer> getHierarchyTree(String rootElementName) {
//		throw new RuntimeException("Not implemented yet!");
////		return this.configurationProperties.getHierarchyTree(rootElementName);
//	}

	@Override
	public String getSectionName() {
		return this.configurationProperties.getSectionName();
	}
	
	

}
