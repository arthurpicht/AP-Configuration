package de.arthurpicht.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

import de.arthurpicht.configuration.helper.RessourceLocator;
import de.arthurpicht.configuration.helper.RessourceLocatorException;

public class ConfigurationFactory {

    private ConfigurationSectionProperties configurationSectionProperties;

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
    public void addFilesystemConfiguration(File file) throws ConfigurationFileNotFoundException, IOException {

        if (file == null) throw new IllegalArgumentException("Specified file is null.");
        if (!file.exists() || !file.isFile() || !file.canRead()) throw new ConfigurationFileNotFoundException(file);

        ConfigurationParser configurationParser = new ConfigurationParser(new FileInputStream(file));
        this.configurationSectionProperties.override(configurationParser.getConfigurationSectionProperties());
    }

    /**
     * Binds file specified by filename from classpath and adds its configuration content
     * to this configuration. Preexisting configuration properties are overriden.
     *
     * @param filename Configuration file to be processed.
     * @throws ConfigurationFileNotFoundException If configurationfile could not be found or not read.
     */
    public void addClasspathConfiguration(String filename) throws ConfigurationFileNotFoundException, IOException {

        ConfigurationParser configurationParser;

        try {
            RessourceLocator ressourceLocator = RessourceLocator.bindRessourceFromClasspath(filename);
            configurationParser = new ConfigurationParser(ressourceLocator.getInputStream());
        } catch (RessourceLocatorException e1) {
            throw new ConfigurationFileNotFoundException(filename + " [classpath]", e1);
        }

        this.configurationSectionProperties.override(configurationParser.getConfigurationSectionProperties());
    }

//    /**
//     * Fügt der Konfiguration den Inhalt einer Konfigurationsdatei mit
//     * dem übergebenen Namen hinzu. Diese Datei wird im Dateisystem
//     * anhand des übergebenen Dateinamens einschließlich Dateipfades
//     * lokalisiert. Vorbestehende Konfigurations-
//     * Schlüssel werden, sofern in dieser Datei erneut definiert,
//     * überschrieben. Über einen Fehler beim Lesen der Datei
//     * einschließlich Nichtexistenz wird hinweg gegangen.
//     *
//     * @param fileName Dateiname der Konfigurationsdatei
//     * @return Konfigurationsdatei geladen oder nicht
//     */
//    public boolean addConfigurationFromFilesystem(String fileName) {
//
//        ConfigurationParser configurationParser;
//
//        try {
//            RessourceLocator ressourceLocator = RessourceLocator.bindRessourceFromFileSystem(fileName);
//            configurationParser = new ConfigurationParser(ressourceLocator.getInputStream());
//        } catch (RessourceLocatorException | IOException e1) {
//            return false;
//        }
//
//        this.configurationSectionProperties.override(configurationParser.getConfigurationSectionProperties());
//
//        return true;
//    }

//    /**
//     * Fügt der Konfiguration den Inhalt einer Properties-Datei mit
//     * dem übergebenen Namen hinzu. Diese Datei wird im Klassenpfad
//     * anhand des übergebenen Dateinamens gesucht. Vorbestehende
//     * Konfigurationsschlüssel werden, sofern in dieser Datei erneut
//     * definiert, überschrieben. Über einen Fehler beim Lesen der Datei
//     * einschließlich Nichtexistenz wird hinweg gegangen.
//     *
//     * @param fileName Dateiname der Konfigurationsdatei
//     */
//    public boolean addConfigurationFromClasspath(String fileName) {
//
//        ConfigurationParser configurationParser;
//
//        try {
//            RessourceLocator ressourceLocator = RessourceLocator.bindRessourceFromClasspath(fileName);
//            configurationParser = new ConfigurationParser(ressourceLocator.getInputStream());
//        } catch (RessourceLocatorException | IOException e1) {
//            return false;
//        }
//
//        this.configurationSectionProperties.override(configurationParser.getConfigurationSectionProperties());
//
//        return true;
//    }

//    /**
//     * Verarbeitet den Inhalt einer Konfigurationsdatei, die im Klassenpfad eines Objektes
//     * gesucht wird. Hierzu zählt insbes. das Package des übergebenen Objektes.
//     * Geeignet insbes. für automatische Testklassen mit Testkonfigurationen im selben
//     * Package.
//     *
//     * @param object
//     * @param fileName
//     * @return
//     */
//    public boolean addConfigurationFromObjectClasspath(Object object, String fileName) {
//
//        ConfigurationParser configurationParser;
//
//        try {
//            RessourceLocator ressourceLocator = RessourceLocator.bindRessourceFromObjectClasspath(object, fileName);
//            configurationParser = new ConfigurationParser(ressourceLocator.getInputStream());
//        } catch (RessourceLocatorException | IOException e1) {
//            return false;
//        }
//
//        this.configurationSectionProperties.override(configurationParser.getConfigurationSectionProperties());
//
//        return true;
//    }


//    /**
//     * Versucht die übergebene Konfigurationsdatei zunächst im Klassenpfad zu
//     * finden und zu lesen. Falls dies fehlschlägt, wird sie versucht im Dateisystem
//     * zu finden. Auftretende Fehler und Nichtexistenz der Konfigurationsdatei werden
//     * ignoriert.
//     *
//     * @param fileName
//     * @return
//     */
//    public boolean addConfiguration(String fileName) {
//        boolean found = this.addConfigurationFromClasspath(fileName);
//        if (!found) {
//            found = this.addConfigurationFromFilesystem(fileName);
//        }
//        return found;
//    }

//    /**
//     * Fügt der Konfiguration den Inhalt einer Properties-Datei hinzu, deren
//     * Pfad bzw. Name als Wert der übergebenen System-Property übergeben wird
//     * und aus dem Dateisystem zu lesen versucht wird. Etwaige Referenzen auf
//     * andere Systemvariablen innerhalb des Wertes der Systemvariablen werden
//     * aufgelöst.
//     *
//     * @param systemProperty
//     */
//    public boolean addConfigurationFromFilesystemReferencedBySystemProperty(String systemProperty) {
//        String configFileName = System.getenv(systemProperty);
//        boolean found = false;
//        if ((configFileName != null) && (!configFileName.equals(""))) {
//            configFileName = ValueExpander.expandEnvironmentProperties(configFileName);
//            found = this.addConfigurationFromFilesystem(configFileName);
//        }
//        return found;
//    }

//    /**
//     * Fügt der Konfiguration den Inhalt einer Properties-Datei hinzu, deren
//     * Pfad bzw. Name als Wert der übergebenen System-Property übergeben wird
//     * und aus dem Klassenpfad zu lesen versucht wird.
//
//     * @param systemProperty
//     */
//    public boolean addConfigurationFromClasspathReferencedBySystemProperty(String systemProperty) {
//        String configFileName = System.getenv(systemProperty);
//        boolean found = false;
//        if ((configFileName != null) && (!configFileName.equals(""))) {
//            configFileName = ValueExpander.expandEnvironmentProperties(configFileName);
//            found = this.addConfigurationFromClasspath(configFileName);
//        }
//        return found;
//    }

//    /**
//     * Fügt der Konfiguration den Inhalt einer Properties-Datei hinzu, deren
//     * Pfad bzw. Name als Wert der übergebenen System-Property übergeben wird
//     * und aus dem Klassenpfad zu lesen versucht wird.
//
//     * @param systemProperty
//     */
//    public boolean addConfigurationFromObjectClasspathReferencedBySystemProperty(String systemProperty, Object object) {
//        String configFileName = System.getenv(systemProperty);
//        boolean found = false;
//        if ((configFileName != null) && (!configFileName.equals(""))) {
//            configFileName = ValueExpander.expandEnvironmentProperties(configFileName);
//
//            found = this.addConfigurationFromObjectClasspath(object, configFileName);
//        }
//        return found;
//    }


//    /**
//     * Fügt der Konfiguration die Definitionen einer Konfigurationsdatei hinzu, deren
//     * Pfad bzw. Name als Wert der übergebenen System-Property gegeben ist. Die Datei
//     * wird zunächst versucht aus dem Klassenpfad zu lesen. Falls dies nicht möglich ist,
//     * wird versucht sie aus dem Dateisystem zu lesen. Etwaig auftretende Fehler und
//     * Nichtexistenz der Datei werden ignoriert. Der Wert der System-Property kann Referenzen
//     * auf weitere System-Properties enthalten.
//     *
//     * @param systemProperty
//     * @return
//     */
//    public boolean addConfigurationReferencedBySystemProperty(String systemProperty) {
//
//        boolean found = this.addConfigurationFromClasspathReferencedBySystemProperty(systemProperty);
//        if (!found) {
//            found = this.addConfigurationFromFilesystemReferencedBySystemProperty(systemProperty);
//        }
//        return found;
//    }

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
     * Prüft ob die angegebene Sektion existiert.
     *
     * @param sectionName
     * @return
     */
    public boolean hasSection(String sectionName) {
        return this.configurationSectionProperties.hasSection(sectionName);
    }

    /**
     * Liefert die Konfiguration für die bezeichnete Sektion.
     * Liefert null wenn diese nicht besteht.
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
     * Prüft ob eine Sektionsvorlage (Template) existiert.
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
