package controllers.parts;

import controllers.BaseController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.rocket.parts.Motor;

/**
 * Controller for the {@link Motor} view
 *
 * @author Brian Woodruff
 *
 */
public class MotorController extends BaseController {

  private Motor motor;

  @FXML
  private TextField manufacturerValue;

  @FXML
  private TextField delaysValue;

  @FXML
  private TextField fuelMassValue;

  @FXML
  private TextField fuelMassError;

  @FXML
  private ChoiceBox<String> fuelMassUnits;

  @FXML
  private TextField polarAngleValue;

  @FXML
  private TextField polarAngleError;

  @FXML
  private ChoiceBox<String> polarAngleUnits;

  @FXML
  private TextField azimuthAngleValue;

  @FXML
  private TextField azimuthAngleError;

  @FXML
  private ChoiceBox<String> azimuthAngleUnits;

  @FXML
  private Label thrustFile;

  @FXML
  private void loadThrustFile() {
    if (manufacturerValue == null) {
      System.out.println("Motor is null");
    }
  }

  /**
   * @param rocket
   *          a rocket this view will modify
   */
  public MotorController(Motor motor) {
    this.motor = motor;
  }

  /**
   * Initialize values and set listeners
   */
  public void initialize() {
    // Populate fields with whatever values we got
    manufacturerValue.setText(motor.getManufacturer());
    delaysValue.setText(motor.getDelays());

    fuelMassValue.setText(Double.toString(motor.getFuelMass().getValue()));
    fuelMassError.setText(Double.toString(motor.getFuelMass().getError()));
    fuelMassUnits.setValue(motor.getFuelMass().getUnit().toString());

    polarAngleValue.setText(Double.toString(motor.getPolarAngle().getValue()));
    polarAngleError.setText(Double.toString(motor.getPolarAngle().getError()));
    polarAngleUnits.setValue(motor.getPolarAngle().getUnit().toString());

    azimuthAngleValue.setText(Double.toString(motor.getAzimuthAngle().getValue()));
    azimuthAngleError.setText(Double.toString(motor.getAzimuthAngle().getError()));
    azimuthAngleUnits.setValue(motor.getAzimuthAngle().getUnit().toString());

    // Set listeners
    manufacturerValue.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
        motor.setManufacturer(arg2);
      }
    });

    delaysValue.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
        motor.setDelays(arg2);
      }
    });

    ListenerHelpers.addValueListener(fuelMassValue, motor.getFuelMass());
    ListenerHelpers.addValueListener(polarAngleError, motor.getPolarAngle());
    ListenerHelpers.addValueListener(azimuthAngleValue, motor.getAzimuthAngle());

    ListenerHelpers.addErrorListener(fuelMassError, motor.getFuelMass());
    ListenerHelpers.addErrorListener(polarAngleError, motor.getPolarAngle());
    ListenerHelpers.addErrorListener(azimuthAngleError, motor.getAzimuthAngle());

    ListenerHelpers.addUnitListener(fuelMassUnits, motor.getFuelMass());
    ListenerHelpers.addUnitListener(polarAngleUnits, motor.getPolarAngle());
    ListenerHelpers.addUnitListener(azimuthAngleUnits, motor.getAzimuthAngle());
  }
}
