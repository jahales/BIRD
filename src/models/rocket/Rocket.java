package models.rocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Measurement;
import models.Unit;
import models.rocket.parts.RocketComponent;

/**
 * A rocket
 *
 * @author Brian Woodruff
 *
 */
public class Rocket {

  private String name = "";
  private Measurement mass = new Measurement(0, 0, Unit.grams);
  private Measurement radialCenterOfMass = new Measurement(0, 0, Unit.other);
  private Measurement radialMomentOfInertia = new Measurement(0, 0, Unit.kilogramSquareMeters);
  private Measurement longitudinalMomentOfInertia = new Measurement(0, 0, Unit.kilogramSquareMeters);
  private List<RocketComponent> interiorComponents = new ArrayList<RocketComponent>();
  private List<RocketComponent> exteriorComponents = new ArrayList<RocketComponent>();
  private Map<String, Measurement> overrides = new HashMap<String, Measurement>();

  /**
   *
   * @return
   */
  public RocketComponent getPartByName(String name) {
    for (RocketComponent component : exteriorComponents) {
      if (component.getName() == name) {
        return component;
      }
    }
    for (RocketComponent component : interiorComponents) {
      if (component.getName() == name) {
        return component;
      }
    }
    return null;
  }

  /**
   *
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   *
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   *
   * @return
   */
  public Measurement getMass() {
    return mass;
  }

  /**
   *
   * @param mass
   */
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
