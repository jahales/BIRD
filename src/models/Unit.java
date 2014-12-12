package models;

/**
 * Mass, Length, Time, Angle, Temperature, Moment of Inertia and Counting Number
 *
 * @author Jacob, Brian
 */
public enum Unit {
  kilograms, grams, milligrams, // Mass
  millimeters, centimeters, meters, kilometers, // Length
  milliseconds, seconds, minutes, hours, // Time
  degrees, radians, // Angle
  celsius, fahrenheit, // Temperature
  kilogramSquareMeters, poundSquareFeet, // Moments of inertia
  number, other // Counting number
}
