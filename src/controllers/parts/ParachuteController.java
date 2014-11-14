package controllers.parts;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.Measurement;
import models.Unit;
import models.rocket.parts.Parachute;

/**
 * Controller for the parachute view
 * 
 * @author Brian Woodruff
 *
 */
public class ParachuteController {
  private Parachute parachute = new Parachute();

  @FXML
  private TextField dragCoefficientValue;

  @FXML
  private TextField deployedDiameterValue;

  @FXML
  private TextField deploymentAltitudeValue;

  @FXML
  private TextField dragCoefficientError;

  @FXML
  private TextField deployedDiameterError;

  @FXML
  private TextField deploymentAltitudeError;

  @FXML
  private ChoiceBox<String> dragCoefficientUnits;

  @FXML
  private ChoiceBox<String> deployedDiameterUnits;

  @FXML
  private ChoiceBox<String> deploymentAltitudeUnits;
  
  /**
   * Get the parachute part
   * 
   * @return parachute
   */
  public Parachute getParachute() {
    return parachute;
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
   * Initialize the parachute part and add listeners
   */
  public void initialize() {
    parachute.setDragCoefficient(new Measurement(0, 0, Unit.Number));
    parachute.setDeployedDiameter(new Measurement(0, 0, Unit.centiMeter));
    parachute.setDeploymentAltitude(new Measurement(0, 0, Unit.Meter));
    
    addValueListener(deployedDiameterValue, parachute.getDeployedDiameter());
    addValueListener(deploymentAltitudeValue, parachute.getDeploymentAltitude());
    addValueListener(dragCoefficientValue, parachute.getDragCoefficient());
    
    addErrorListener(deployedDiameterError, parachute.getDeployedDiameter());
    addErrorListener(deploymentAltitudeError, parachute.getDeploymentAltitude());
    addErrorListener(dragCoefficientError, parachute.getDragCoefficient());
    
    addUnitListener(deployedDiameterUnits, parachute.getDeployedDiameter());
    addUnitListener(deploymentAltitudeUnits, parachute.getDeploymentAltitude());
    addUnitListener(dragCoefficientUnits, parachute.getDragCoefficient());
  }
}
