/*
 * AppSettings controls access to the application properties file, 
 * and follows the singleton design pattern. 
 * 
 * and open the template in the editor.
 */
package models;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author Joseph
 */
public class AppSettings {

    private static AppSettings appSettings;

    //TODO: add properties for most recently used files.
    private static String defaultRocketPath;

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
    public static void loadProperties() {
        Properties prop = new Properties();
    	InputStream input = null;
 
    	try {
 
    		String filename = "AppSettings.properties";
    		input = AppSettings.class.getClassLoader().getResourceAsStream(filename);
    		if(input==null){
    	            System.out.println("Sorry, unable to find " + filename);
    		    return;
    		}
 
    		//load a properties file from class path, inside static method
    		prop.load(input);
 
                //get the property values and store it in the object
                defaultRocketPath = prop.getProperty("defaultRocketPath");
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        } finally{
        	if(input!=null){
        		try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        	}
        }
    }
    
    /**
     * 
     */
    public static void saveProperties() {
        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream("config.properties");

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
}
