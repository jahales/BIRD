package controllers.parts;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.rocket.parts.NoseCone;
import models.rocket.parts.NoseCone.NoseShape;
import controllers.BaseController;

/**
 * Controller for the {@link NoseCone} view
 *
 * @author Brian Woodruff
 *
 */
public class NoseConeController extends BaseController {

  private NoseCone noseCone;

  @FXML
  private TextField shapeParameter;

  @FXML
  private TextField diameterValue;

  @FXML
  private TextField diameterError;

  @FXML
  private ChoiceBox<String> diameterUnits;

  @FXML
  private ChoiceBox<String> shape;

  /**
   * @param rocket
   *          a rocket this view will modify
   */
  public NoseConeController(NoseCone noseCone) {
    this.noseCone = noseCone;
  }

  /**
   * Initialize values and add listeners
   */
  public void initialize() {
    // Populate fields with whatever values we got
    this.shapeParameter.setText(Double.toString(this.noseCone.getShapeParameter()));

    this.diameterValue.setText(Double.toString(this.noseCone.getDiameter().getValue()));
    this.diameterError.setText(Double.toString(this.noseCone.getDiameter().getError()));
    this.diameterUnits.setValue(this.noseCone.getDiameter().getUnit().toString());

    // shape.setValue(noseCone.getShapeParameterDescription()); // Not sure if
    // this is correct!

    // Set listeners
    ListenerHelpers.addUnitListener(this.diameterUnits, this.noseCone.getDiameter());
    this.shape
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (ChangeListener<String>) (arg0, arg1, arg2) -> NoseConeController.this.noseCone
                .setNoseShape(NoseShape.valueOf(arg2.toUpperCase())));

    ListenerHelpers.addValueListener(this.diameterValue, this.noseCone.getDiameter());
    this.shapeParameter.textProperty().addListener((ChangeListener<String>) (arg0, arg1, arg2) -> {
      try {
        NoseConeController.this.noseCone.setShapeParameter(Double.parseDouble(arg2));
      } catch (NumberFormatException e) {
        e.printStackTrace();
      }
    });

    ListenerHelpers.addErrorListener(this.diameterError, this.noseCone.getDiameter());
  }
}
