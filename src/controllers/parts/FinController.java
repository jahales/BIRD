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
    finCountValue.setText(Integer.toString(trapezoidFinSet.getCount()));

    spanLengthValue.setText(Double.toString(trapezoidFinSet.getSpanLength().getValue()));
    sweepLengthValue.setText(Double.toString(trapezoidFinSet.getSweepLength().getValue()));
    rootChordValue.setText(Double.toString(trapezoidFinSet.getRootChord().getValue()));

    spanLengthError.setText(Double.toString(trapezoidFinSet.getSpanLength().getError()));
    sweepLengthError.setText(Double.toString(trapezoidFinSet.getSweepLength().getError()));
    rootChordError.setText(Double.toString(trapezoidFinSet.getRootChord().getError()));

    spanLengthUnits.setValue(trapezoidFinSet.getSpanLength().getUnit().toString());
    sweepLengthUnits.setValue(trapezoidFinSet.getSweepLength().getUnit().toString());
    rootChordUnits.setValue(trapezoidFinSet.getRootChord().getUnit().toString());

    // Set listeners
    ListenerHelpers.addValueListener(rootChordValue, trapezoidFinSet.getRootChord());
    ListenerHelpers.addValueListener(spanLengthValue, trapezoidFinSet.getSpanLength());
    ListenerHelpers.addValueListener(sweepLengthValue, trapezoidFinSet.getSweepLength());

    ListenerHelpers.addErrorListener(rootChordError, trapezoidFinSet.getRootChord());
    ListenerHelpers.addErrorListener(spanLengthError, trapezoidFinSet.getSpanLength());
    ListenerHelpers.addErrorListener(sweepLengthError, trapezoidFinSet.getSweepLength());

    ListenerHelpers.addUnitListener(rootChordUnits, trapezoidFinSet.getRootChord());
    ListenerHelpers.addUnitListener(spanLengthUnits, trapezoidFinSet.getSpanLength());
    ListenerHelpers.addUnitListener(sweepLengthUnits, trapezoidFinSet.getSweepLength());
  }
}
