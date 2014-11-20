package models;

import java.io.File;
import models.rocket.Rocket;
import models.simulator.Simulation;

/**
 * Singleton pattern that stores a rocket and simulation object. A property object will be defined soon.
 * @author Brian Woodruff
 *
 */
public class ModelState {
  private static ModelState appState;
  
  private boolean unsaved = false;
  private boolean neverBeenSaved = true;
  private File presentWorkingFile;
  private File presentWorkingDirectory;
  private Rocket rocket = new Rocket();
  private Simulation simulation = new Simulation();
  //private Property property;
  
  /**
   * Private constructor
   */
  public ModelState() {
    rocket = new Rocket();
    simulation = new Simulation();
  }

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

  public boolean hasNeverBeenSaved() {
      return neverBeenSaved;
  }

  public void setNeverBeenSaved(boolean unsaved) {
      this.neverBeenSaved = unsaved;
  }
   public File getPresentWorkingFile() {
      return presentWorkingFile;
  }

  public void setPresentWorkingFile(File presentWorkingFile) {
      this.presentWorkingFile = presentWorkingFile;
  }

  public File getPresentWorkingDirectory() {
      return presentWorkingDirectory;
  }

  public void setPresentWorkingDirectory(File presentWorkingDirectory) {
      this.presentWorkingDirectory = presentWorkingDirectory;
  }

  public boolean isUnsaved() {
      return unsaved;
  }

  public void setUnsaved(boolean unsaved) {
      this.unsaved = unsaved;
  }
  
}
