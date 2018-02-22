package de.arthurpicht.configuration.demo;


import de.arthurpicht.configuration.Configuration;
import de.arthurpicht.configuration.ConfigurationFactory;
import de.arthurpicht.configuration.ConfigurationFileNotFoundException;

import java.io.File;
import java.io.IOException;

public class ConfigurationDemo {
	
	public static void main(String[] args) {
		
		ConfigurationFactory configurationFactory = new ConfigurationFactory();
		try {
			configurationFactory.addFilesystemConfiguration(new File("test23.conf"));
		} catch (ConfigurationFileNotFoundException | IOException e) {
			e.printStackTrace();
		}

		configurationFactory.list();
		
		Configuration configuration = configurationFactory.getConfiguration();
		if (configuration == null) {
			System.out.println("Configuration ist null");
		}
		
	}

}
