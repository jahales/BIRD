package models.rocketmodel.parts;

import models.rocketmodel.Measurement;


/**
 * A conical frustum
 * 
 * @author Brian Woodruff
 *
 */
public class ConicalFrustum extends RocketComponent {
    private Measurement upperDiameter;
    private Measurement lowerDiameter;
    
    /**
     * @return upperDiameter
     */
    public Measurement getUpperDiameter() {
        return upperDiameter;
    }
    
    /**
     * @param upperDiameter
     */
    public void setUpperDiameter(Measurement upperDiameter) {
        this.upperDiameter = upperDiameter;
    }
    
    /**
     * @return lowerDiameter
     */
    public Measurement getLowerDiameter() {
        return lowerDiameter;
    }
    
    /**
     * @param lowerDiameter
     */
    public void setLowerDiameter(Measurement lowerDiameter) {
        this.lowerDiameter = lowerDiameter;
    }
}
