package models.rocketmodel.parts;

import models.Measurement;


/**
 * A trapesoid fin set
 * 
 * @author Brian Woodruff
 *
 */
public class TrapezoidFinSet extends RocketComponent {
    private int count;
    private Measurement rootChord;
    private Measurement spanLength;
    private Measurement sweepLength;
    private Measurement cantAngle;
    private Measurement bodyDiameter;
    
    /**
     * @return count
     */
    public int getCount() {
        return count;
    }
    
    /**
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
    }
    
    /**
     * @return rootChord
     */
    public Measurement getRootChord() {
        return rootChord;
    }
    
    /**
     * @param rootChord
     */
    public void setRootChord(Measurement rootChord) {
        this.rootChord = rootChord;
    }
    
    /**
     * @return spanLength
     */
    public Measurement getSpanLength() {
        return spanLength;
    }
    
    /**
     * @param spanLength
     */
    public void setSpanLength(Measurement spanLength) {
        this.spanLength = spanLength;
    }
    
    /**
     * @return sweepLength
     */
    public Measurement getSweepLength() {
        return sweepLength;
    }
    
    /**
     * @param sweepLength
     */
    public void setSweepLength(Measurement sweepLength) {
        this.sweepLength = sweepLength;
    }
    
    /**
     * @return cantAngle
     */
    public Measurement getCantAngle() {
        return cantAngle;
    }
    
    /**
     * @param cantAngle
     */
    public void setCantAngle(Measurement cantAngle) {
        this.cantAngle = cantAngle;
    }
    
    /**
     * @return bodyDiameter
     */
    public Measurement getBodyDiameter() {
        return bodyDiameter;
    }
    
    /**
     * @param bodyDiameter
     */
    public void setBodyDiameter(Measurement bodyDiameter) {
        this.bodyDiameter = bodyDiameter;
    }
}
