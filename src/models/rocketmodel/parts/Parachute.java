package models.rocketmodel.parts;

import models.rocketmodel.Measurement;


/**
 * A parachute
 * 
 * @author Brian Woodruff
 *
 */
public class Parachute extends CircularCylinder {
    private Measurement dragCoefficient;
    private Measurement deployedDiametr;
    private Measurement deployedAltitude;
    
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
    public Measurement getDeployedDiametr() {
        return deployedDiametr;
    }
    
    /**
     * @param deployedDiametr
     */
    public void setDeployedDiametr(Measurement deployedDiametr) {
        this.deployedDiametr = deployedDiametr;
    }
    
    /**
     * @return deployedAltitude
     */
    public Measurement getDeployedAltitude() {
        return deployedAltitude;
    }
    
    /**
     * @param deployedAltitude
     */
    public void setDeployedAltitude(Measurement deployedAltitude) {
        this.deployedAltitude = deployedAltitude;
    }
}
