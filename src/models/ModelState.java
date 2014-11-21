package models;

import java.io.File;
import models.rocket.Rocket;
import models.simulator.Simulation;

/**
 * Singleton pattern that stores a rocket and simulation object. A property
 * object will be defined soon.
 * 
 * @author Brian Woodruff
 *
 */
public class ModelState {
  private boolean unsaved = false;
  private boolean neverBeenSaved = true;
  private File presentWorkingFile;
  private File presentWorkingDirectory;
  private Rocket rocket = new Rocket();
  private Simulation simulation = new Simulation();

  // private Property property;
 

  /**
   * @return rocket
   */
  public Rocket getRocket() {
    return rocket;
  }

  /**
   * @return simulation
   */
  public Simulation getSimulation() {
    return simulation;
  }

  /**
   * @return neverBeenSaved
   */
  public boolean hasNeverBeenSaved() {
    return neverBeenSaved;
  }

  /**
   * @param unsaved
   */
  public void setNeverBeenSaved(boolean unsaved) {
    this.neverBeenSaved = unsaved;
  }

  /**
   * @return presentWorkingFile
   */
  public File getPresentWorkingFile() {
    return presentWorkingFile;
  }

  /**
   * @param presentWorkingFile
   */
  public void setPresentWorkingFile(File presentWorkingFile) {
    this.presentWorkingFile = presentWorkingFile;
  }

  /**
   * @return presentWorkingDirectory
   */
  public File getPresentWorkingDirectory() {
    return presentWorkingDirectory;
  }

  /**
   * @param presentWorkingDirectory
   */
  public void setPresentWorkingDirectory(File presentWorkingDirectory) {
    this.presentWorkingDirectory = presentWorkingDirectory;
  }

  /**
   * @return unsaved
   */
  public boolean isUnsaved() {
    return unsaved;
  }

  /**
   * @param unsaved
   */
  public void setUnsaved(boolean unsaved) {
    this.unsaved = unsaved;
  }
}
