package controllers.parts;

import models.Measurement;
import models.Unit;
import models.rocket.parts.CircularCylinder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * Controller for Circular Cylinder view.
 * 
 * @author Brian Woodruff
 *
 */
public class CircularCylinderController {
  private CircularCylinder circularCylinder = new CircularCylinder();
  
  @FXML
  private TextField diameterValue;
  
  @FXML
  private TextField diameterError;

  @FXML
  private ChoiceBox<String> diameterUnits;
  
  /**
   * Get the circular cylinder
   * 
   * @return circularCylinder
   */
  public CircularCylinder getCircularCylinder() {
    return circularCylinder;
  }
  
  /**
   * Updates the unit when user selects a unit.
   * 
   * @param field
   * @param measurement
   */
  private void addUnitListener(ChoiceBox<String> field, Measurement measurement) {
    field.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
        measurement.setUnit(Unit.valueOf(arg2));
      }
    });
  }
  
  /**
   * Updates the value when the user changes it.
   * 
   * @param field
   * @param measurement
   */
  private void addValueListener(TextField field, Measurement measurement) {
    field.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
        try {
          measurement.setValue(Double.parseDouble(arg2));
        } catch (NumberFormatException e) {
          e.printStackTrace();
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
  private void addErrorListener(TextField field, Measurement measurement) {
    field.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
        try {
          measurement.setError(Double.parseDouble(arg2));
        } catch (NumberFormatException e) {
          e.printStackTrace();
        }
      }
    });
  }
  
  /**
   * Initialize values for circular cylinder and add listeners.
   */
  public void initialize() {
    try {
      // Assert values are initialized
      if (diameterValue == null) throw new Exception("'diameterValue' not initialized correctly. Please check CircularCylinder.fxml");
      if (diameterError == null) throw new Exception("'diameterError' not initialized correctly. Please check CircularCylinder.fxml");
      if (diameterUnits == null) throw new Exception("'diameterUnits' not initialized correctly. Please check CircularCylinder.fxml");
    } catch (Exception e) {
      System.out.println(e.getLocalizedMessage());
    }
    circularCylinder.setDiameter(new Measurement(0, 0, Unit.centimeters));
    
    addValueListener(diameterValue, circularCylinder.getDiameter());
    
    addErrorListener(diameterError, circularCylinder.getDiameter());
    
    addUnitListener(diameterUnits, circularCylinder.getDiameter());
  }
}
