package models;

import java.util.List;
import javafx.collections.FXCollections;
import models.Unit;

/**
 * A Measurement class container.
 * 
 * @author Brian Woodruff
 *
 */
public class Measurement {
  private double value;
  private double error;
  private Unit unit;
  
  /**
   *
   * @param value
   * @param error
   * @param unit
   */
  public Measurement(double value, double error, Unit unit) {
    this.value = value;
    this.error = error;
    this.unit = unit;
  }
  
  /**
   *
   */
  public Measurement() {
  }


  /**
   * @return value
   */
  public double getValue() {
    return value;
  }

  /**
   * @param value
   */
  public void setValue(double value) {
    this.value = value;
  }

  /**
   * @return error
   */
  public double getError() {
    return error;
  }

  /**
   * @param error
   */
  public void setError(double error) {
    this.error = error;
  }

  /**
   *
   * @return
   */
  public Unit getUnit() {
    return unit;
  }

  /**
   *
   * @param unit
   */
  public void setUnit(Unit unit) {
    this.unit = unit;
  }
  
  /**
   * Unit conversion
   * 
   * @param from
   * @param toUnit
   * @return newMeasurement
   */
  public static Measurement convert(Measurement from, Unit toUnit) {
    if (from.getUnit().equals(toUnit)) {
      return from; // Don't bother if it's the same unit
    }
    Unit fromUnit = from.getUnit();
    
    List<Unit> mass =            FXCollections.observableArrayList(Unit.kilograms, Unit.grams, Unit.milligrams);
    List<Unit> length =          FXCollections.observableArrayList(Unit.kilometers, Unit.meters, Unit.centimeters, Unit.millimeters);
    List<Unit> time =            FXCollections.observableArrayList(Unit.hours, Unit.minutes, Unit.seconds, Unit.milliseconds);
    List<Unit> angle =           FXCollections.observableArrayList(Unit.degrees, Unit.radians);
    List<Unit> temperature =     FXCollections.observableArrayList(Unit.fahrenheit, Unit.celsius);
    List<Unit> momentOfInertia = FXCollections.observableArrayList(Unit.kilogramSquareMeters, Unit.poundSquareFeet);
    
    if (mass.contains(fromUnit) && mass.contains(toUnit)) {               // Mass
      switch (fromUnit) {
      case kilograms:
        switch (toUnit) {
        case grams: return new Measurement(from.value * 1000.0, from.error * 1000.0, Unit.grams);
        default: return new Measurement(from.value * 1000000.0, from.error * 1000000.0, Unit.milligrams);
        }
      case grams:
        switch (toUnit) {
        case kilograms: return new Measurement(from.value / 1000.0, from.error / 1000.0, Unit.kilograms);
        default: return new Measurement(from.value * 1000.0, from.error * 1000.0, Unit.milligrams);
        }
      default: // milligrams
        switch (toUnit) {
        case kilograms: return new Measurement(from.value / 1000000.0, from.error / 1000000.0, Unit.kilograms);
        default: return new Measurement(from.value / 1000.0, from.error / 1000.0, Unit.grams);
        }
      }
    } else if (length.contains(fromUnit) && length.contains(toUnit)) {    // Length
      switch (fromUnit) {
      case kilometers:
        switch (toUnit) {
        case meters: return new Measurement(from.value * 1000.0, from.error * 1000.0, Unit.meters);
        case centimeters: return new Measurement(from.value * 100000.0, from.error * 100000.0, Unit.centimeters);
        default: return new Measurement(from.value * 1000000.0, from.error * 1000000.0, Unit.centimeters);
        }
      case meters:
        switch (toUnit) {
        case kilometers: return new Measurement(from.value / 1000.0, from.error / 1000.0, Unit.kilometers);
        case centimeters: return new Measurement(from.value * 100.0, from.error * 100.0, Unit.centimeters);
        default: return new Measurement(from.value * 1000.0, from.error * 1000.0, Unit.millimeters);
        }
      case centimeters:
        switch (toUnit) {
        case kilometers: return new Measurement(from.value / 100000.0, from.error / 100000.0, Unit.kilometers);
        case meters: return new Measurement(from.value / 100.0, from.error, Unit.meters);
        default: return new Measurement(from.value * 10.0, from.error, Unit.millimeters);
        }
      default: // millimeters
        switch (toUnit) {
        case kilometers: return new Measurement(from.value / 1000000.0, from.error / 1000000.0, Unit.kilometers);
        case meters: return new Measurement(from.value / 1000.0, from.error / 1000.0, Unit.meters);
        default: return new Measurement(from.value / 10.0, from.error / 10.0, Unit.centimeters);
        }
      }
    } else if (time.contains(fromUnit) && time.contains(toUnit)) {      // Time
      switch (fromUnit) {
      case hours:
        switch (toUnit) {
        case minutes: return new Measurement(from.value * 60.0, from.error * 60.0, Unit.minutes);
        case seconds: return new Measurement(from.value * 3600.0, from.error * 3600.0, Unit.seconds);
        default: return new Measurement(from.value *3600000.0, from.error * 3600000.0, Unit.milliseconds);
        }
      case minutes:
        switch (toUnit) {
        case hours: return new Measurement(from.value / 60.0, from.error / 60.0, Unit.hours);
        case seconds: return new Measurement(from.value * 60.0, from.error * 60.0, Unit.seconds);
        default: return new Measurement(from.value * 60000.0, from.error * 60000.0, Unit.milliseconds);
        }
      case seconds:
        switch (toUnit) {
        case hours: return new Measurement(from.value / 3600.0, from.error / 3600.0, Unit.hours);
        case minutes: return new Measurement(from.value / 60.0, from.error / 60.0, Unit.minutes);
        default: return new Measurement(from.value * 1000.0, from.error * 1000.0, Unit.milliseconds);
        }
      default: // milliseconds
        switch (toUnit) {
        case hours: return new Measurement(from.value / 3600000.0, from.error / 3600000.0, Unit.hours);
        case minutes: return new Measurement(from.value / 60000.0, from.error / 60000.0, Unit.minutes);
        default: return new Measurement(from.value / 1000.0, from.error / 1000.0, Unit.seconds);
        }
      }
    } else if (angle.contains(fromUnit) && angle.contains(toUnit)) {      // Angle
      if (toUnit.equals(Unit.degrees)) {
        return new Measurement(from.value * 180.0 / Math.PI, from.error * 180 / Math.PI, Unit.degrees);
      } else {
        return new Measurement(from.value * Math.PI / 180.0, from.error * Math.PI / 180.0, Unit.radians);
      }
    } else if (temperature.contains(fromUnit) && temperature.contains(toUnit)) {
      if (toUnit.equals(Unit.fahrenheit)) {
        return new Measurement((from.value - 32.0) / 1.8, from.error, Unit.fahrenheit);
      } else {
        return new Measurement(from.value * 1.8 + 32.0, from.error, Unit.celsius);
      }
    } else if (momentOfInertia.contains(fromUnit) && momentOfInertia.contains(toUnit)) {
      if (toUnit.equals(Unit.kilogramSquareMeters)) {
        return new Measurement(from.value / 23.730360404, from.error / 23.730360404, Unit.kilogramSquareMeters);
      } else {
        return new Measurement(from.value * 23.730360404, from.error * 23.730360404, Unit.poundSquareFeet);
      }
    }
    
    return from; // Other
  }
}
