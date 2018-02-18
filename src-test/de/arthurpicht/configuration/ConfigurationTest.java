package de.arthurpicht.configuration;

import de.arthurpicht.configuration.Configuration;
import de.arthurpicht.configuration.ConfigurationFactory;

import java.util.List;

public class ConfigurationTest {
	
	public static void main(String[] args) {
		ConfigurationFactory configurationFactory = new ConfigurationFactory();
		configurationFactory.addConfigurationFromFilesystem("test-resrc/test.conf");
		configurationFactory.addConfigurationFromFilesystem("test-resrc/test2.conf");
		configurationFactory.addConfigurationFromClasspath("test3.conf");
		
		Configuration myConfiguration = configurationFactory.getConfiguration();
		System.out.println("wahrheit: " + myConfiguration.getBoolean("wahrheit"));
		System.out.println("integer : " + myConfiguration.getInt("intzahl"));
		System.out.println("double  : " + myConfiguration.getDouble("doublezahl"));
		
		List<String> valueList = myConfiguration.getStringList("liste");
		for (String element : valueList) {
			System.out.println("Element : '" + element + "'");
		}
		
		System.out.println("interpol : '" + myConfiguration.getString("interpol") + "'");
	}

}
