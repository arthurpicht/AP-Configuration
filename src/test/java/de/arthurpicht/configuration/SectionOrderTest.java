package de.arthurpicht.configuration;

import de.arthurpicht.utils.core.collection.Lists;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SectionOrderTest {

    @Test
    public void simpleSectionOrder() throws IOException, ConfigurationFileNotFoundException {
        ConfigurationFactory configurationFactory = new ConfigurationFactory();
        configurationFactory.addConfigurationFileFromClasspath("testSection.conf");
        List<String> sectionNamesList = new ArrayList<>(configurationFactory.getSectionNames());

        assertEquals(3, sectionNamesList.size());
        assertEquals("section1", sectionNamesList.get(0));
        assertEquals("section2", sectionNamesList.get(1));
        assertEquals("section3", sectionNamesList.get(2));
    }

    @Test
    public void orderTest() throws IOException, ConfigurationFileNotFoundException {
        ConfigurationFactory configurationFactory = new ConfigurationFactory();
        configurationFactory.addConfigurationFileFromClasspath("meta.conf");
        List<String> sectionNamesList = new ArrayList<>(configurationFactory.getSectionNames());

        assertEquals(9, sectionNamesList.size());
        assertEquals(Lists.newArrayList("general", "simple", "testRepo1", "testRepo2", "testRepo3", "testRepo4", "testRepo5", "testRepo6", "testRepo7"), sectionNamesList);
    }

}
