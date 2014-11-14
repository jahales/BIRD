package models.rocket.parts;

import models.Measurement;


/**
 * A parachute
 * 
 * @author Brian Woodruff
 *
 */
public class Parachute extends CircularCylinder {
    private Measurement dragCoefficient;
    private Measurement deployedDiameter;
    private Measurement deploymentAltitude;
    
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
}
