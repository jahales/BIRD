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
    dragCoefficientValue.setText(Double.toString(parachute.getDragCoefficient().getValue()));
    deployedDiameterValue.setText(Double.toString(parachute.getDeployedDiameter().getValue()));
    deploymentAltitudeValue.setText(Double.toString(parachute.getDeploymentAltitude().getValue()));

    dragCoefficientError.setText(Double.toString(parachute.getDragCoefficient().getError()));
    deployedDiameterError.setText(Double.toString(parachute.getDeployedDiameter().getError()));
    deploymentAltitudeError.setText(Double.toString(parachute.getDeploymentAltitude().getError()));

    deployedDiameterUnits.setValue(parachute.getDeployedDiameter().getUnit().toString());
    deployedAltitudeUnits.setValue(parachute.getDeploymentAltitude().getUnit().toString());

    // parachute.getDeployAtApogee(); // Missing implementation

    // Set listeners
    ListenerHelpers.addValueListener(deployedDiameterValue, parachute.getDeployedDiameter());
    ListenerHelpers.addValueListener(deploymentAltitudeValue, parachute.getDeploymentAltitude());
    ListenerHelpers.addValueListener(dragCoefficientValue, parachute.getDragCoefficient());

    ListenerHelpers.addErrorListener(deployedDiameterError, parachute.getDeployedDiameter());
    ListenerHelpers.addErrorListener(deploymentAltitudeError, parachute.getDeploymentAltitude());
    ListenerHelpers.addErrorListener(dragCoefficientError, parachute.getDragCoefficient());

    ListenerHelpers.addUnitListener(deployedDiameterUnits, parachute.getDeployedDiameter());
    ListenerHelpers.addUnitListener(deployedAltitudeUnits, parachute.getDeploymentAltitude());
  }
}
