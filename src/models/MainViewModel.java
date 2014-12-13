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

  private boolean neverBeenSaved = true;
  private File presentWorkingFile;
  private Rocket rocket = new Rocket();
  private Simulation simulation = new Simulation();

  /**
   * @return rocket
   */
  public Rocket getRocket() {
    return rocket;
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
    return simulation;
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
    return neverBeenSaved;
  }

  /**
   * @param unsaved
   */
  public void setNeverBeenSaved(boolean unsaved) {
    neverBeenSaved = unsaved;
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
}
