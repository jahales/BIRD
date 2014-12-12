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
    this.name.setText(this.component.getName());

    this.massValue.setText(Double.toString(this.component.getMass().getValue()));
    this.axialLengthValue.setText(Double.toString(this.component.getAxialLength().getValue()));
    this.axialOffsetValue.setText(Double.toString(this.component.getAxialOffset().getValue()));
    this.radialOffsetValue.setText(Double.toString(this.component.getRadialOffset().getValue()));
    this.thicknessValue.setText(Double.toString(this.component.getThickness().getValue()));

    this.massError.setText(Double.toString(this.component.getMass().getError()));
    this.axialLengthError.setText(Double.toString(this.component.getAxialLength().getError()));
    this.axialOffsetError.setText(Double.toString(this.component.getAxialOffset().getError()));
    this.radialOffsetError.setText(Double.toString(this.component.getRadialOffset().getError()));
    this.thicknessError.setText(Double.toString(this.component.getThickness().getError()));

    this.massUnits.setValue(this.component.getMass().getUnit().toString());
    this.axialLengthUnits.setValue(this.component.getAxialLength().getUnit().toString());
    this.axialOffsetUnits.setValue(this.component.getAxialOffset().getUnit().toString());
    this.radialOffsetUnits.setValue(this.component.getRadialOffset().getUnit().toString());
    this.thicknessUnits.setValue(this.component.getThickness().getUnit().toString());

    // Set listeners
    this.name.textProperty().addListener(
        (ChangeListener<String>) (reserved, old, current) -> ComponentController.this.component
            .setName(current));
    ListenerHelpers.addValueListener(this.massValue, this.component.getMass());
    ListenerHelpers.addValueListener(this.axialLengthValue, this.component.getAxialLength());
    ListenerHelpers.addValueListener(this.axialOffsetValue, this.component.getAxialOffset());
    ListenerHelpers.addValueListener(this.radialOffsetValue, this.component.getRadialOffset());
    ListenerHelpers.addValueListener(this.thicknessValue, this.component.getThickness());

    ListenerHelpers.addErrorListener(this.massError, this.component.getMass());
    ListenerHelpers.addErrorListener(this.axialLengthError, this.component.getAxialLength());
    ListenerHelpers.addErrorListener(this.axialOffsetError, this.component.getAxialOffset());
    ListenerHelpers.addErrorListener(this.radialOffsetError, this.component.getRadialOffset());
    ListenerHelpers.addErrorListener(this.thicknessError, this.component.getThickness());

    ListenerHelpers.addUnitListener(this.massUnits, this.component.getMass());
    ListenerHelpers.addUnitListener(this.axialLengthUnits, this.component.getAxialLength());
    ListenerHelpers.addUnitListener(this.axialOffsetUnits, this.component.getAxialOffset());
    ListenerHelpers.addUnitListener(this.radialOffsetUnits, this.component.getRadialOffset());
    ListenerHelpers.addUnitListener(this.thicknessUnits, this.component.getThickness());
  }
}
