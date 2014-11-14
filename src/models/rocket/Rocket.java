package models.rocketmodel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Measurement;
import models.rocket.parts.Motor;
import models.rocket.parts.Parachute;
import models.rocket.parts.RocketComponent;

/**
 * A rocket
 *
 * @author Brian Woodruff
 *
 */
public class Rocket {

  private Measurement mass;
  private Measurement radialCenterOfMass;
  private Measurement radialMomentOfInertia;
  private Measurement longitudinalMomentOfInertia;
  private List<RocketComponent> interiorComponents;
  private List<RocketComponent> exteriorComponents;
  private Map<String, Measurement> overrides = new HashMap<String, Measurement>();

  public Measurement getMass() {
    return mass;
  }

  public void setMass(Measurement mass) {
    this.mass = mass;
  }

  /**
   *
   * @return
   */
  public Map<String, Measurement> getOverrides() {
    return overrides;
  }

  /**
   *
   * @param overrides
   */
  public void setOverrides(Map<String, Measurement> overrides) {
    this.overrides = overrides;
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
