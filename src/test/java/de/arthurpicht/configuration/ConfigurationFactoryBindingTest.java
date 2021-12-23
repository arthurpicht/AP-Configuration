package de.arthurpicht.configuration;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class ConfigurationFactoryBindingTest {

    @Test
    public void addConfigurationFromFilesystem() throws IOException, ConfigurationFileNotFoundException {
        ConfigurationFactory configurationFactory = new ConfigurationFactory();
        configurationFactory.addConfigurationFileFromClasspath("test.conf");
    }

    @Test
    public void addConfigurationFromClasspath() throws IOException, ConfigurationFileNotFoundException {
        ConfigurationFactory configurationFactory = new ConfigurationFactory();
        configurationFactory.addConfigurationFileFromFilesystem(new File("src/test/resources/test.conf"));
    }

}
