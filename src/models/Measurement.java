package models;

import models.Unit;

/**
 * A Measurement class container.
 * 
 * @author Brian Woodruff
 *
 */
public class Measurement {
    private double value;
    private double error;
    private Unit unit;
    
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
    
    public Unit getUnit() {
        return unit;
    }
    
    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}
