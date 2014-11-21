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
