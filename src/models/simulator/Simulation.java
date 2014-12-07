package models.simulator;

/**
 * Simulation class
 * 
 * @author Brian Woodruff
 *
 */
public class Simulation {
    private String rocketFile;
    private String engineFile;
    private String atmosphereFile;
    private String launchRailFile;
    private boolean monteCarlo;
    private int monteNumber;
    private LaunchRail launchRail = new LaunchRail();
    
    /**
     * @return rocketFile
     */
    public String getRocketFile() {
        return rocketFile;
    }
    
    /**
     * @param rocketFile
     */
    public void setRocketFile(String rocketFile) {
        this.rocketFile = rocketFile;
    }
    
    /**
     * @return engineFile
     */
    public String getEngineFile() {
        return engineFile;
    }
    
    /**
     * @param engineFile
     */
    public void setEngineFile(String engineFile) {
        this.engineFile = engineFile;
    }
    
    /**
     * @return atmosphereFile
     */
    public String getAtmosphereFile() {
        return atmosphereFile;
    }
    
    /**
     * @param atmosphereFile
     */
    public void setAtmosphereFile(String atmosphereFile) {
        this.atmosphereFile = atmosphereFile;
    }
    
    /**
     * @return launchRailFile
     */
    public String getLaunchRailFile() {
        return launchRailFile;
    }
    
    /**
     * @param launchRailFile
     */
    public void setLaunchRailFile(String launchRailFile) {
        this.launchRailFile = launchRailFile;
    }

    /**
     * @return launchRail
     */
    public LaunchRail getLaunchRail() {
      return launchRail;
    }

    /**
     * @param launchRail
     */
    public void setLaunchRail(LaunchRail launchRail) {
      this.launchRail = launchRail;
    }

    public boolean isMonteCarlo() {
        return monteCarlo;
    }

    public void setIsMonteCarlo(boolean isMonteCarlo) {
        this.monteCarlo = isMonteCarlo;
    }

    public int getMonteNumber() {
        return monteNumber;
    }

    public void setMonteNumber(int monteNumber) {
        this.monteNumber = monteNumber;
    }
    
    
}
