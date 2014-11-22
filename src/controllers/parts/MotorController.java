package controllers.parts;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Measurement;
import models.Unit;
import models.report.DataTable;
import models.rocket.Rocket;
import models.rocket.parts.Motor;

/**
 * Controller for a motor view.
 *
 * @author Brian Woodruff
 *
 */
public class MotorController extends PartController {
  private static Motor motor = new Motor();
  private Rocket rocket;
  
  @FXML
  private Label thrustFile;

  @FXML
  private TextField manufacturerValue;

  @FXML
  private TextField delaysValue;

  @FXML
  private TextField fuelMassValue;

  @FXML
  private TextField polarAngleValue;

  @FXML
  private TextField asimuthAngleValue;

  @FXML
  private TextField fuelMassError;

  @FXML
  private TextField polarAngleError;

  @FXML
  private TextField asimuthAngleError;

  @FXML
  private ChoiceBox<String> fuelMassUnits;

  @FXML
  private ChoiceBox<String> polarAngleUnits;

  @FXML
  private ChoiceBox<String> asimuthAngleUnits;

  @FXML
  private void loadThrustFile() {

  }

  /**
   *
   * @param rocket
   */
  public MotorController(Rocket rocket) {
    super(motor);
    this.rocket = rocket;
  }

  /**
   * Initialize the motor part and set listeners
   */
  public void initialize() {
    rocket.getInteriorComponents().add(motor);

    motor.setManufacturer("");
    motor.setDelays("");
    motor.setFuelMass(new Measurement(0, 0, Unit.grams));
    motor.setPolarAngle(new Measurement(0, 0, Unit.degrees));
    motor.setAzimuthAngle(new Measurement(0, 0, Unit.degrees));
    motor.setThrust(new DataTable());

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
    addValueListener(fuelMassValue, motor.getFuelMass());
    addValueListener(polarAngleError, motor.getPolarAngle());
    addValueListener(asimuthAngleValue, motor.getAzimuthAngle());

    addErrorListener(fuelMassError, motor.getFuelMass());
    addErrorListener(polarAngleError, motor.getPolarAngle());
    addErrorListener(asimuthAngleError, motor.getAzimuthAngle());

    addUnitListener(fuelMassUnits, motor.getFuelMass());
    addUnitListener(polarAngleUnits, motor.getPolarAngle());
    addUnitListener(asimuthAngleUnits, motor.getAzimuthAngle());
  }
}
