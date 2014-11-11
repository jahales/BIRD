package models.rocketmodel;

import java.util.List;

import models.rocketmodel.parts.Motor;
import models.rocketmodel.parts.Parachute;
import models.rocketmodel.parts.RocketComponent;

/**
 * A rocket
 * 
 * @author Brian Woodruff
 *
 */
public class Rocket {
    private List<RocketComponent> components;
    private List<RocketComponent> interiorComponents;
    private List<RocketComponent> exteriorComponents;
    private List<Motor> motors;
    private List<Parachute> parachutes;
    private boolean overrideEnabled;
    private Measurement radialCenterOfMass;
    private Measurement radialMomentOfInertia;
    private Measurement longitudinalMomentOfInertia;
    
    /**
     * @return components
     */
    public List<RocketComponent> getComponents() {
        return components;
    }
    
    /**
     * @param components
     */
    public void setComponents(List<RocketComponent> components) {
        this.components = components;
    }
    
    /**
     * @return interiorComponents
     */
    public List<RocketComponent> getInteriorComponents() {
        return interiorComponents;
    }
    
    /**
     * @param interiorComponents
     */
    public void setInteriorComponents(List<RocketComponent> interiorComponents) {
        this.interiorComponents = interiorComponents;
    }
    
    /**
     * @return exteriorComponents
     */
    public List<RocketComponent> getExteriorComponents() {
        return exteriorComponents;
    }
    
    /**
     * @param exteriorComponents
     */
    public void setExteriorComponents(List<RocketComponent> exteriorComponents) {
        this.exteriorComponents = exteriorComponents;
    }
    
    /**
     * @return motors
     */
    public List<Motor> getMotors() {
        return motors;
    }
    
    /**
     * @param motors
     */
    public void setMotors(List<Motor> motors) {
        this.motors = motors;
    }
    
    /**
     * @return parachutes
     */
    public List<Parachute> getParachutes() {
        return parachutes;
    }
    
    /**
     * @param parachutes
     */
    public void setParachutes(List<Parachute> parachutes) {
        this.parachutes = parachutes;
    }
    
    /**
     * @return overrideEnabled
     */
    public boolean isOverrideEnabled() {
        return overrideEnabled;
    }
    
    /**
     * @param overrideEnabled
     */
    public void setOverrideEnabled(boolean overrideEnabled) {
        this.overrideEnabled = overrideEnabled;
    }
    
    /**
     * @return radialCenterOfMass
     */
    public Measurement getRadialCenterOfMass() {
        return radialCenterOfMass;
    }
    
    /**
     * @param radialCenterOfMass
     */
    public void setRadialCenterOfMass(Measurement radialCenterOfMass) {
        this.radialCenterOfMass = radialCenterOfMass;
    }
    
    /**
     * @return radialMomentOfInertia
     */
    public Measurement getRadialMomentOfInertia() {
        return radialMomentOfInertia;
    }
    
    /**
     * @param radialMomentOfInertia
     */
    public void setRadialMomentOfInertia(Measurement radialMomentOfInertia) {
        this.radialMomentOfInertia = radialMomentOfInertia;
    }
    
    /**
     * @return longitudinalMomentOfInertia
     */
    public Measurement getLongitudinalMomentOfInertia() {
        return longitudinalMomentOfInertia;
    }
    
    /**
     * @param longitudinalMomentOfInertia
     */
    public void setLongitudinalMomentOfInertia(Measurement longitudinalMomentOfInertia) {
        this.longitudinalMomentOfInertia = longitudinalMomentOfInertia;
    }
    
}
