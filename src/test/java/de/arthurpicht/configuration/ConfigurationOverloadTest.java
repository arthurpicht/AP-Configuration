package de.arthurpicht.configuration;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigurationOverloadTest {

    @Test
    public void test1() throws IOException, ConfigurationFileNotFoundException {
        ConfigurationFactory configurationFactory = new ConfigurationFactory();

        configurationFactory.addConfigurationFileFromClasspath("test.conf");
            configurationFactory.addConfigurationFileFromClasspath("test2.conf");
            Configuration myConfiguration = configurationFactory.getConfiguration();

            assertTrue(myConfiguration.getBoolean("wahrheit"));
            assertEquals(3, myConfiguration.getInt("intzahl"));
            assertEquals(4.56, myConfiguration.getDouble("doublezahl"),0.01);
            assertEquals("überschrieben", myConfiguration.getString("ein_text"));
            assertEquals("test234", myConfiguration.getString("neuer_para"));
    }

}
