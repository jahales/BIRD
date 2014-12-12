package controllers.parts;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.rocket.parts.CircularCylinder;
import controllers.BaseController;

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
   * @param rocket
   *          a rocket this view will modify
   */
  public CircularCylinderController(CircularCylinder circularCylinder) {
    this.circularCylinder = circularCylinder;
  }

  /**
   * Initialize values and add listeners
   */
  public void initialize() {
    // Populate fields with whatever values we got
    diameterValue.setText(Double.toString(circularCylinder.getDiameter().getValue()));
    diameterError.setText(Double.toString(circularCylinder.getDiameter().getError()));
    diameterUnits.setValue(circularCylinder.getDiameter().getUnit().toString());

    // Set listeners
    ListenerHelpers.addValueListener(diameterValue, circularCylinder.getDiameter());

    ListenerHelpers.addErrorListener(diameterError, circularCylinder.getDiameter());

    ListenerHelpers.addUnitListener(diameterUnits, circularCylinder.getDiameter());
  }
}
