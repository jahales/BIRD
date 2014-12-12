package controllers.parts;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.rocket.parts.RocketComponent;
import controllers.BaseController;
import controllers.RocketCreationController.RocketPart;

/**
 * Abstract controller for a {@link RocketPart}
 *
 * @author Brian Woodruff
 *
 */
public class ComponentController extends BaseController {

  RocketComponent component;

  @FXML
  private TextField name;

  @FXML
  private TextField massValue;

  @FXML
  private TextField axialLengthValue;

  @FXML
  private TextField axialOffsetValue;

  @FXML
  private TextField radialOffsetValue;

  @FXML
  private TextField thicknessValue;

  @FXML
  private TextField massError;

  @FXML
  private TextField axialLengthError;

  @FXML
  private TextField axialOffsetError;

  @FXML
  private TextField radialOffsetError;

  @FXML
  private TextField thicknessError;

  @FXML
  private ChoiceBox<String> massUnits;

  @FXML
  private ChoiceBox<String> axialLengthUnits;

  @FXML
  private ChoiceBox<String> axialOffsetUnits;

  @FXML
  private ChoiceBox<String> radialOffsetUnits;

  @FXML
  private ChoiceBox<String> thicknessUnits;

  /**
   * Adds listeners to all common elements of a {@link RocketComponent}.
   *
   * @param component
   *          a {@link RocketComponent} this controller will modify
   */
  public ComponentController(RocketComponent component) {
    this.component = component;
  }

  public void initialize() {
    // Populate fields with whatever values we got
    name.setText(component.getName());

    massValue.setText(Double.toString(component.getMass().getValue()));
    axialLengthValue.setText(Double.toString(component.getAxialLength().getValue()));
    axialOffsetValue.setText(Double.toString(component.getAxialOffset().getValue()));
    radialOffsetValue.setText(Double.toString(component.getRadialOffset().getValue()));
    thicknessValue.setText(Double.toString(component.getThickness().getValue()));

    massError.setText(Double.toString(component.getMass().getError()));
    axialLengthError.setText(Double.toString(component.getAxialLength().getError()));
    axialOffsetError.setText(Double.toString(component.getAxialOffset().getError()));
    radialOffsetError.setText(Double.toString(component.getRadialOffset().getError()));
    thicknessError.setText(Double.toString(component.getThickness().getError()));

    massUnits.setValue(component.getMass().getUnit().toString());
    axialLengthUnits.setValue(component.getAxialLength().getUnit().toString());
    axialOffsetUnits.setValue(component.getAxialOffset().getUnit().toString());
    radialOffsetUnits.setValue(component.getRadialOffset().getUnit().toString());
    thicknessUnits.setValue(component.getThickness().getUnit().toString());

    // Set listeners
    name.textProperty().addListener(
        (ChangeListener<String>) (reserved, old, current) -> ComponentController.this.component
        .setName(current));
    ListenerHelpers.addValueListener(massValue, component.getMass());
    ListenerHelpers.addValueListener(axialLengthValue, component.getAxialLength());
    ListenerHelpers.addValueListener(axialOffsetValue, component.getAxialOffset());
    ListenerHelpers.addValueListener(radialOffsetValue, component.getRadialOffset());
    ListenerHelpers.addValueListener(thicknessValue, component.getThickness());

    ListenerHelpers.addErrorListener(massError, component.getMass());
    ListenerHelpers.addErrorListener(axialLengthError, component.getAxialLength());
    ListenerHelpers.addErrorListener(axialOffsetError, component.getAxialOffset());
    ListenerHelpers.addErrorListener(radialOffsetError, component.getRadialOffset());
    ListenerHelpers.addErrorListener(thicknessError, component.getThickness());

    ListenerHelpers.addUnitListener(massUnits, component.getMass());
    ListenerHelpers.addUnitListener(axialLengthUnits, component.getAxialLength());
    ListenerHelpers.addUnitListener(axialOffsetUnits, component.getAxialOffset());
    ListenerHelpers.addUnitListener(radialOffsetUnits, component.getRadialOffset());
    ListenerHelpers.addUnitListener(thicknessUnits, component.getThickness());
  }
}
