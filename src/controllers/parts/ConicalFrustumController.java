package controllers.parts;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.Measurement;
import models.Unit;
import models.rocket.Rocket;
import models.rocket.parts.ConicalFrustum;

/**
 * Controller for a {@link ConicalFrustum} view
 *
 * @author Brian Woodruff
 *
 */
public class ConicalFrustumController {

  private ConicalFrustum conicalFrustum;

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
   * @param rocket a rocket this view will modify
   */
  public ConicalFrustumController(ConicalFrustum conicalFrustum) {
    this.conicalFrustum = conicalFrustum;
  }

  /**
   * Initialize values and add listeners
   */
  public void initialize() {
    ListenerHelpers.addValueListener(lowerDiameterValue, conicalFrustum.getLowerDiameter());
    ListenerHelpers.addValueListener(upperDiameterValue, conicalFrustum.getUpperDiameter());

    ListenerHelpers.addErrorListener(lowerDiameterError, conicalFrustum.getLowerDiameter());
    ListenerHelpers.addErrorListener(upperDiameterError, conicalFrustum.getUpperDiameter());

    ListenerHelpers.addUnitListener(lowerDiameterUnits, conicalFrustum.getLowerDiameter());
    ListenerHelpers.addUnitListener(upperDiameterUnits, conicalFrustum.getUpperDiameter());
  }
}
