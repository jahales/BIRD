package models;

/**
 * 
 * @author Brian Woodruff
 *
 */
public class BIRDSimulatorEngine {
    private Simulation simulation;
    private Process process;
    
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
    
    /**
     * @return process
     */
    public Process getProcess() {
        return process;
    }
    
    /**
     * @param process
     */
    public void setProcess(Process process) {
        this.process = process;
    }
}
