package de.arthurpicht.configuration;

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

}
