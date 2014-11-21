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
    private Measurement dragCoefficient  = new Measurement(0, 0, Unit.meters);
    private Measurement deployedDiameter = new Measurement(0, 0, Unit.meters);
    private Measurement deploymentAltitude = new Measurement(0, 0, Unit.meters);
    private Boolean deployAtApogee = false;
    
    /**
     * @return dragCoefficient
     */
    public Measurement getDragCoefficient() {
        return dragCoefficient;
    }
    
    /**
     * @param dragCoefficient
     */
    public void setDragCoefficient(Measurement dragCoefficient) {
        this.dragCoefficient = dragCoefficient;
    }
    
    /**
     * @return deployedDiameter
     */
    public Measurement getDeployedDiameter() {
        return deployedDiameter;
    }
    
    /**
     * @param deployedDiameter
     */
    public void setDeployedDiameter(Measurement deployedDiameter) {
        this.deployedDiameter = deployedDiameter;
    }
    
    /**
     * @return deploymentAltitude
     */
    public Measurement getDeploymentAltitude() {
        return deploymentAltitude;
    }
    
    /**
     * @param deploymentAltitude
     */
    public void setDeploymentAltitude(Measurement deploymentAltitude) {
        this.deploymentAltitude = deploymentAltitude;
    }

  /**
   *
   * @return
   */
  public Boolean getDeployAtApogee() {
    return deployAtApogee;
  }

  /**
   *
   * @param deployAtApogee
   */
  public void setDeployAtApogee(Boolean deployAtApogee) {
    this.deployAtApogee = deployAtApogee;
  }
}
