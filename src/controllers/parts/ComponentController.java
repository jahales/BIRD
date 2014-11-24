package controllers.parts;

import controllers.RocketCreationController.RocketPart;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.Measurement;
import models.Unit;
import models.rocket.parts.RocketComponent;

/**
 * Abstract controller for a {@link RocketPart}
 *
 * @author Brian Woodruff
 *
 */
public class ComponentController {

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
   * @param component a {@link RocketComponent} this controller will modify
   */
  public ComponentController(RocketComponent component) {
    this.component = component;
  }

  public void initialize() {
    name.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> reserved, String old, String current) {
        component.setName(current);
      }
    });
    ListenerHelpers.addValueListener(massValue, component.getMass());
    ListenerHelpers.addValueListener(axialLengthValue, component.getAxialLength());
    ListenerHelpers.addValueListener(axialOffsetValue, component.getAxialOffset());
    ListenerHelpers.addValueListener(radialOffsetValue, component.getRadialOffset());
    ListenerHelpers.addValueListener(thicknessValue, component.getThickness());

    ListenerHelpers.addErrorListener(massValue, component.getMass());
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
