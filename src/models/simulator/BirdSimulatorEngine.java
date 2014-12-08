package models.simulator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private final String exePath = "\"C:\\Temp\\byuirocket.exe\"";
    private String settingsPath = "\"C:\\Temp\\Simple Rocket (1).xml\"";
    
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
    ISerializer<Simulation> serializer = new BirdSimulationSerializer();
      try {
        serializer.serialize(simulation, new FileOutputStream("C:\\Temp\\Settings.xml"));
      } catch (Exception ex) {
        Logger.getLogger(BirdSimulatorEngine.class.getName()).log(Level.SEVERE, null, ex);
      }
    
      try {
        String cmd = exePath + " run " + settingsPath;
        Process p = Runtime.getRuntime().exec(cmd);
        p.waitFor();
        
        CSVReader reader = new CSVReader();
        DataTable csv = reader.deserialize(new FileInputStream("C:\\Temp\\result.csv"));
        return reduceDataTable(csv);
      } catch (Exception ex) {
        Logger.getLogger(BirdSimulatorEngine.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      return null;
  }
  
  private DataTable reduceDataTable(DataTable dataTable) throws DataTable.RowFormatError
  {
//    DataTable result = new DataTable();
//    
//    for (String columnName : dataTable.getColumnNames())
//    {
//      result.addColumn(columnName);
//    }
//    
//    int inc = dataTable.getRows() / 50;
//    
//    for (int i = 0; i < dataTable.getRows(); i += inc)
//    {
//      result.addRow(dataTable.getRow(i));
//    }
//    
//    return result;
    return dataTable;
  }
}
