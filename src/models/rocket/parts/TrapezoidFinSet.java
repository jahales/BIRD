package models.rocket.parts;

import models.Measurement;
import models.Unit;

/**
 * A trapezoid fin set
 *
 * @author Brian Woodruff
 *
 */
public class TrapezoidFinSet extends RocketComponent {
  private int count;
  private Measurement rootChord = new Measurement(0, 0, Unit.meters);
  private Measurement tipChord = new Measurement(0, 0, Unit.meters);
  private Measurement spanLength = new Measurement(0, 0, Unit.meters);
  private Measurement sweepLength = new Measurement(0, 0, Unit.meters);
  private Measurement cantAngle = new Measurement(0, 0, Unit.radians);
  private Measurement bodyDiameter = new Measurement(0, 0, Unit.meters);

  /**
   * @return a {@link Measurement} for tip chord
   */
  public Measurement getTipChord() {
    return this.tipChord;
  }

  /**
   * @param tipChord
   *          a {@link Measurement} for tip chord
   */
  public void setTipChord(Measurement tipChord) {
    this.tipChord = tipChord;
  }

  /**
   * @return number of fins
   */
  public int getCount() {
    return this.count;
  }

  /**
   * @param count
   *          number of fins
   */
  public void setCount(int count) {
    this.count = count;
  }

  /**
   * @return a {@link Measurement} for root chord
   */
  public Measurement getRootChord() {
    return this.rootChord;
  }

  /**
   * @param rootChord
   *          a {@link Measurement} for root chord
   */
  public void setRootChord(Measurement rootChord) {
    this.rootChord = rootChord;
  }

  /**
   * @return a {@link Measurement} for span length
   */
  public Measurement getSpanLength() {
    return this.spanLength;
  }

  /**
   * @param spanLength
   *          a {@link Measurement} for span length
   */
  public void setSpanLength(Measurement spanLength) {
    this.spanLength = spanLength;
  }

  /**
   * @return a {@link Measurement} for sweep length
   */
  public Measurement getSweepLength() {
    return this.sweepLength;
  }

  /**
   * @param sweepLength
   *          a {@link Measurement} for sweep length
   */
  public void setSweepLength(Measurement sweepLength) {
    this.sweepLength = sweepLength;
  }

  /**
   * @return a {@link Measurement} for cant angle
   */
  public Measurement getCantAngle() {
    return this.cantAngle;
  }

  /**
   * @param cantAngle
   *          a {@link Measurement} for cant angle
   */
  public void setCantAngle(Measurement cantAngle) {
    this.cantAngle = cantAngle;
  }

  /**
   * @return a {@link Measurement} for body diameter
   */
  public Measurement getBodyDiameter() {
    return this.bodyDiameter;
  }

  /**
   * @param bodyDiameter
   *          a {@link Measurement} for body diamter
   */
  public void setBodyDiameter(Measurement bodyDiameter) {
    this.bodyDiameter = bodyDiameter;
  }
}
