package models.rocketmodel.parts;

import models.Measurement;


/**
 * A circular cylinder
 * 
 * @author Brian Woodruff
 *
 */
public class CircularCylinder extends RocketComponent {
    private Measurement diameter;

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
    
}
