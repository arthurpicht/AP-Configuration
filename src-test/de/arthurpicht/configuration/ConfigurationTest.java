package de.arthurpicht.configuration;

import de.arthurpicht.configuration.Configuration;
import de.arthurpicht.configuration.ConfigurationFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigurationTest {

    public static void main(String[] args) {
        ConfigurationFactory configurationFactory = new ConfigurationFactory();
        try {
            configurationFactory.addFilesystemConfiguration(new File("test-resrc/test.conf"));
            configurationFactory.addFilesystemConfiguration(new File("test-resrc/test2.conf"));
            configurationFactory.addClasspathConfiguration("test3.conf");
        } catch (ConfigurationFileNotFoundException | IOException e) {

            e.printStackTrace();
        }

        Configuration myConfiguration = configurationFactory.getConfiguration();
        System.out.println("wahrheit: " + myConfiguration.getBoolean("wahrheit"));
        System.out.println("integer : " + myConfiguration.getInt("intzahl"));
        System.out.println("double  : " + myConfiguration.getDouble("doublezahl"));

        List<String> valueList = myConfiguration.getStringList("liste");
        for (String element : valueList) {
            System.out.println("Element : '" + element + "'");
        }

        System.out.println("interpol : '" + myConfiguration.getString("interpol") + "'");
    }

}
