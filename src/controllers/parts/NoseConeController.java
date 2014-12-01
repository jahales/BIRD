package controllers.parts;

import controllers.BaseController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.Measurement;
import models.Unit;
import models.rocket.Rocket;
import models.rocket.parts.NoseCone;
import models.rocket.parts.NoseCone.NoseShape;

/**
 * Controller for the {@link NoseCone} view
 *
 * @author Brian Woodruff
 *
 */
public class NoseConeController extends BaseController {

  private NoseCone noseCone;

  @FXML
  private TextField shapeParameter;

  @FXML
  private TextField diameterValue;

  @FXML
  private TextField diameterError;

  @FXML
  private ChoiceBox<String> diameterUnits;

  @FXML
  private ChoiceBox<String> shape;

  /**
   * @param rocket a rocket this view will modify
   */
  public NoseConeController(NoseCone noseCone) {
    this.noseCone = noseCone;
  }

  /**
   * Initialize values and add listeners
   */
  public void initialize() {
    noseCone.setShapeParameter(0);
    noseCone.setDiameter(new Measurement(0, 0, Unit.centimeters));

    ListenerHelpers.addUnitListener(diameterUnits, noseCone.getDiameter());
    shape.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
        noseCone.setNoseShape(NoseShape.valueOf(arg2));
      }
    });

    ListenerHelpers.addValueListener(diameterValue, noseCone.getDiameter());
    shapeParameter.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
        try {
          noseCone.setShapeParameter(Double.parseDouble(arg2));
        } catch (NumberFormatException e) {
          e.printStackTrace();
        }
      }
    });

    ListenerHelpers.addErrorListener(diameterError, noseCone.getDiameter());
  }
}
