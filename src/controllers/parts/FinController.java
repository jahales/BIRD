package controllers.parts;

import models.Measurement;
import models.Unit;
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
  private Measurement finCount = new Measurement(0.0, 0.0, Unit.milliMeter);
  private Measurement spanLength;
  private Measurement sweepLength;
  private Measurement rootChord;

  public Measurement getFinCount() {
    return finCount;
  }

  public Measurement getSpanLength() {
    return spanLength;
  }

  public Measurement getRootChord() {
    return rootChord;
  }

  public Measurement getSweepLength() {
    return sweepLength;
  }

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

  public void initialize() {
    rootChordUnits.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
          @Override
          public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
            System.out.println("You selected: " + arg2);
          }
        });
  }
}
