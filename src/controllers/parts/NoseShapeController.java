package controllers.parts;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import models.rocket.Rocket;
import models.rocket.parts.NoseShape;

/**
 * Controller for the {@link NoseShape} view
 *
 * @author Brian Woodruff
 *
 */
public class NoseShapeController extends PartController {
  private NoseShape noseShape;
  private Rocket rocket;

  @FXML
  private ChoiceBox<String> shape;

  /**
   * @param rocket
   *          a rocket this view will modify
   */
  public NoseShapeController(Rocket rocket) {
    super(null);
    this.rocket = rocket;
  }

  /**
   * Updates the unit when user selects a unit.
   *
   * @param field
   *          ChoiceBox to add listener to
   * @param measurement
   *          measurement to set
   */
  private void addUnitListener(ChoiceBox<String> field, NoseShape measurement) {
    field.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> reserved, String old, String current) {
        noseShape = NoseShape.valueOf(current);
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
