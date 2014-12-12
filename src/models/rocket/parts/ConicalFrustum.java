package models.rocket.parts;

import models.Measurement;
import models.Unit;

/**
 * A conical frustum
 *
 * @author Brian Woodruff
 *
 */
public class ConicalFrustum extends RocketComponent {
  private Measurement upperDiameter = new Measurement(0, 0, Unit.meters);
  private Measurement lowerDiameter = new Measurement(0, 0, Unit.meters);

  /**
   * @return a {@link Measurement} for upper diameter
   */
  public Measurement getUpperDiameter() {
    return this.upperDiameter;
  }

  /**
   * @param upperDiameter
   *          a {@link Measurement} for upper diameter
   */
  public void setUpperDiameter(Measurement upperDiameter) {
    this.upperDiameter = upperDiameter;
  }

  /**
   * @return a {@link Measurement} for lower diameter
   */
  public Measurement getLowerDiameter() {
    return this.lowerDiameter;
  }

  /**
   * @param lowerDiameter
   *          a {@link Measurement} for lower diameter
   */
  public void setLowerDiameter(Measurement lowerDiameter) {
    this.lowerDiameter = lowerDiameter;
  }
}
