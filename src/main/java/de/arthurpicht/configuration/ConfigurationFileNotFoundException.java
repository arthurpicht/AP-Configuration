package de.arthurpicht.configuration;

import java.io.File;

public class ConfigurationFileNotFoundException extends ConfigurationException {

    public ConfigurationFileNotFoundException() {
    }

    public ConfigurationFileNotFoundException(File file) {
        super("Configuration file not found: " + file.getAbsolutePath());
    }

    public ConfigurationFileNotFoundException(File file, Throwable cause) {
        super("Configuration file not found: " + file.getAbsolutePath(), cause);
    }

    public ConfigurationFileNotFoundException(String filename) {
        super("Configuration file not found: " + filename);
    }

    public ConfigurationFileNotFoundException(String filename, Throwable cause) {
        super("Configuration file not found: " + filename, cause);
    }

    public ConfigurationFileNotFoundException(Throwable cause) {
        super(cause);
    }
}
