/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.parts;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.Measurement;
import models.Unit;

/**
 *
 * @author Jacob
 */
public class ListenerHelpers {

  /**
   * Updates the measurement when user selects a unit
   *
   * @param field
   *          ChoiceBox to add listener to
   * @param measurement
   *          measurement to set
   */
  public static void addUnitListener(ChoiceBox<String> field, Measurement measurement) {
    field
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (ChangeListener<String>) (reserved, old, current) -> measurement.setUnit(Unit
                .valueOf(current)));
  }

  /**
   * Updates the value when the user changes it
   *
   * @param field
   *          TextField to add listner to
   * @param measurement
   *          measurement to set
   */
  public static void addValueListener(TextField field, Measurement measurement) {
    field.textProperty().addListener((ChangeListener<String>) (reserved, old, current) -> {
      try {
        measurement.setValue(Double.parseDouble(current));
      } catch (NumberFormatException e) {
        field.setText(old); // Set to old value
      }
    });
  }

  /**
   * Updates the error when the user changes it
   *
   * @param field
   *          TextField to add listener to
   * @param measurement
   *          measurement to set
   */
  public static void addErrorListener(TextField field, Measurement measurement) {
    field.textProperty().addListener((ChangeListener<String>) (reserved, old, current) -> {
      try {
        measurement.setError(Double.parseDouble(current));
      } catch (NumberFormatException e) {
        field.setText(old); // Set to old value
      }
    });
  }
}
