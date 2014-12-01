package models.simulator;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Brian Woodruff, Jacob Hales
 *
 */
public class BirdSimulatorEngine implements ISimulationEngine {
    private Simulation simulation;
    
    /**
     * @return simulation
     */
    public Simulation getSimulation() {
        return simulation;
    }
    
    /**
     * @param simulation
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

  @Override
  public String run(Simulation simulation) {
      try {
        String[] cmd = { "bird.exe", "simulation.xml" };
        Process p = Runtime.getRuntime().exec(cmd);
        p.waitFor();
      } catch (IOException | InterruptedException ex) {
        Logger.getLogger(BirdSimulatorEngine.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      return null;
  }
}
