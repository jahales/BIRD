package models;

import java.io.File;

import models.rocket.Rocket;
import models.simulator.Simulation;

/**
 * Object representing the overall state of the model.
 *
 * @author Brian Woodruff, Jacob Hales
 *
 */
public class MainViewModel {

  private boolean unsaved = false;
  private boolean neverBeenSaved = true;
  private File presentWorkingFile;
  private File presentWorkingDirectory;
  private Rocket rocket = new Rocket();
  private Simulation simulation = new Simulation();

  /**
   * @return rocket
   */
  public Rocket getRocket() {
    return this.rocket;
  }

  /**
   *
   * @param rocket
   */
  public void setRocket(Rocket rocket) {
    this.rocket = rocket;
  }

  /**
   * @return simulation
   */
  public Simulation getSimulation() {
    return this.simulation;
  }

  /**
   *
   * @param simulation
   */
  public void setSimulation(Simulation simulation) {
    this.simulation = simulation;
  }

  /**
   * @return neverBeenSaved
   */
  public boolean hasNeverBeenSaved() {
    return this.neverBeenSaved;
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
    return this.presentWorkingFile;
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
    return this.presentWorkingDirectory;
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
    return this.unsaved;
  }

  /**
   * @param unsaved
   */
  public void setUnsaved(boolean unsaved) {
    this.unsaved = unsaved;
  }
}
