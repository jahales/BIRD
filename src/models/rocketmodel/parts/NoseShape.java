package models.rocketmodel.parts;

/**
 *
 * @author Jacob
 */
public enum NoseShape {

  /**
   *
   */
  UNKNOWN,

  /**
   *
   */
  OGIVE,

  /**
   *
   */
  PARABOLA,

  /**
   *
   */
  CONE,

  /**
   *
   */
  POWERSERIES;
  
  /**
   *
   * @param noseShape
   * @return
   */
  public static NoseShape parseString(String noseShape) 
  {
    switch (noseShape.toUpperCase())
    {
      case "OGIVE":
        return OGIVE;
      case "PARABOLOA":
        return PARABOLA;
      case "CONE":
        return CONE;
      case "POWERSERIES":
        return POWERSERIES;
      default:
        return UNKNOWN;
    }
  }
}
