package de.arthurpicht.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SmokeTest {

    private static ConfigurationFactory configurationFactory;

    @BeforeAll
    public static void prepare() throws IOException, ConfigurationFileNotFoundException {
        configurationFactory = new ConfigurationFactory();
        configurationFactory.addConfigurationFileFromClasspath("test.conf");
    }

    @Test
    public void sections() {
        Set<String> sectionNames = configurationFactory.getSectionNames();
        assertTrue(sectionNames.contains(""));
        assertTrue(sectionNames.contains("section1"));
        assertTrue(sectionNames.contains("section2"));
        assertEquals(3, sectionNames.size());
    }

    @Test
    public void unnamedSection() {
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

    @Test
    public void section1() {
        Configuration configuration = configurationFactory.getConfiguration("section1");
        assertEquals(2, configuration.getKeys().size());

        String text = configuration.getString("text");
        assertEquals("Dies ist ein Text.", text);

        int wert = configuration.getInt("wert");
        assertEquals(42, wert);
    }

    @Test
    public void section2() {
        Configuration configuration = configurationFactory.getConfiguration("section2");
        assertEquals(2, configuration.getKeys().size());

        boolean wahrheit = configuration.getBoolean("wahrheit");
        assertFalse(wahrheit);

        double zahl = configuration.getDouble("zahl");
        assertEquals(42.42, zahl);
     }

}
