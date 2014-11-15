package controllers;

import models.rocket.Rocket;
import models.simulator.Simulation;

/**
 * Singleton pattern that stores a rocket and simulation object. A property object will be defined soon.
 * @author Brian Woodruff
 *
 */
public class AppState {
  private static AppState appState;
  
  private Rocket rocket;
  private Simulation simulation;
  //private Property property;
  
  /**
   * Private constructor
   */
  private AppState() {
    rocket = new Rocket();
    simulation = new Simulation();
  }
  
  /**
   * @return appState
   */
  public static AppState getInstance() {
    if (appState == null) {
      appState = new AppState();
    }
    return appState;
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
}
