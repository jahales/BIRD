package models.simulator;

import models.report.DataTable;

/**
 *
 * @author Brian Woodruff
 *
 */
public interface ISimulationEngine {

  /**
   * Run the simulation
   *
   * @param simulation
   * @return
   */
  public DataTable run(Simulation simulation);
}
