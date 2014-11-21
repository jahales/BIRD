/*
 * AppSettings controls access to the application properties file, 
 * and follows the singleton design pattern. 
 * 
 */
package models;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joseph
 */
public class AppSettings {
    Logger logger = Logger.getLogger(AppSettings.class.getName());
    private static AppSettings appSettings;
    //TODO: add properties for most recently used files.
    private String defaultRocketPath;

    /**
     * Private constructor
     */
    private AppSettings() {
        loadProperties();
    }

    /**
     * @return appSettings
     */
    public static AppSettings getInstance() {
        if (appSettings == null) {
            appSettings = new AppSettings();
        }
        return appSettings;
    }

    /**
     *
     */
    public void loadProperties() {
        try {
            Properties prop = new Properties();
            prop.load(getClass().getClassLoader().getResourceAsStream("models/AppSettings.properties"));
            //Load property values
            defaultRocketPath = prop.getProperty("defaultRocketPath");
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Load Properties Failed", ex);
        }
    }

    /**
     * UNTESTED
     */
    public void saveProperties() {
        Properties prop = new Properties();
        OutputStream output = null;

        try {
            output = new FileOutputStream("models/AppSettings.properties");

            // set the property values
            prop.setProperty("defaultRocketPath", defaultRocketPath);

            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     *
     * @return
     */
    public String getDefaultRocketPath() {
        return defaultRocketPath;
    }

    /**
     *
     * @param defaultRocketPath
     */
    public void setDefaultRocketPath(String defaultRocketPath) {
        AppSettings.getInstance().defaultRocketPath = defaultRocketPath;
    }
}
