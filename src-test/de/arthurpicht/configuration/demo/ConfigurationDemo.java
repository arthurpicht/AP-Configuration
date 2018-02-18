package de.arthurpicht.configuration.demo;


import de.arthurpicht.configuration.Configuration;
import de.arthurpicht.configuration.ConfigurationFactory;

public class ConfigurationDemo {
	
	public static void main(String[] args) {
		
		ConfigurationFactory configurationFactory = new ConfigurationFactory();
		configurationFactory.addConfiguration("test23.conf");
				
		configurationFactory.list();
		
		Configuration configuration = configurationFactory.getConfiguration();
		if (configuration == null) {
			System.out.println("Configuration ist null");
		}
		
	}

}
