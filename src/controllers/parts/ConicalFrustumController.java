package controllers.parts;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.ModelState;
import models.Measurement;
import models.Unit;
import models.rocket.Rocket;
import models.rocket.parts.ConicalFrustum;

/**
 * A controller for a conical frustum part.
 *
 * @author Brian Woodruff
 *
 */
public class ConicalFrustumController {

  private ConicalFrustum conicalFrustum = new ConicalFrustum();

  Rocket rocket;

  public ConicalFrustumController(Rocket rocket) {
    this.rocket = rocket;
  }

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
   * Get the conical frustum
   *
   * @return conicalFrustum
   */
  public ConicalFrustum getConicalFrustum() {
    return conicalFrustum;
  }

  /**
   * Updates the unit when user selects a unit.
   *
   * @param field
   * @param measurement
   */
  private void addUnitListener(ChoiceBox<String> field, Measurement measurement) {
    field.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
        measurement.setUnit(Unit.valueOf(arg2));
      }
    });
  }

  /**
   * Updates the value when the user changes it.
   *
   * @param field
   * @param measurement
   */
  private void addValueListener(TextField field, Measurement measurement) {
    field.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
        try {
          measurement.setValue(Double.parseDouble(arg2));
        } catch (NumberFormatException e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Updates the error when the user changes it.
   *
   * @param field
   * @param measurement
   */
  private void addErrorListener(TextField field, Measurement measurement) {
    field.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
        try {
          measurement.setError(Double.parseDouble(arg2));
        } catch (NumberFormatException e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Initialize values for concial frustum and add listeners.
   */
  public void initialize() {
    rocket.getExteriorComponents().add(conicalFrustum);

    conicalFrustum.setLowerDiameter(new Measurement(0, 0, Unit.centimeters));
    conicalFrustum.setUpperDiameter(new Measurement(0, 0, Unit.centimeters));

    addValueListener(lowerDiameterValue, conicalFrustum.getLowerDiameter());
    addValueListener(upperDiameterValue, conicalFrustum.getUpperDiameter());

    addErrorListener(lowerDiameterError, conicalFrustum.getLowerDiameter());;
    addErrorListener(upperDiameterError, conicalFrustum.getUpperDiameter());

    addUnitListener(lowerDiameterUnits, conicalFrustum.getLowerDiameter());
    addUnitListener(upperDiameterUnits, conicalFrustum.getUpperDiameter());
  }
}
