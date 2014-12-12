package controllers.parts;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.rocket.parts.TrapezoidFinSet;
import controllers.BaseController;

/**
 * Controller for {@link TrapezoidFinSet} view
 *
 * @author Brian Woodruff
 *
 */
public class FinController extends BaseController {

  private TrapezoidFinSet trapezoidFinSet;

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
   * @param rocket
   *          a rocket this view will modify
   */
  public FinController(TrapezoidFinSet trapezoidFinSet) {
    this.trapezoidFinSet = trapezoidFinSet;
  }

  /**
   * Initialize values and add listeners.
   */
  public void initialize() {
    // Populate fields with whatever values we got
    this.finCountValue.setText(Integer.toString(this.trapezoidFinSet.getCount()));

    this.spanLengthValue.setText(Double.toString(this.trapezoidFinSet.getSpanLength().getValue()));
    this.sweepLengthValue
        .setText(Double.toString(this.trapezoidFinSet.getSweepLength().getValue()));
    this.rootChordValue.setText(Double.toString(this.trapezoidFinSet.getRootChord().getValue()));

    this.spanLengthError.setText(Double.toString(this.trapezoidFinSet.getSpanLength().getError()));
    this.sweepLengthError
        .setText(Double.toString(this.trapezoidFinSet.getSweepLength().getError()));
    this.rootChordError.setText(Double.toString(this.trapezoidFinSet.getRootChord().getError()));

    this.spanLengthUnits.setValue(this.trapezoidFinSet.getSpanLength().getUnit().toString());
    this.sweepLengthUnits.setValue(this.trapezoidFinSet.getSweepLength().getUnit().toString());
    this.rootChordUnits.setValue(this.trapezoidFinSet.getRootChord().getUnit().toString());

    // Set listeners
    ListenerHelpers.addValueListener(this.rootChordValue, this.trapezoidFinSet.getRootChord());
    ListenerHelpers.addValueListener(this.spanLengthValue, this.trapezoidFinSet.getSpanLength());
    ListenerHelpers.addValueListener(this.sweepLengthValue, this.trapezoidFinSet.getSweepLength());

    ListenerHelpers.addErrorListener(this.rootChordError, this.trapezoidFinSet.getRootChord());
    ListenerHelpers.addErrorListener(this.spanLengthError, this.trapezoidFinSet.getSpanLength());
    ListenerHelpers.addErrorListener(this.sweepLengthError, this.trapezoidFinSet.getSweepLength());

    ListenerHelpers.addUnitListener(this.rootChordUnits, this.trapezoidFinSet.getRootChord());
    ListenerHelpers.addUnitListener(this.spanLengthUnits, this.trapezoidFinSet.getSpanLength());
    ListenerHelpers.addUnitListener(this.sweepLengthUnits, this.trapezoidFinSet.getSweepLength());
  }
}
