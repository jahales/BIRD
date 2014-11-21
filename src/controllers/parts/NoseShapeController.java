package controllers.parts;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import models.rocket.Rocket;
import models.rocket.parts.NoseShape;

;

/**
 * Controller for the nose shape view
 *
 * @author Brian Woodruff
 *
 */
public class NoseShapeController {

  private NoseShape noseShape;

  Rocket rocket;

  /**
   *
   * @param rocket
   */
  public NoseShapeController(Rocket rocket) {
    this.rocket = rocket;
  }

  @FXML
  private ChoiceBox<String> shape;

  /**
   * Get the noseshape part.
   *
   * @return noseShape
   */
  public NoseShape getNoseShape() {
    return noseShape;
  }

  /**
   * Updates the unit when user selects a unit.
   *
   * @param field
   * @param measurement
   */
  private void addUnitListener(ChoiceBox<String> field, NoseShape measurement) {
    field.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
        noseShape = NoseShape.valueOf(arg2);
      }
    });
  }

  /**
   * Set listener
   */
  public void initialize() {
    addUnitListener(shape, noseShape);
  }
}
