package controllers.parts;

import models.Measurement;
import models.Unit;
import models.rocket.parts.CircularCylinder;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.rocket.Rocket;

/**
 * Controller for {@link CircularCylinder} view
 *
 * @author Brian Woodruff
 *
 */
public class CircularCylinderController extends PartController {
  private static CircularCylinder circularCylinder = new CircularCylinder();
  private Rocket rocket;

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
  public CircularCylinderController(Rocket rocket) {
    super(circularCylinder);
    this.rocket = rocket;
  }

  /**
   * Initialize values and add listeners
   */
  public void initialize() {
    rocket.getInteriorComponents().add(circularCylinder);

    circularCylinder.setDiameter(new Measurement(0, 0, Unit.centimeters));

    addValueListener(diameterValue, circularCylinder.getDiameter());

    addErrorListener(diameterError, circularCylinder.getDiameter());

    addUnitListener(diameterUnits, circularCylinder.getDiameter());
  }
}
