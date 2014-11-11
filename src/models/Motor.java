package models;

/**
 * A motor
 * 
 * @author Brian Woodruff
 *
 */
public class Motor extends CircularCylinder {
    private String manufacturer;
    private String delays;
    private Measurement fuelMass;
    private Measurement polarAngle;
    private Measurement azimuthAngle;
    private DataTable<Number> thrust;
    
    /**
     * @return manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }
    
    /**
     * @param manufacturer
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    
    /**
     * @return delays
     */
    public String getDelays() {
        return delays;
    }
    
    /**
     * @param delays
     */
    public void setDelays(String delays) {
        this.delays = delays;
    }
    
    /**
     * @return fuelMass
     */
    public Measurement getFuelMass() {
        return fuelMass;
    }
    
    /**
     * @param fuelMass
     */
    public void setFuelMass(Measurement fuelMass) {
        this.fuelMass = fuelMass;
    }
    
    /**
     * @return polarAngle
     */
    public Measurement getPolarAngle() {
        return polarAngle;
    }
    
    /**
     * @param polarAngle
     */
    public void setPolarAngle(Measurement polarAngle) {
        this.polarAngle = polarAngle;
    }
    
    /**
     * @return azimuthAngle
     */
    public Measurement getAzimuthAngle() {
        return azimuthAngle;
    }
    
    /**
     * @param azimuthAngle
     */
    public void setAzimuthAngle(Measurement azimuthAngle) {
        this.azimuthAngle = azimuthAngle;
    }
    
    /**
     * @return thrust
     */
    public DataTable<Number> getThrust() {
        return thrust;
    }
    
    /**
     * @param thrust
     */
    public void setThrust(DataTable<Number> thrust) {
        this.thrust = thrust;
    }
}
