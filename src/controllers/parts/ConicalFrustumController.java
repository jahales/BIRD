package controllers.parts;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.Measurement;
import models.Unit;
import models.rocket.Rocket;
import models.rocket.parts.ConicalFrustum;

/**
 * A controller for a conical frustum part.
 *
 * @author Brian Woodruff
 *
 */
public class ConicalFrustumController extends PartController {
  private ConicalFrustum conicalFrustum = new ConicalFrustum();
  private Rocket rocket;

  @FXML
  private TextField upperDiameterValue;

  @FXML
  private TextField lowerDiameterValue;

  @FXML
  private TextField upperDiameterError;

  @FXML
  private TextField lowerDiameterError;

  @FXML
  private ChoiceBox<String> upperDiameterUnits;

  @FXML
  private ChoiceBox<String> lowerDiameterUnits;

  /**
   *
   * @param rocket
   */
  public ConicalFrustumController(Rocket rocket) {
    this.rocket = rocket;
  }

  /**
   * Initialize values for concial frustum and add listeners.
   */
  public void initialize() {
    rocket.getExteriorComponents().add(conicalFrustum);

    conicalFrustum.setLowerDiameter(new Measurement(0, 0, Unit.centimeters));
    conicalFrustum.setUpperDiameter(new Measurement(0, 0, Unit.centimeters));

    addValueListener(lowerDiameterValue, conicalFrustum.getLowerDiameter());
    addValueListener(upperDiameterValue, conicalFrustum.getUpperDiameter());

    addErrorListener(lowerDiameterError, conicalFrustum.getLowerDiameter());;
    addErrorListener(upperDiameterError, conicalFrustum.getUpperDiameter());

    addUnitListener(lowerDiameterUnits, conicalFrustum.getLowerDiameter());
    addUnitListener(upperDiameterUnits, conicalFrustum.getUpperDiameter());
  }
}
