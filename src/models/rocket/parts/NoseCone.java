package models.rocket.parts;

import models.Measurement;
import models.Unit;

/**
 * A rocket nose cone with a selected nose shape.
 *
 * @author Brian Woodruff, Jacob Hales
 *
 */
public class NoseCone extends RocketComponent {

  private double shapeParameter = 0.0;
  private Measurement diameter = new Measurement(0, 0, Unit.meters);
  private NoseShape noseShape = NoseShape.OGIVE;

  /**
   * @return A NoseShape specifying the shape.
   */
  public NoseShape getNoseShape() {
    return noseShape;
  }

  /**
   * @param noseShape A NoseShape specifying the shape.
   */
  public void setNoseShape(NoseShape noseShape) {
    this.noseShape = noseShape;
  }

  /**
   * @return Diameter at the base of the nose.
   */
  public Measurement getDiameter() {
    return diameter;
  }

  /**
   * @param diameter Diameter at the base of the nose.
   */
  public void setDiameter(Measurement diameter) {
    this.diameter = diameter;
  }

  /**
   * @return Shape parameter of the nose cone.
   */
  public double getShapeParameter() {
    return shapeParameter;
  }

  /**
   * @param shapeParameter Shape parameter of the nose cone.
   */
  public void setShapeParameter(double shapeParameter) {
    this.shapeParameter = shapeParameter;
  }
  
    /**
   * @return True if the nose shape has a shape parameter
   */
  public Boolean getHasShapeParameter() {
    switch (noseShape) {
      case POWERSERIES:
        return true;
    }

    return false;
  }

  /**
   * @return A description of the nose cone shape parameter.
   */
  public String getShapeParameterDescription() {
    switch (noseShape) {
      case POWERSERIES:
        return "Exponent of the power series function.";
    }

    return "";
  }
}
