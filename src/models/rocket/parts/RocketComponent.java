package models.rocket.parts;

import models.Measurement;
import models.Unit;

/**
 * A rocket component
 *
 * @author Brian Woodruff
 *
 */
public class RocketComponent {
  private String name = "";
  private Measurement mass = new Measurement(0, 0, Unit.kilograms);
  private Measurement axialLength = new Measurement(0, 0, Unit.meters);
  private Measurement axialOffset = new Measurement(0, 0, Unit.meters);
  private Measurement radialOffset = new Measurement(0, 0, Unit.meters);
  private Measurement thickness = new Measurement(0, 0, Unit.meters);

  /**
   * @return name of component
   */
  public String getName() {
    return this.name;
  }

  /**
   * @param name
   *          name of component
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return a {@link Measurement} for mass
   */
  public Measurement getMass() {
    return this.mass;
  }

  /**
   * @param mass
   *          a {@link Measurement} for mass
   */
  public void setMass(Measurement mass) {
    this.mass = mass;
  }

  /**
   * @return a {@link Measurement} for axial length
   */
  public Measurement getAxialLength() {
    return this.axialLength;
  }

  /**
   * @param axialLength
   *          a {@link Measurement} for axial length
   */
  public void setAxialLength(Measurement axialLength) {
    this.axialLength = axialLength;
  }

  /**
   * @return a {@link Measurement} for axial offset
   */
  public Measurement getAxialOffset() {
    return this.axialOffset;
  }

  /**
   * @param axialOffset
   *          a {@link Measurement} for axial offset
   */
  public void setAxialOffset(Measurement axialOffset) {
    this.axialOffset = axialOffset;
  }

  /**
   * @return a {@link Measurement} for radial offset
   */
  public Measurement getRadialOffset() {
    return this.radialOffset;
  }

  /**
   * @param radialOffset
   *          a {@link Measurement} for radial offset
   */
  public void setRadialOffset(Measurement radialOffset) {
    this.radialOffset = radialOffset;
  }

  /**
   * @return a {@link Measurement} for thickness
   */
  public Measurement getThickness() {
    return this.thickness;
  }

  /**
   * @param thickness
   *          a {@link Measurement} for thickness
   */
  public void setThickness(Measurement thickness) {
    this.thickness = thickness;
  }
}
