package controllers.parts;

import controllers.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.rocket.parts.ConicalFrustum;

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
   * @param rocket a rocket this view will modify
   */
  public ConicalFrustumController(ConicalFrustum conicalFrustum) {
    this.conicalFrustum = conicalFrustum;
  }

  /**
   * Initialize values and add listeners
   */
  public void initialize() {
    // Populate fields with whatever values we got
    upperDiameterValue.setText(Double.toString(conicalFrustum.getUpperDiameter().getValue()));
    lowerDiameterValue.setText(Double.toString(conicalFrustum.getLowerDiameter().getValue()));
    
    upperDiameterError.setText(Double.toString(conicalFrustum.getUpperDiameter().getError()));
    lowerDiameterError.setText(Double.toString(conicalFrustum.getLowerDiameter().getError()));
    
    upperDiameterUnits.setValue(conicalFrustum.getUpperDiameter().getUnit().toString());
    lowerDiameterUnits.setValue(conicalFrustum.getLowerDiameter().getUnit().toString());
    
    // Set listeners
    ListenerHelpers.addValueListener(lowerDiameterValue, conicalFrustum.getLowerDiameter());
    ListenerHelpers.addValueListener(upperDiameterValue, conicalFrustum.getUpperDiameter());

    ListenerHelpers.addErrorListener(lowerDiameterError, conicalFrustum.getLowerDiameter());
    ListenerHelpers.addErrorListener(upperDiameterError, conicalFrustum.getUpperDiameter());

    ListenerHelpers.addUnitListener(lowerDiameterUnits, conicalFrustum.getLowerDiameter());
    ListenerHelpers.addUnitListener(upperDiameterUnits, conicalFrustum.getUpperDiameter());
  }
}
