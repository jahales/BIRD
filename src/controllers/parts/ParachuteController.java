package controllers.parts;

import controllers.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.rocket.parts.Parachute;

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
   * @param rocket a rocket this view will modify
   */
  public ParachuteController(Parachute parachute) {
    this.parachute = parachute;
  }

  /**
   * Initialize values and add listeners
   */
  public void initialize() {
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
