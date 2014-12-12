package controllers.parts;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.rocket.parts.Parachute;
import controllers.BaseController;

/**
 * Controller for the {@link Parachute} view
 *
 * @author Brian Woodruff
 *
 */
public class ParachuteController extends BaseController {

  private Parachute parachute;

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
  private ChoiceBox<String> deployedDiameterUnits;

  @FXML
  private ChoiceBox<String> deployedAltitudeUnits;

  /**
   * @param rocket
   *          a rocket this view will modify
   */
  public ParachuteController(Parachute parachute) {
    this.parachute = parachute;
  }

  /**
   * Initialize values and add listeners
   */
  public void initialize() {
    // Populate fields with whatever values we got
    this.dragCoefficientValue.setText(Double.toString(this.parachute.getDragCoefficient()
        .getValue()));
    this.deployedDiameterValue.setText(Double.toString(this.parachute.getDeployedDiameter()
        .getValue()));
    this.deploymentAltitudeValue.setText(Double.toString(this.parachute.getDeploymentAltitude()
        .getValue()));

    this.dragCoefficientError.setText(Double.toString(this.parachute.getDragCoefficient()
        .getError()));
    this.deployedDiameterError.setText(Double.toString(this.parachute.getDeployedDiameter()
        .getError()));
    this.deploymentAltitudeError.setText(Double.toString(this.parachute.getDeploymentAltitude()
        .getError()));

    this.deployedDiameterUnits.setValue(this.parachute.getDeployedDiameter().getUnit().toString());
    this.deployedAltitudeUnits
        .setValue(this.parachute.getDeploymentAltitude().getUnit().toString());

    // parachute.getDeployAtApogee(); // Missing implementation

    // Set listeners
    ListenerHelpers.addValueListener(this.deployedDiameterValue,
        this.parachute.getDeployedDiameter());
    ListenerHelpers.addValueListener(this.deploymentAltitudeValue,
        this.parachute.getDeploymentAltitude());
    ListenerHelpers
        .addValueListener(this.dragCoefficientValue, this.parachute.getDragCoefficient());

    ListenerHelpers.addErrorListener(this.deployedDiameterError,
        this.parachute.getDeployedDiameter());
    ListenerHelpers.addErrorListener(this.deploymentAltitudeError,
        this.parachute.getDeploymentAltitude());
    ListenerHelpers
        .addErrorListener(this.dragCoefficientError, this.parachute.getDragCoefficient());

    ListenerHelpers.addUnitListener(this.deployedDiameterUnits,
        this.parachute.getDeployedDiameter());
    ListenerHelpers.addUnitListener(this.deployedAltitudeUnits,
        this.parachute.getDeploymentAltitude());
  }
}
