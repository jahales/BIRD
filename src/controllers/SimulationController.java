package controllers;

import java.io.File;

import models.Measurement;
import models.Unit;
import models.simulator.Simulation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 * Controller for simulation tab view
 * 
 * @author Brian Woodruff
 *
 */
public class SimulationController {
  private Simulation simulation = new Simulation();
  private Measurement length = new Measurement(0, 0, Unit.centimeters);
  private Measurement azimuthAngle = new Measurement(0, 0, Unit.degrees);
  private Measurement polarAngle = new Measurement(0, 0, Unit.degrees);

  @FXML
  private Label rocketFilePath;

  @FXML
  private Label atmosphereFilePath;
  
  @FXML
  private Label engineFilePath;

  @FXML
  private Label launchRailFilePath;

  @FXML
  private TextField lengthValue;

  @FXML
  private TextField polarAngleValue;

  @FXML
  private TextField azimuthAngleValue;

  @FXML
  private TextField lengthError;

  @FXML
  private TextField polarAngleError;

  @FXML
  private TextField azimuthAngleError;

  @FXML
  private ChoiceBox<String> lengthUnits;

  @FXML
  private ChoiceBox<String> polarAngleUnits;

  @FXML
  private ChoiceBox<String> azimuthAngleUnits;

  @FXML
  void runSimulation() {
    
  }

  @FXML
  void openRocketFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Rocket File");
    File file = fileChooser.showOpenDialog(rocketFilePath.getScene().getWindow());
    if (file != null) {
      rocketFilePath.setText(file.getName());
      simulation.setRocketFile(file.getAbsolutePath());
    }
  }

  @FXML
  void openAtmosphereFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Atmosphere File");
    File file = fileChooser.showOpenDialog(atmosphereFilePath.getScene().getWindow());
    if (file != null) {
      atmosphereFilePath.setText(file.getName());
      simulation.setAtmosphereFile(file.getAbsolutePath());
    }
  }

  @FXML
  void openEngineFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Engine File");
    File file = fileChooser.showOpenDialog(engineFilePath.getScene().getWindow());
    if (file != null) {
      engineFilePath.setText(file.getName());
      simulation.setEngineFile(file.getAbsolutePath());
    }
  }

  @FXML
  void openLaunchRail() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Launch Rail File");
    File file = fileChooser.showOpenDialog(launchRailFilePath.getScene().getWindow());
    if (file != null) {
      launchRailFilePath.setText(file.getName());
      simulation.setLaunchRailFile(file.getAbsolutePath());
    }
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

  public void initialize() {
    addValueListener(lengthValue, length);
    addValueListener(polarAngleValue, polarAngle);
    addValueListener(azimuthAngleValue, azimuthAngle);
    
    addErrorListener(lengthError, length);
    addErrorListener(polarAngleError, polarAngle);
    addErrorListener(azimuthAngleError, azimuthAngle);
    
    addUnitListener(lengthUnits, length);
    addUnitListener(polarAngleUnits, polarAngle);
    addUnitListener(azimuthAngleUnits, azimuthAngle);
  }
}
