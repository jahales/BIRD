package models.rocket.parts;

import models.report.DataTable;
import models.Measurement;
import models.Unit;

/**
 * A motor
 * 
 * @author Brian Woodruff
 *
 */
public class Motor extends CircularCylinder {
  private String manufacturer = "";
  private String delays = "";
  private Measurement fuelMass = new Measurement(0, 0, Unit.meters);
  private Measurement polarAngle = new Measurement(0, 0, Unit.meters);
  private Measurement azimuthAngle = new Measurement(0, 0, Unit.meters);
  private DataTable thrust;

  /**
   * @return manufacturer
   */
  public String getManufacturer() {
    return manufacturer;
  }

  /**
   * @param manufacturer
   *          name of manufacturer
   */
  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  /**
   * @return a string of delays
   */
  public String getDelays() {
    return delays;
  }

  /**
   * @param delays
   *          a string of delays
   */
  public void setDelays(String delays) {
    this.delays = delays;
  }

  /**
   * @return a {@link Measurement} for fuel mass
   */
  public Measurement getFuelMass() {
    return fuelMass;
  }

  /**
   * @param fuelMass
   *          a {@link Measurement} for fuel mass
   */
  public void setFuelMass(Measurement fuelMass) {
    this.fuelMass = fuelMass;
  }

  /**
   * @return a {@link Measurement} for polar angle
   */
  public Measurement getPolarAngle() {
    return polarAngle;
  }

  /**
   * @param polarAngle
   *          a {@link Measurement} for polar angle
   */
  public void setPolarAngle(Measurement polarAngle) {
    this.polarAngle = polarAngle;
  }

  /**
   * @return a {@link Measurement} for azimuth angle
   */
  public Measurement getAzimuthAngle() {
    return azimuthAngle;
  }

  /**
   * @param azimuthAngle
   *          a {@link Measurement} for azimuth angle
   */
  public void setAzimuthAngle(Measurement azimuthAngle) {
    this.azimuthAngle = azimuthAngle;
  }

  /**
   * @return thrust {@link DataTable}
   */
  public DataTable getThrust() {
    return thrust;
  }

  /**
   * @param thrust
   *          thrust {@link DataTable}
   */
  public void setThrust(DataTable thrust) {
    this.thrust = thrust;
  }
}
