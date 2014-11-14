package controllers.parts;

import models.Measurement;
import models.Unit;
import models.rocket.parts.TrapezoidFinSet;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * A controller for a fin part.
 * 
 * @author Brian Woodruff
 *
 */
public class FinController {
  private TrapezoidFinSet fin = new TrapezoidFinSet();

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
   * Get the trapezoid fin set
   * 
   * @return fin
   */
  public TrapezoidFinSet getFin() {
    return fin;
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
   * Initialize the fin set and add listeners.
   */
  public void initialize() {
    fin.setCount(0);
    fin.setSpanLength(new Measurement(0, 0, Unit.centimeters));
    fin.setSweepLength(new Measurement(0, 0, Unit.centimeters));
    fin.setRootChord(new Measurement(0, 0, Unit.centimeters));
    
    finCountValue.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
        try {
          fin.setCount(Integer.parseInt(arg2));
        } catch (NumberFormatException e) {
          e.printStackTrace();
        }
      }
    });
    addValueListener(rootChordValue, fin.getRootChord());
    addValueListener(spanLengthValue, fin.getSpanLength());
    addValueListener(sweepLengthValue, fin.getSweepLength());
    
    addErrorListener(rootChordError, fin.getRootChord());
    addErrorListener(spanLengthError, fin.getSpanLength());
    addErrorListener(sweepLengthError, fin.getSweepLength());
    
    addUnitListener(rootChordUnits, fin.getRootChord());
    addUnitListener(spanLengthUnits, fin.getSpanLength());
    addUnitListener(sweepLengthUnits, fin.getSweepLength());
  }
}
