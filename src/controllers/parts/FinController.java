package controllers.parts;

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
public class FinController extends PartController {
  private static TrapezoidFinSet trapezoidFinSet = new TrapezoidFinSet();
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
   * @param rocket
   *          a rocket this view will modify
   */
  public FinController(Rocket rocket) {
    super(trapezoidFinSet);
    this.rocket = rocket;
  }

  /**
   * Initialize values and add listeners.
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
