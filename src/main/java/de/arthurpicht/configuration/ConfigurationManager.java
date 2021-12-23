package de.arthurpicht.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ConfigurationManager implements Configuration {

    private static final String KEY_IS_NULL_EXCEPTION = "Key is null or empty.";

    private final ConfigurationProperties configurationProperties;

    public ConfigurationManager(ConfigurationProperties configurationProperties) {
        this.configurationProperties = configurationProperties;
    }

    @Override
    public Set<String> getKeys() {
        return this.configurationProperties.getKeys();
    }

    @Override
    public boolean containsKey(String key) {
        return this.configurationProperties.containsKey(key);
    }

    @Override
    public boolean getBoolean(String key) {
        this.keyConsistencyCheck(key);
        String value = this.configurationProperties.getProperty(key);
        try {
            return Boolean.parseBoolean(value);
        } catch (NumberFormatException nfEx) {
            throw new ParseException("Cannot parse '" + value + "' to boolean.");
        }
    }

    @Override
    public double getDouble(String key) {
        this.keyConsistencyCheck(key);

        String value = this.configurationProperties.getProperty(key);
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException nfEx) {
            throw new ParseException("Cannot parse '" + value + "' to double.");
        }
    }

    @Override
    public int getInt(String key) {
        this.keyConsistencyCheck(key);

        String value = this.configurationProperties.getProperty(key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException nfEx) {
            throw new ParseException("Cannot parse '" + value + "' to integer.");
        }
    }

    @Override
    public String getString(String key) {
        this.keyConsistencyCheck(key);
        return this.configurationProperties.getProperty(key);
    }

    @Override
    public List<String> getStringList(String key) {
        this.keyConsistencyCheck(key);
        return this.configurationProperties.getPropertyList(key);
    }

    @Override
    public String getString(String configVarName, String defaultValue) {
        try {
            return this.getString(configVarName);
        } catch (NoSuchKeyException | ParseException ex) {
            return defaultValue;
        }
    }

    @Override
    public boolean getBoolean(String configVarName, boolean defaultValue) {
        try {
            return this.getBoolean(configVarName);
        } catch (NoSuchKeyException | ParseException ex) {
            return defaultValue;
        }
    }

    @Override
    public double getDouble(String configVarName, double defaultValue) {
        try {
            return this.getDouble(configVarName);
        } catch (NoSuchKeyException | ParseException ex) {
            return defaultValue;
        }
    }

    @Override
    public int getInt(String configVarName, int defaultValue) {
        try {
            return this.getInt(configVarName);
        } catch (NoSuchKeyException | ParseException ex) {
            return defaultValue;
        }
    }

    @Override
    public List<String> getStringList(String key, String defaultValue) {
        try {
            return this.getStringList(key);
        } catch (NoSuchKeyException | ParseException ex) {
            List<String> stringList = new ArrayList<>();
            stringList.add(defaultValue);
            return stringList;
        }
    }

    @Override
    public List<String> getStringList(String key, String... defaultValue) {
        try {
            return this.getStringList(key);
        } catch (NoSuchKeyException | ParseException ex) {
            return Arrays.asList(defaultValue);
        }
    }

    @Override
    public String getSectionName() {
        return this.configurationProperties.getSectionName();
    }

    private void keyConsistencyCheck(String key) {
        if (key == null || key.equals("")) {
            throw new ParseException(KEY_IS_NULL_EXCEPTION);
        }
        if (!this.configurationProperties.containsKey(key)) {
            throw new NoSuchKeyException("Configuration-key '" + key + "' not defined.");
        }
    }

}
