package models.rocketmodel.parts;

import models.Measurement;


/**
 * A rocket component
 * 
 * @author Brian Woodruff
 *
 */
public class RocketComponent {
    private String name;
    private Measurement mass;
    private Measurement axialLength;
    private Measurement axialOffset;
    private Measurement radialOffset;
    private Measurement thickness;
    
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
