package controllers.parts;

import controllers.BaseController;
import models.rocket.parts.CircularCylinder;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * Controller for {@link CircularCylinder} view
 *
 * @author Brian Woodruff
 *
 */
public class CircularCylinderController extends BaseController {

  private CircularCylinder circularCylinder;

  @FXML
  private TextField diameterValue;

  @FXML
  private TextField diameterError;

  @FXML
  private ChoiceBox<String> diameterUnits;

  /**
   * @param rocket a rocket this view will modify
   */
  public CircularCylinderController(CircularCylinder circularCylinder) {
    this.circularCylinder = circularCylinder;
  }

  /**
   * Initialize values and add listeners
   */
  public void initialize() {
    ListenerHelpers.addValueListener(diameterValue, circularCylinder.getDiameter());

    ListenerHelpers.addErrorListener(diameterError, circularCylinder.getDiameter());

    ListenerHelpers.addUnitListener(diameterUnits, circularCylinder.getDiameter());
  }
}
