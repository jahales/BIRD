package models.simulator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.ISerializer;
import models.report.CSVReader;
import models.report.DataTable;
import models.simulator.data.BirdSimulationSerializer;

/**
 *
 * @author Jacob Hales
 *
 */
public class BirdSimulatorEngine implements ISimulationEngine {

  private Simulation simulation;
  private final String exeFile = "C:\\Temp\\bird.exe";

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
  public DataTable run(Simulation simulation) {
    try {
      String settingsFile = simulation.getOutputFile() + ".temp";

      // Unpack the executable needed to run the simulation
      try (InputStream is = getClass().getClassLoader().getResourceAsStream("resources/bird.exe")) {
        byte[] buffer = new byte[4096];
        int read = 0;

        try (OutputStream os = new FileOutputStream(exeFile)) {
          while ((read = is.read(buffer)) > 0) {
            os.write(buffer, 0, read);
          }
        }
      }

      // Unpack the simulation file need to run the simulation
      ISerializer<Simulation> serializer = new BirdSimulationSerializer();

      try (FileOutputStream stream = new FileOutputStream(settingsFile)) {
        serializer.serialize(simulation, stream);
      }

      // Create the arguments to send to the simulator
      String cmd = null;

      if (simulation.isMonteCarlo()) {
        cmd = exeFile + " mc " + settingsFile + " " + simulation.getMonteNumber();
      } else {
        cmd = exeFile + " run " + settingsFile;
      }

      // Run the simulator and return the result
      Process p = Runtime.getRuntime().exec(cmd);
      p.waitFor();
      DataTable csv = new CSVReader().deserialize(new FileInputStream(simulation.getOutputFile()));

      return csv;
    } catch (Exception ex) {
      Logger.getLogger(BirdSimulatorEngine.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }
}
