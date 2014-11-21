package controllers.parts;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.Measurement;
import models.Unit;

public abstract class PartController {
  /**
   * Updates the unit when user selects a unit.
   *
   * @param field
   * @param measurement
   */
  void addUnitListener(ChoiceBox<String> field, Measurement measurement) {
    field.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> reserved, String old, String current) {
        measurement.setUnit(Unit.valueOf(current));
      }
    });
  }

  /**
   * Updates the value when the user changes it.
   *
   * @param field
   * @param measurement
   */
  void addValueListener(TextField field, Measurement measurement) {
    field.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> reserved, String old, String current) {
        try {
          measurement.setValue(Double.parseDouble(current));
        } catch (NumberFormatException e) {
          field.setText(old); // Set to old value
        }
      }
    });
  }

  /**
   * Updates the error when the user changes it.
   *
   * @param field
   * @param measurement
   */
  void addErrorListener(TextField field, Measurement measurement) {
    field.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> reserved, String old, String current) {
        try {
          measurement.setError(Double.parseDouble(current));
        } catch (NumberFormatException e) {
          field.setText(old); // Set to old value
        }
      }
    });
  }
}
