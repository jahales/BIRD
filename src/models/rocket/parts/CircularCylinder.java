package models.rocket.parts;

import models.Measurement;
import models.Unit;

/**
 * A circular cylinder
 *
 * @author Brian Woodruff
 *
 */
public class CircularCylinder extends RocketComponent {
  private Measurement diameter = new Measurement(0, 0, Unit.meters);

  /**
   * @return a {@link Measurement} for diameter
   */
  public Measurement getDiameter() {
    return diameter;
  }

  /**
   * @param diameter
   *          a {@link Measurement} for diameter
   */
  public void setDiameter(Measurement diameter) {
    this.diameter = diameter;
  }
}
