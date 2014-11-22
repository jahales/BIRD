package models;

import java.util.List;
import javafx.collections.FXCollections;
import models.Unit;

/**
 * A Measurement contains a value, error and units of measurement. A conversion
 * method is available for converting the units.
 * 
 * @author Brian Woodruff
 *
 */
public class Measurement {
  private double value;
  private double error;
  private Unit unit;

  /**
   * <h1>Preferred Constructor</h1>
   * 
   * @param value
   *          value of measurement
   * @param error
   *          error of measurement
   * @param unit
   *          {@link Unit} of measurement
   */
  public Measurement(double value, double error, Unit unit) {
    this.value = value;
    this.error = error;
    this.unit = unit;
  }

  /**
   * <h1>Default Constructor</h1>
   * <p>
   * <b>Note:</b> Units may not have been initialized, so use {@link setUnit}
   * to avoid errors
   */
  public Measurement() {
  }

  /**
   * @return the value of the {@link Measurement}
   */
  public double getValue() {
    return value;
  }

  /**
   * @param value
   *          the value of the {@link Measurement}
   */
  public void setValue(double value) {
    this.value = value;
  }

  /**
   * @return the error of the {@link Measurement}
   */
  public double getError() {
    return error;
  }

  /**
   * @param error
   *          the error of the {@link Measurement}
   */
  public void setError(double error) {
    this.error = error;
  }

  /**
   *
   * @return the {@link Unit} of measurement
   */
  public Unit getUnit() {
    return unit;
  }

  /**
   *
   * @param unit
   *          the {@link Unit} of measurement
   */
  public void setUnit(Unit unit) {
    this.unit = unit;
  }

  /**
   * Convert the {@link Unit} of a {@link Measurement}. <b>Note:</b> Only works
   * if the Units of measurement are of the same type as your unit.
   * 
   * @param measurement
   *          The {@link Measurement} you are converting
   * @param unit
   *          The {@link Unit} you are converting to
   * @return A new measurement with your units, unless Units were not the same
   *         type.
   */
  public static Measurement convert(Measurement measurement, Unit unit) {
    if (measurement.getUnit().equals(unit)) {
      return measurement; // Don't bother if it's the same unit
    }
    Unit fromUnit = measurement.getUnit();

    List<Unit> mass = FXCollections
        .observableArrayList(Unit.kilograms, Unit.grams, Unit.milligrams);
    List<Unit> length = FXCollections.observableArrayList(Unit.kilometers, Unit.meters,
        Unit.centimeters, Unit.millimeters);
    List<Unit> time = FXCollections.observableArrayList(Unit.hours, Unit.minutes, Unit.seconds,
        Unit.milliseconds);
    List<Unit> angle = FXCollections.observableArrayList(Unit.degrees, Unit.radians);
    List<Unit> temperature = FXCollections.observableArrayList(Unit.fahrenheit, Unit.celsius);
    List<Unit> momentOfInertia = FXCollections.observableArrayList(Unit.kilogramSquareMeters,
        Unit.poundSquareFeet);

    if (mass.contains(fromUnit) && mass.contains(unit)) { // Mass
      switch (fromUnit) {
      case kilograms:
        switch (unit) {
        case grams:
          return new Measurement(measurement.value * 1000.0, measurement.error * 1000.0, Unit.grams);
        default:
          return new Measurement(measurement.value * 1000000.0, measurement.error * 1000000.0,
              Unit.milligrams);
        }
      case grams:
        switch (unit) {
        case kilograms:
          return new Measurement(measurement.value / 1000.0, measurement.error / 1000.0,
              Unit.kilograms);
        default:
          return new Measurement(measurement.value * 1000.0, measurement.error * 1000.0,
              Unit.milligrams);
        }
      default: // milligrams
        switch (unit) {
        case kilograms:
          return new Measurement(measurement.value / 1000000.0, measurement.error / 1000000.0,
              Unit.kilograms);
        default:
          return new Measurement(measurement.value / 1000.0, measurement.error / 1000.0, Unit.grams);
        }
      }
    } else if (length.contains(fromUnit) && length.contains(unit)) { // Length
      switch (fromUnit) {
      case kilometers:
        switch (unit) {
        case meters:
          return new Measurement(measurement.value * 1000.0, measurement.error * 1000.0,
              Unit.meters);
        case centimeters:
          return new Measurement(measurement.value * 100000.0, measurement.error * 100000.0,
              Unit.centimeters);
        default:
          return new Measurement(measurement.value * 1000000.0, measurement.error * 1000000.0,
              Unit.centimeters);
        }
      case meters:
        switch (unit) {
        case kilometers:
          return new Measurement(measurement.value / 1000.0, measurement.error / 1000.0,
              Unit.kilometers);
        case centimeters:
          return new Measurement(measurement.value * 100.0, measurement.error * 100.0,
              Unit.centimeters);
        default:
          return new Measurement(measurement.value * 1000.0, measurement.error * 1000.0,
              Unit.millimeters);
        }
      case centimeters:
        switch (unit) {
        case kilometers:
          return new Measurement(measurement.value / 100000.0, measurement.error / 100000.0,
              Unit.kilometers);
        case meters:
          return new Measurement(measurement.value / 100.0, measurement.error, Unit.meters);
        default:
          return new Measurement(measurement.value * 10.0, measurement.error, Unit.millimeters);
        }
      default: // millimeters
        switch (unit) {
        case kilometers:
          return new Measurement(measurement.value / 1000000.0, measurement.error / 1000000.0,
              Unit.kilometers);
        case meters:
          return new Measurement(measurement.value / 1000.0, measurement.error / 1000.0,
              Unit.meters);
        default:
          return new Measurement(measurement.value / 10.0, measurement.error / 10.0,
              Unit.centimeters);
        }
      }
    } else if (time.contains(fromUnit) && time.contains(unit)) { // Time
      switch (fromUnit) {
      case hours:
        switch (unit) {
        case minutes:
          return new Measurement(measurement.value * 60.0, measurement.error * 60.0, Unit.minutes);
        case seconds:
          return new Measurement(measurement.value * 3600.0, measurement.error * 3600.0,
              Unit.seconds);
        default:
          return new Measurement(measurement.value * 3600000.0, measurement.error * 3600000.0,
              Unit.milliseconds);
        }
      case minutes:
        switch (unit) {
        case hours:
          return new Measurement(measurement.value / 60.0, measurement.error / 60.0, Unit.hours);
        case seconds:
          return new Measurement(measurement.value * 60.0, measurement.error * 60.0, Unit.seconds);
        default:
          return new Measurement(measurement.value * 60000.0, measurement.error * 60000.0,
              Unit.milliseconds);
        }
      case seconds:
        switch (unit) {
        case hours:
          return new Measurement(measurement.value / 3600.0, measurement.error / 3600.0, Unit.hours);
        case minutes:
          return new Measurement(measurement.value / 60.0, measurement.error / 60.0, Unit.minutes);
        default:
          return new Measurement(measurement.value * 1000.0, measurement.error * 1000.0,
              Unit.milliseconds);
        }
      default: // milliseconds
        switch (unit) {
        case hours:
          return new Measurement(measurement.value / 3600000.0, measurement.error / 3600000.0,
              Unit.hours);
        case minutes:
          return new Measurement(measurement.value / 60000.0, measurement.error / 60000.0,
              Unit.minutes);
        default:
          return new Measurement(measurement.value / 1000.0, measurement.error / 1000.0,
              Unit.seconds);
        }
      }
    } else if (angle.contains(fromUnit) && angle.contains(unit)) { // Angle
      if (unit.equals(Unit.degrees)) {
        return new Measurement(measurement.value * 180.0 / Math.PI, measurement.error * 180
            / Math.PI, Unit.degrees);
      } else {
        return new Measurement(measurement.value * Math.PI / 180.0, measurement.error * Math.PI
            / 180.0, Unit.radians);
      }
    } else if (temperature.contains(fromUnit) && temperature.contains(unit)) {
      if (unit.equals(Unit.fahrenheit)) {
        return new Measurement((measurement.value - 32.0) / 1.8, measurement.error, Unit.fahrenheit);
      } else {
        return new Measurement(measurement.value * 1.8 + 32.0, measurement.error, Unit.celsius);
      }
    } else if (momentOfInertia.contains(fromUnit) && momentOfInertia.contains(unit)) {
      if (unit.equals(Unit.kilogramSquareMeters)) {
        return new Measurement(measurement.value / 23.730360404, measurement.error / 23.730360404,
            Unit.kilogramSquareMeters);
      } else {
        return new Measurement(measurement.value * 23.730360404, measurement.error * 23.730360404,
            Unit.poundSquareFeet);
      }
    }

    return measurement; // Other
  }
}
