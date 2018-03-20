package de.arthurpicht.configuration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SmokeTest {

    @Test
    public void smokeTest1() {
        ConfigurationFactory configurationFactory = new ConfigurationFactory();
        try {
            configurationFactory.addConfigurationFileFromClasspath("test.conf");
        } catch (ConfigurationFileNotFoundException | IOException e) {
            fail(e.getClass().getSimpleName() + ": " + e.getMessage());
        }

        Configuration configuration = configurationFactory.getConfiguration();

        String string = configuration.getString("ein_text");
        assertEquals("hallo 2 3 4", string);

        int intValue = configuration.getInt("port");
        assertEquals(4711, intValue);

        boolean boolValue = configuration.getBoolean("wahrheit");
        assertEquals(true, boolValue);

        double doubleValue = configuration.getDouble("doublezahl");
        assertEquals(4.56, doubleValue, 0.01);

        List<String> stringList = configuration.getStringList("liste");
        assertThat(stringList, is(Arrays.asList("Aachen", "Düren", "Köln")));

        String expandedValue = configuration.getString("interpol");
        assertEquals("vor 3 soso 4.56 ende", expandedValue);

        try {
            String noValue = configuration.getString("not_existing");
            fail("NoSuchKeyException expected");
        } catch (NoSuchKeyException e) {

        }

        String defaultValue = configuration.getString("not_existing", "defaultValue");
        assertEquals("defaultValue", defaultValue);
    }
}
