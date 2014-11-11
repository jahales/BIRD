package models.rocketmodel;

/**
 * A Measurement class container.
 * 
 * @author Brian Woodruff
 *
 */
public class Measurement {
    private double value;
    private double error;
    private double Enum;
    
    /**
     * @return value
     */
    public double getValue() {
        return value;
    }
    
    /**
     * @param value
     */
    public void setValue(double value) {
        this.value = value;
    }
    
    /**
     * @return error
     */
    public double getError() {
        return error;
    }
    
    /**
     * @param error
     */
    public void setError(double error) {
        this.error = error;
    }
    
    /**
     * @return Enum
     */
    public double getEnum() {
        return Enum;
    }
    
    /**
     * @param enum1
     */
    public void setEnum(double Enum) {
        this.Enum = Enum;
    }
}
