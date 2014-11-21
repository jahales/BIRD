package controllers.parts;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.Measurement;
import models.Unit;
import models.rocket.Rocket;
import models.rocket.parts.NoseCone;
import models.rocket.parts.NoseShape;

/**
 * Controller for the nosecone view.
 *
 * @author Brian Woodruff
 *
 */
public class NoseConeController extends PartController {
  private NoseCone noseCone = new NoseCone();
  private Rocket rocket;

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
   *
   * @param rocket
   */
  public NoseConeController(Rocket rocket) {
    this.rocket = rocket;
  }

  /**
   * Initialize nosecone values and add listeners
   */
  public void initialize() {
    rocket.getExteriorComponents().add(noseCone);

    noseCone.setShapeParameter(0);
    noseCone.setDiameter(new Measurement(0, 0, Unit.centimeters));

    addUnitListener(diameterUnits, noseCone.getDiameter());
    shape.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
        noseCone.setNoseShape(NoseShape.valueOf(arg2));
      }
    });

    addValueListener(diameterValue, noseCone.getDiameter());
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

    addErrorListener(diameterError, noseCone.getDiameter());
  }
}
