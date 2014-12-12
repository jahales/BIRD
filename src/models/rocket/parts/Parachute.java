package models.rocket.parts;

import models.Measurement;
import models.Unit;

/**
 * A parachute
 *
 * @author Brian Woodruff
 *
 */
public class Parachute extends CircularCylinder {
  private Measurement dragCoefficient = new Measurement(0, 0, Unit.meters);
  private Measurement deployedDiameter = new Measurement(0, 0, Unit.meters);
  private Measurement deploymentAltitude = new Measurement(0, 0, Unit.meters);
  private Boolean deployAtApogee = false;

  /**
   * @return a {@link Measurement} for drag coefficient
   */
  public Measurement getDragCoefficient() {
    return dragCoefficient;
  }

  /**
   * @param dragCoefficient
   *          a {@link Measurement} for drage coefficient
   */
  public void setDragCoefficient(Measurement dragCoefficient) {
    this.dragCoefficient = dragCoefficient;
  }

  /**
   * @return a {@link Measurement} for deployed diameter
   */
  public Measurement getDeployedDiameter() {
    return deployedDiameter;
  }

  /**
   * @param deployedDiameter
   *          a {@link Measurement} for deployed diameter
   */
  public void setDeployedDiameter(Measurement deployedDiameter) {
    this.deployedDiameter = deployedDiameter;
  }

  /**
   * @return a {@link Measurement} for deployment altitude
   */
  public Measurement getDeploymentAltitude() {
    return deploymentAltitude;
  }

  /**
   * @param deploymentAltitude
   *          a {@link Measurement} for deployment altitude
   */
  public void setDeploymentAltitude(Measurement deploymentAltitude) {
    this.deploymentAltitude = deploymentAltitude;
  }

  /**
   * @return whether parachute is deployed at apogee
   */
  public Boolean getDeployAtApogee() {
    return deployAtApogee;
  }

  /**
   * @param deployAtApogee
   *          whether parachute is deployed at apogee
   */
  public void setDeployAtApogee(Boolean deployAtApogee) {
    this.deployAtApogee = deployAtApogee;
  }
}
