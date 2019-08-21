package de.arthurpicht.configuration;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.fail;

public class ConfigurationFactoryBindingTest {

    @Test
    public void addConfigurationFromFilesystem() {

        ConfigurationFactory configurationFactory = new ConfigurationFactory();
        try {
            configurationFactory.addConfigurationFileFromClasspath("test.conf");
        } catch (ConfigurationFileNotFoundException | IOException  e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

    @Test
    public void addConfigurationFromClasspath() {

        ConfigurationFactory configurationFactory = new ConfigurationFactory();
        try {
            configurationFactory.addConfigurationFileFromFilesystem(new File("src/test/resources/test.conf"));
        } catch (IOException | ConfigurationFileNotFoundException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

    }
}