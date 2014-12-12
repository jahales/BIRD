/*
 * AppSettings controls access to the application properties file,
 * and follows the singleton design pattern.
 *
 */
package models;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.simulator.LaunchRail;

/**
 *
 * @author Joseph
 */
public class AppSettings {

  Logger logger = Logger.getLogger(AppSettings.class.getName());
  private static AppSettings appSettings;
  
  // TODO: add properties for most recently used files.
  private LaunchRail launchRail = new LaunchRail();
  private File presentWorkingDirectory;
  
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
      // Load property values
      presentWorkingDirectory = new File(prop.getProperty("defaultRocketPath"));

      // Launch Rail
      Measurement length = new Measurement();
      length.setValue(Double.parseDouble(prop.getProperty("length")));
      length.setError(Double.parseDouble(prop.getProperty("lengthError")));
      length.setUnit(Unit.valueOf(prop.getProperty("lengthUnits")));
      launchRail.setLength(length);

      Measurement polarAngle = new Measurement();
      polarAngle.setValue(Double.parseDouble(prop.getProperty("polarAngle")));
      polarAngle.setError(Double.parseDouble(prop.getProperty("polarAngleError")));
      polarAngle.setUnit(Unit.valueOf(prop.getProperty("polarAngleUnits")));
      launchRail.setPolarAngle(polarAngle);

      Measurement azimuthAngle = new Measurement();
      azimuthAngle.setValue(Double.parseDouble(prop.getProperty("azimuthAngle")));
      azimuthAngle.setError(Double.parseDouble(prop.getProperty("azimuthAngleError")));
      azimuthAngle.setUnit(Unit.valueOf(prop.getProperty("azimuthAngleUnits")));
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
    OutputStream output;
    try {
      // set the property values
      prop.setProperty("defaultRocketPath", presentWorkingDirectory.getPath());

      // Launch Rail
      prop.setProperty("length", Double.toString(launchRail.getLength().getValue()));
      prop.setProperty("lengthError", Double.toString(launchRail.getLength().getError()));
      prop.setProperty("lengthUnits", launchRail.getLength().getUnit().toString());

      prop.setProperty("polarAngle", Double.toString(launchRail.getPolarAngle().getValue()));
      prop.setProperty("polarAngleError", Double.toString(launchRail.getPolarAngle().getError()));
      prop.setProperty("polarAngleUnits", launchRail.getPolarAngle().getUnit().toString());

      prop.setProperty("azimuthAngle", Double.toString(launchRail.getAzimuthAngle().getValue()));
      prop.setProperty("azimuthAngleError",
          Double.toString(launchRail.getAzimuthAngle().getError()));
      prop.setProperty("azimuthAngleUnits", launchRail.getAzimuthAngle().getUnit().toString());

      // save properties to project root folder
      output = new FileOutputStream("src/models/AppSettings.properties");
      prop.store(output, "");

      if (output != null) {
        output.close();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {

    }
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

  public File getPresentWorkingDirectory() {
    return presentWorkingDirectory;
  }

  public void setPresentWorkingDirectory(File presentWorkingDirectory) {
    this.presentWorkingDirectory = presentWorkingDirectory;
    this.saveProperties();
  }
  
  
}
