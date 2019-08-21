package de.arthurpicht.configuration;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class ConfigurationOverloadTest {

    @Test
    public void test1() {

        ConfigurationFactory configurationFactory = new ConfigurationFactory();
        try {
            configurationFactory.addConfigurationFileFromClasspath("test.conf");
            configurationFactory.addConfigurationFileFromClasspath("test2.conf");
            Configuration myConfiguration = configurationFactory.getConfiguration();

            assertEquals(true, myConfiguration.getBoolean("wahrheit"));

            assertEquals(3, myConfiguration.getInt("intzahl"));

            assertEquals(4.56, myConfiguration.getDouble("doublezahl"),0.01);

            assertEquals("überschrieben", myConfiguration.getString("ein_text"));

            assertEquals("test234", myConfiguration.getString("neuer_para"));

        } catch (ConfigurationFileNotFoundException | IOException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

}
