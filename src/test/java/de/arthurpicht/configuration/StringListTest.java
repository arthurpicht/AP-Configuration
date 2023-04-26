package de.arthurpicht.configuration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringListTest {

    private static Configuration configuration;

    @BeforeAll
    public static void prepare() throws IOException, ConfigurationFileNotFoundException {
        ConfigurationFactory configurationFactory = new ConfigurationFactory();
        configurationFactory.addConfigurationFileFromClasspath("testList.conf");
        configuration = configurationFactory.getConfiguration();
    }

    @Test
    public void simpleStringList() {
        List<String> stringList = configuration.getStringList("my_list");
        List<String> stringListExpected = Arrays.asList("one", "two", "three");
        assertEquals(stringListExpected, stringList);
    }

    @Test
    public void propertiesAsListTest() {
        List<String> stringList = configuration.getStringList("my_properties");
        List<String> stringListExpected = Arrays.asList("key1=one", "key2=two", "key3=three");
        assertEquals(stringListExpected, stringList);
    }

    @Test
    public void properties2AsListTest() {
        List<String> stringList = configuration.getStringList("properties2");
        List<String> stringListExpected = Arrays.asList("para1=5", "para2 = foo", "para3 = foo bar");
        assertEquals(stringListExpected, stringList);
    }

}
