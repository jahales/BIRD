package models.rocket.parts;

import models.Measurement;
import models.Unit;


/**
 * A conical frustum
 * 
 * @author Brian Woodruff
 *
 */
public class ConicalFrustum extends RocketComponent {
    private Measurement upperDiameter  = new Measurement(0, 0, Unit.meters);
    private Measurement lowerDiameter = new Measurement(0, 0, Unit.meters);
    
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
