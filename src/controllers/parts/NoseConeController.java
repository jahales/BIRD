package controllers.parts;

import controllers.AppState;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.Measurement;
import models.Unit;
import models.rocket.parts.NoseCone;
import models.rocket.parts.NoseShape;

/**
 * Controller for the nosecone view.
 * 
 * @author Brian Woodruff
 *
 */
public class NoseConeController {
  private NoseCone noseCone = new NoseCone();

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
   * Initialize nosecone values and add listeners
   */
  public void initialize() {
    AppState.getInstance().getRocket().getExteriorComponents().add(noseCone);
    
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
