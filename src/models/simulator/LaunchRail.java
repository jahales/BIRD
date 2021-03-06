package models.simulator;

import models.Measurement;

/**
 * A launch rail
 *
 * @author Brian Woodruff
 *
 */
public class LaunchRail {
  private Measurement length = new Measurement();
  private Measurement polarAngle = new Measurement();
  private Measurement azimuthAngle = new Measurement();

  /**
   * @return length
   */
  public Measurement getLength() {
    return length;
  }

  /**
   * @param length
   */
  public void setLength(Measurement length) {
    this.length = length;
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
}
