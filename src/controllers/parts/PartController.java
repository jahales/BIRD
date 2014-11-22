package controllers.parts;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.Measurement;
import models.Unit;
import models.rocket.parts.RocketComponent;

public abstract class PartController {
  @FXML
  private TextField name;

  @FXML
  private TextField massValue;

  @FXML
  private TextField axialLengthValue;

  @FXML
  private TextField axialOffsetValue;

  @FXML
  private TextField radialOffsetValue;

  @FXML
  private TextField thicknessValue;

  @FXML
  private TextField massError;

  @FXML
  private TextField axialLengthError;

  @FXML
  private TextField axialOffsetError;

  @FXML
  private TextField radialOffsetError;

  @FXML
  private TextField thicknessError;

  @FXML
  private ChoiceBox<String> massUnits;

  @FXML
  private ChoiceBox<String> axialLengthUnits;

  @FXML
  private ChoiceBox<String> axialOffsetUnits;
  
  @FXML
  private ChoiceBox<String> radialOffsetUnits;
  
  @FXML
  private ChoiceBox<String> thicknessUnits;
  
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
  
  public PartController(RocketComponent component) {
    name.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> reserved, String old, String current) {
        component.setName(current);
      }
    });
    addValueListener(massValue, component.getMass());
    addValueListener(axialLengthValue, component.getAxialLength());
    addValueListener(axialOffsetValue, component.getAxialOffset());
    addValueListener(radialOffsetValue, component.getRadialOffset());
    addValueListener(thicknessValue, component.getThickness());

    addErrorListener(massValue, component.getMass());
    addErrorListener(axialLengthError, component.getAxialLength());
    addErrorListener(axialOffsetError, component.getAxialOffset());
    addErrorListener(radialOffsetError, component.getRadialOffset());
    addErrorListener(thicknessError, component.getThickness());

    addUnitListener(massUnits, component.getMass());
    addUnitListener(axialLengthUnits, component.getAxialLength());
    addUnitListener(axialOffsetUnits, component.getAxialOffset());
    addUnitListener(radialOffsetUnits, component.getRadialOffset());
    addUnitListener(thicknessUnits, component.getThickness());
  }
}
