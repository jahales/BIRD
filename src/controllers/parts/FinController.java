package controllers.parts;

import controllers.BaseController;
import models.Measurement;
import models.Unit;
import models.rocket.parts.TrapezoidFinSet;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.rocket.Rocket;

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
   * @param rocket a rocket this view will modify
   */
  public FinController(TrapezoidFinSet trapezoidFinSet) {
    this.trapezoidFinSet = trapezoidFinSet;
  }

  /**
   * Initialize values and add listeners.
   */
  public void initialize() {
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
