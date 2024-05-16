package de.arthurpicht.configuration;

import de.arthurpicht.configuration.helper.RessourceLocator;
import de.arthurpicht.configuration.helper.RessourceLocatorException;

import java.io.*;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
public class ConfigurationFactory {

    private final ConfigurationSectionProperties configurationSectionProperties;

    public ConfigurationFactory() {
        this.configurationSectionProperties = new ConfigurationSectionProperties();
    }

    /**
     * Adds configuration content of file to this configuration. Preexisting configuration
     * properties are overridden.
     *
     * @param file Configuration file to be processed.
     * @throws ConfigurationFileNotFoundException If configuration file could not be found or not read.
     */
    public void addConfigurationFileFromFilesystem(File file) throws ConfigurationFileNotFoundException, IOException {
        if (file == null) throw new IllegalArgumentException("Specified file is null.");
        if (!file.exists() || !file.isFile() || !file.canRead()) throw new ConfigurationFileNotFoundException(file);
        ConfigurationParser configurationParser = new ConfigurationParser(new FileInputStream(file));
        this.configurationSectionProperties.override(configurationParser.getConfigurationSectionProperties());
    }

    /**
     * Binds file specified by filename from classpath and adds its configuration content
     * to this configuration. Preexisting configuration properties are overridden.
     *
     * @param filename Configuration file to be processed.
     * @throws ConfigurationFileNotFoundException If configuration file could not be found or not read.
     */
    public void addConfigurationFileFromClasspath(String filename) throws ConfigurationFileNotFoundException, IOException {
        ConfigurationParser configurationParser;
        try {
            RessourceLocator ressourceLocator = RessourceLocator.bindRessourceFromClasspath(filename);
            configurationParser = new ConfigurationParser(ressourceLocator.getInputStream());
        } catch (RessourceLocatorException e1) {
            throw new ConfigurationFileNotFoundException(filename + " [classpath]", e1);
        }
        this.configurationSectionProperties.override(configurationParser.getConfigurationSectionProperties());
    }

    /**
     * Adds configuration given as string to this configuration. Preexisting configuration properties
     * are overridden.
     *
     * @param string configuration string
     * @throws IOException
     */
    public void addConfigurationFromString(String string) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(string.getBytes());
        ConfigurationParser configurationParser = new ConfigurationParser(inputStream);
        this.configurationSectionProperties.override(configurationParser.getConfigurationSectionProperties());
    }

    /**
     * Liefert die Bezeichnungen aller Sektionen.
     *
     * @return
     */
    public Set<String> getSectionNames() {
        return this.configurationSectionProperties.getSectionNames();
    }

    /**
     * Liefert die Anzahl der Sektionen.
     *
     * @return
     */
    public int getNrOfSections() {
        return this.configurationSectionProperties.getNrOfSections();
    }

    /**
     * Prüft, ob die angegebene Sektion existiert.
     *
     * @param sectionName
     * @return
     */
    public boolean hasSection(String sectionName) {
        return this.configurationSectionProperties.hasSection(sectionName);
    }

    /**
     * Liefert die Konfiguration für die bezeichnete Sektion.
     * Liefert null, wenn diese nicht besteht.
     *
     * @param sectionName
     * @return Konfiguration für bezeichnete Sektion, null wenn diese nicht besteht.
     */
    public Configuration getConfiguration(String sectionName) {
        ConfigurationProperties configurationProperties = this.configurationSectionProperties.getConfigurationProperties(sectionName);
        if (configurationProperties == null) return null;
        return new ConfigurationManager(configurationProperties);
    }

    /**
     * Liefert die Konfiguration für die erste und zugleich unbenannte Sektion.
     * Liefert null, wenn die erste Sektion benannt ist.
     *
     * @return
     */
    public Configuration getConfiguration() {
        ConfigurationProperties configurationProperties = this.configurationSectionProperties.getConfigurationProperties("");
        if (configurationProperties == null) return null;
        return new ConfigurationManager(configurationProperties);
    }

    /**
     * Prüft, ob eine Sektionsvorlage (Template) existiert.
     *
     * @return
     */
    public boolean hasTemplateSection() {
        return this.configurationSectionProperties.hasTemplateSection();
    }

    /**
     * Liefert die Sektionsvorlage (Template) als Configuration zurück.
     * Liefert null, wenn keine Vorlage existiert.
     *
     * @return
     */
    public Configuration getTemplateSectionAsConfiguration() {
        ConfigurationProperties configurationProperties = this.configurationSectionProperties.getTemplateSection();
        if (configurationProperties == null) return null;
        return new ConfigurationManager(configurationProperties);
    }

    public void list() {
        // TODO debug
//		System.out.println("Properties:");
        this.configurationSectionProperties.list(System.out);
    }

}
