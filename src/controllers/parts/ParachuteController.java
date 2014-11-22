package controllers.parts;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.Measurement;
import models.Unit;
import models.rocket.Rocket;
import models.rocket.parts.Parachute;

/**
 * Controller for the {@link Parachute} view
 *
 * @author Brian Woodruff
 *
 */
public class ParachuteController extends PartController {
  private static Parachute parachute = new Parachute();
  private Rocket rocket;

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
  public ParachuteController(Rocket rocket) {
    super(parachute);
    this.rocket = rocket;
  }
  
  /**
   * Initialize values and add listeners
   */
  public void initialize() {
    rocket.getInteriorComponents().add(parachute);

    parachute.setDragCoefficient(new Measurement(0, 0, Unit.number));
    parachute.setDeployedDiameter(new Measurement(0, 0, Unit.centimeters));
    parachute.setDeploymentAltitude(new Measurement(0, 0, Unit.meters));

    addValueListener(deployedDiameterValue, parachute.getDeployedDiameter());
    addValueListener(deploymentAltitudeValue, parachute.getDeploymentAltitude());
    addValueListener(dragCoefficientValue, parachute.getDragCoefficient());

    addErrorListener(deployedDiameterError, parachute.getDeployedDiameter());
    addErrorListener(deploymentAltitudeError, parachute.getDeploymentAltitude());
    addErrorListener(dragCoefficientError, parachute.getDragCoefficient());

    addUnitListener(deployedDiameterUnits, parachute.getDeployedDiameter());
    addUnitListener(deployedAltitudeUnits, parachute.getDeploymentAltitude());
  }
}
