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
   *
   * @return
   */
  public Measurement getTipChord() {
    return tipChord;
  }

  /**
   *
   * @param tipChord
   */
  public void setTipChord(Measurement tipChord) {
    this.tipChord = tipChord;
  }

  /**
   * @return count
   */
  public int getCount() {
    return count;
  }

  /**
   * @param count
   */
  public void setCount(int count) {
    this.count = count;
  }

  /**
   * @return rootChord
   */
  public Measurement getRootChord() {
    return rootChord;
  }

  /**
   * @param rootChord
   */
  public void setRootChord(Measurement rootChord) {
    this.rootChord = rootChord;
  }

  /**
   * @return spanLength
   */
  public Measurement getSpanLength() {
    return spanLength;
  }

  /**
   * @param spanLength
   */
  public void setSpanLength(Measurement spanLength) {
    this.spanLength = spanLength;
  }

  /**
   * @return sweepLength
   */
  public Measurement getSweepLength() {
    return sweepLength;
  }

  /**
   * @param sweepLength
   */
  public void setSweepLength(Measurement sweepLength) {
    this.sweepLength = sweepLength;
  }

  /**
   * @return cantAngle
   */
  public Measurement getCantAngle() {
    return cantAngle;
  }

  /**
   * @param cantAngle
   */
  public void setCantAngle(Measurement cantAngle) {
    this.cantAngle = cantAngle;
  }

  /**
   * @return bodyDiameter
   */
  public Measurement getBodyDiameter() {
    return bodyDiameter;
  }

  /**
   * @param bodyDiameter
   */
  public void setBodyDiameter(Measurement bodyDiameter) {
    this.bodyDiameter = bodyDiameter;
  }
}
