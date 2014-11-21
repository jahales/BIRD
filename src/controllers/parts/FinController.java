package controllers.parts;

import models.Measurement;
import models.Unit;
import models.rocket.parts.TrapezoidFinSet;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.rocket.Rocket;

/**
 * A controller for a fin part.
 *
 * @author Brian Woodruff
 *
 */
public class FinController {
  private TrapezoidFinSet trapezoidFinSet = new TrapezoidFinSet();
  private Rocket rocket;

  @FXML
  private TextField finCountValue;

  @FXML
  private TextField spanLengthValue;

  @FXML
  private TextField spanLengthError;

  @FXML
  private TextField sweepLengthValue;

  @FXML
  private TextField sweepLengthError;

  @FXML
  private TextField rootChordError;

  @FXML
  private TextField rootChordValue;

  @FXML
  private ChoiceBox<String> rootChordUnits;

  @FXML
  private ChoiceBox<String> spanLengthUnits;

  @FXML
  private ChoiceBox<String> sweepLengthUnits;

  /**
   *
   * @param rocket
   */
  public FinController(Rocket rocket) {
    this.rocket = rocket;
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
      public void changed(ObservableValue<? extends String> reserved, String old, String current) {
        measurement.setUnit(Unit.valueOf(current));
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
      public void changed(ObservableValue<? extends String> reserved, String old, String current) {
        try {
          measurement.setValue(Double.parseDouble(current));
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
      public void changed(ObservableValue<? extends String> reserved, String old, String current) {
        try {
          measurement.setError(Double.parseDouble(current));
        } catch (NumberFormatException e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Initialize the fin set and add listeners.
   */
  public void initialize() {
    rocket.getExteriorComponents().add(trapezoidFinSet);

    trapezoidFinSet.setCount(0);
    trapezoidFinSet.setSpanLength(new Measurement(0, 0, Unit.centimeters));
    trapezoidFinSet.setSweepLength(new Measurement(0, 0, Unit.centimeters));
    trapezoidFinSet.setRootChord(new Measurement(0, 0, Unit.centimeters));

    addValueListener(rootChordValue, trapezoidFinSet.getRootChord());
    addValueListener(spanLengthValue, trapezoidFinSet.getSpanLength());
    addValueListener(sweepLengthValue, trapezoidFinSet.getSweepLength());

    addErrorListener(rootChordError, trapezoidFinSet.getRootChord());
    addErrorListener(spanLengthError, trapezoidFinSet.getSpanLength());
    addErrorListener(sweepLengthError, trapezoidFinSet.getSweepLength());

    addUnitListener(rootChordUnits, trapezoidFinSet.getRootChord());
    addUnitListener(spanLengthUnits, trapezoidFinSet.getSpanLength());
    addUnitListener(sweepLengthUnits, trapezoidFinSet.getSweepLength());
  }
}
