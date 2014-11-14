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
  
  public Measurement(double value, double error, Unit unit) {
    this.value = value;
    this.error = error;
    this.unit = unit;
  }
  
  public Measurement() {
  }


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
   *
   * @return
   */
  public Unit getUnit() {
    return unit;
  }

  /**
   *
   * @param unit
   */
  public void setUnit(Unit unit) {
    this.unit = unit;
  }
}
