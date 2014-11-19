package models.rocket.parts;

import models.Measurement;
import models.Unit;


/**
 * A rocket component
 * 
 * @author Brian Woodruff
 *
 */
public class RocketComponent {
    private String name = "";
    private Measurement mass = new Measurement(0, 0, Unit.kilograms);
    private Measurement axialLength = new Measurement(0, 0, Unit.meters);
    private Measurement axialOffset = new Measurement(0, 0, Unit.meters);
    private Measurement radialOffset = new Measurement(0, 0, Unit.meters);
    private Measurement thickness = new Measurement(0, 0, Unit.meters);
    
    /**
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return mass
     */
    public Measurement getMass() {
        return mass;
    }
    
    /**
     * @param mass
     */
    public void setMass(Measurement mass) {
        this.mass = mass;
    }
    
    /**
     * @return axialLength
     */
    public Measurement getAxialLength() {
        return axialLength;
    }
    
    /**
     * @param axialLength
     */
    public void setAxialLength(Measurement axialLength) {
        this.axialLength = axialLength;
    }
    
    /**
     * @return axialOffset
     */
    public Measurement getAxialOffset() {
        return axialOffset;
    }
    
    /**
     * @param axialOffset
     */
    public void setAxialOffset(Measurement axialOffset) {
        this.axialOffset = axialOffset;
    }
    
    /**
     * @return radialOffset
     */
    public Measurement getRadialOffset() {
        return radialOffset;
    }
    
    /**
     * @param radialOffset
     */
    public void setRadialOffset(Measurement radialOffset) {
        this.radialOffset = radialOffset;
    }
    
    /**
     *  @return thickness
     */
    public Measurement getThickness() {
        return thickness;
    }
    
    /**
     * @param thickness
     */
    public void setThickness(Measurement thickness) {
        this.thickness = thickness;
    }
}
