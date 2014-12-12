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
    this.diameterValue.setText(Double.toString(this.circularCylinder.getDiameter().getValue()));
    this.diameterError.setText(Double.toString(this.circularCylinder.getDiameter().getError()));
    this.diameterUnits.setValue(this.circularCylinder.getDiameter().getUnit().toString());

    // Set listeners
    ListenerHelpers.addValueListener(this.diameterValue, this.circularCylinder.getDiameter());

    ListenerHelpers.addErrorListener(this.diameterError, this.circularCylinder.getDiameter());

    ListenerHelpers.addUnitListener(this.diameterUnits, this.circularCylinder.getDiameter());
  }
}
