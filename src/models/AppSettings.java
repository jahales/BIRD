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
import models.simulator.LaunchRail;
import models.Unit;
/**
 *
 * @author Joseph
 */
public class AppSettings {

  Logger logger = Logger.getLogger(AppSettings.class.getName());
  private static AppSettings appSettings;
  //TODO: add properties for most recently used files.
  private String defaultRocketPath;
  private LaunchRail launchRail;

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

      //Launch Rail
      Measurement length = new Measurement();
      length.setValue(Double.parseDouble(prop.getProperty("length")));
      length.setError(Double.parseDouble(prop.getProperty("lengthError")));
      length.setUnit(Unit.valueOf(prop.getProperty("lengthUnit")));
      launchRail.setLength(length);

      Measurement polarAngle = new Measurement();
      polarAngle.setValue(Double.parseDouble(prop.getProperty("polarAngle")));
      polarAngle.setError(Double.parseDouble(prop.getProperty("polarAngleError")));
      polarAngle.setUnit(Unit.valueOf(prop.getProperty("polarAngleUnit")));
      launchRail.setPolarAngle(polarAngle);

      Measurement azimuthAngle = new Measurement();
      azimuthAngle.setValue(Double.parseDouble(prop.getProperty("azimuthAngle")));
      azimuthAngle.setError(Double.parseDouble(prop.getProperty("azimuthAngleError")));
      azimuthAngle.setUnit(Unit.valueOf(prop.getProperty("azimuthAngleUnit")));
      launchRail.setAzimuthAngle(azimuthAngle);

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

      //Launch Rail
      prop.setProperty("length", Double.toString(launchRail.getLength().getValue()));
      prop.setProperty("lengthError", Double.toString(launchRail.getLength().getError()));
      prop.setProperty("lengthUnit", launchRail.getLength().getUnit().toString());

      prop.setProperty("polarAngle", Double.toString(launchRail.getPolarAngle().getValue()));
      prop.setProperty("polarAngleError", Double.toString(launchRail.getPolarAngle().getError()));
      prop.setProperty("polarAngleUnit", launchRail.getPolarAngle().getUnit().toString());

      prop.setProperty("azimuthAngle", Double.toString(launchRail.getAzimuthAngle().getValue()));
      prop.setProperty("azimuthAngleError", Double.toString(launchRail.getAzimuthAngle().getError()));
      prop.setProperty("azimuthAngleUnit", launchRail.getAzimuthAngle().getUnit().toString());

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
    this.defaultRocketPath = defaultRocketPath;
    AppSettings.getInstance().saveProperties();
  }

  /**
   *
   * @return
   */
  public LaunchRail getLaunchRail() {
    return launchRail;
  }

  /**
   *
   * @param launchRail
   */
  public void setLaunchRail(LaunchRail launchRail) {
    this.launchRail = launchRail;
    AppSettings.getInstance().saveProperties();
  }
}
