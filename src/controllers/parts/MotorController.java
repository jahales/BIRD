package controllers.parts;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.FileHelper;
import models.rocket.parts.Motor;
import controllers.BaseController;

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
    // TODO: Joe implement me
    // update <thrustFile>
    String fileName = FileHelper.openMotorFile(this.thrustFile);
    this.thrustFile.setText(fileName);
    this.motor.setThrustFile(fileName);
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
    this.manufacturerValue.setText(this.motor.getManufacturer());
    this.delaysValue.setText(this.motor.getDelays());

    this.fuelMassValue.setText(Double.toString(this.motor.getFuelMass().getValue()));
    this.fuelMassError.setText(Double.toString(this.motor.getFuelMass().getError()));
    this.fuelMassUnits.setValue(this.motor.getFuelMass().getUnit().toString());

    this.polarAngleValue.setText(Double.toString(this.motor.getPolarAngle().getValue()));
    this.polarAngleError.setText(Double.toString(this.motor.getPolarAngle().getError()));
    this.polarAngleUnits.setValue(this.motor.getPolarAngle().getUnit().toString());

    this.azimuthAngleValue.setText(Double.toString(this.motor.getAzimuthAngle().getValue()));
    this.azimuthAngleError.setText(Double.toString(this.motor.getAzimuthAngle().getError()));
    this.azimuthAngleUnits.setValue(this.motor.getAzimuthAngle().getUnit().toString());

    // Set listeners
    this.manufacturerValue.textProperty().addListener(
        (ChangeListener<String>) (observable, oldValue, newValue) -> MotorController.this.motor
            .setManufacturer(newValue));
    this.delaysValue.textProperty().addListener(
        (ChangeListener<String>) (observable, oldValue, newValue) -> MotorController.this.motor
            .setDelays(newValue));

    ListenerHelpers.addValueListener(this.fuelMassValue, this.motor.getFuelMass());
    ListenerHelpers.addValueListener(this.polarAngleError, this.motor.getPolarAngle());
    ListenerHelpers.addValueListener(this.azimuthAngleValue, this.motor.getAzimuthAngle());

    ListenerHelpers.addErrorListener(this.fuelMassError, this.motor.getFuelMass());
    ListenerHelpers.addErrorListener(this.polarAngleError, this.motor.getPolarAngle());
    ListenerHelpers.addErrorListener(this.azimuthAngleError, this.motor.getAzimuthAngle());

    ListenerHelpers.addUnitListener(this.fuelMassUnits, this.motor.getFuelMass());
    ListenerHelpers.addUnitListener(this.polarAngleUnits, this.motor.getPolarAngle());
    ListenerHelpers.addUnitListener(this.azimuthAngleUnits, this.motor.getAzimuthAngle());
  }
}
