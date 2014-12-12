package controllers.parts;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.rocket.parts.ConicalFrustum;
import controllers.BaseController;

/**
 * Controller for a {@link ConicalFrustum} view
 *
 * @author Brian Woodruff
 *
 */
public class ConicalFrustumController extends BaseController {

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
   * @param rocket
   *          a rocket this view will modify
   */
  public ConicalFrustumController(ConicalFrustum conicalFrustum) {
    this.conicalFrustum = conicalFrustum;
  }

  /**
   * Initialize values and add listeners
   */
  public void initialize() {
    // Populate fields with whatever values we got
    this.upperDiameterValue.setText(Double.toString(this.conicalFrustum.getUpperDiameter()
        .getValue()));
    this.lowerDiameterValue.setText(Double.toString(this.conicalFrustum.getLowerDiameter()
        .getValue()));

    this.upperDiameterError.setText(Double.toString(this.conicalFrustum.getUpperDiameter()
        .getError()));
    this.lowerDiameterError.setText(Double.toString(this.conicalFrustum.getLowerDiameter()
        .getError()));

    this.upperDiameterUnits.setValue(this.conicalFrustum.getUpperDiameter().getUnit().toString());
    this.lowerDiameterUnits.setValue(this.conicalFrustum.getLowerDiameter().getUnit().toString());

    // Set listeners
    ListenerHelpers.addValueListener(this.lowerDiameterValue,
        this.conicalFrustum.getLowerDiameter());
    ListenerHelpers.addValueListener(this.upperDiameterValue,
        this.conicalFrustum.getUpperDiameter());

    ListenerHelpers.addErrorListener(this.lowerDiameterError,
        this.conicalFrustum.getLowerDiameter());
    ListenerHelpers.addErrorListener(this.upperDiameterError,
        this.conicalFrustum.getUpperDiameter());

    ListenerHelpers
        .addUnitListener(this.lowerDiameterUnits, this.conicalFrustum.getLowerDiameter());
    ListenerHelpers
        .addUnitListener(this.upperDiameterUnits, this.conicalFrustum.getUpperDiameter());
  }
}
