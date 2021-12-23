package de.arthurpicht.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SmokeTest {

    @Test
    public void smokeTest1() throws IOException, ConfigurationFileNotFoundException {
        ConfigurationFactory configurationFactory = new ConfigurationFactory();
        configurationFactory.addConfigurationFileFromClasspath("test.conf");

        Configuration configuration = configurationFactory.getConfiguration();

        String string = configuration.getString("ein_text");
        assertEquals("hallo 2 3 4", string);

        int intValue = configuration.getInt("port");
        assertEquals(4711, intValue);

        boolean boolValue = configuration.getBoolean("wahrheit");
        assertTrue(boolValue);

        double doubleValue = configuration.getDouble("doublezahl");
        assertEquals(4.56, doubleValue, 0.01);

        List<String> stringList = configuration.getStringList("liste");
        List<String> stringListExpected = Arrays.asList("Aachen", "Düren", "Köln");
        assertEquals(stringListExpected, stringList);

        List<String> stringListByDefault = configuration.getStringList("not_existing", "a", "b");
        List<String> stringListByDefaultExpected = Arrays.asList("a", "b");
        assertEquals(stringListByDefaultExpected, stringListByDefault);

        String expandedValue = configuration.getString("interpol");
        assertEquals("vor 3 soso 4.56 ende", expandedValue);

        Assertions.assertThrows(NoSuchKeyException.class, () -> configuration.getString("not_existing"));

        String defaultValue = configuration.getString("not_existing", "defaultValue");
        assertEquals("defaultValue", defaultValue);
    }

}
