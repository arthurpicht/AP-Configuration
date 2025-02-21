package de.arthurpicht.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NonValidConfigurationsTest {

    @Test
    public void oneLineOfRubbish() {
        ConfigurationFactory configurationFactory = new ConfigurationFactory();
        ConfigurationRuntimeException e = Assertions.assertThrows(
                ConfigurationRuntimeException.class,
                () -> configurationFactory.addConfigurationFileFromClasspath("not-valid.conf")
        );
        Assertions.assertEquals("No assignment operator found in configuration line.", e.getMessage());
    }

}
