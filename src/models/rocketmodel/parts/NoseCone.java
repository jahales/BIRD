package models.rocketmodel.parts;

import models.Measurement;

/**
 * A nose cone. Please define 'shape'
 *
 * @author Brian Woodruff
 *
 */
public class NoseCone extends RocketComponent {

  // private shape;

  private double shapeParameter;
  private Measurement diameter;
  private NoseShape noseShape;

  public NoseShape getNoseShape() {
    return noseShape;
  }

  public void setNoseShape(NoseShape noseShape) {
    this.noseShape = noseShape;
  }

  /**
   * @return diameter
   */
  public Measurement getDiameter() {
    return diameter;
  }

  /**
   * @param diameter
   */
  public void setDiameter(Measurement diameter) {
    this.diameter = diameter;
  }

  /**
   * @return shapeParameter
   */
  public double getShapeParameter() {
    return shapeParameter;
  }

  /**
   * @param shapeParameter
   */
  public void setShapeParameter(double shapeParameter) {
    this.shapeParameter = shapeParameter;
  }
}
