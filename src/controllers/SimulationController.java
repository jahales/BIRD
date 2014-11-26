package controllers;

import static controllers.MainViewController.logger;
import java.io.File;

import models.MainViewModel;
import models.Measurement;
import models.Unit;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import models.AppSettings;

/**
 * Controller for simulation tab view
 * 
 * @author Brian Woodruff
 *
 */
public class SimulationController {
  private Measurement length;
  private Measurement azimuthAngle;
  private Measurement polarAngle;
  
  MainViewModel mainViewModel;
        
  /**
   *
   * @param mainViewModel
   */
  public SimulationController(MainViewModel mainViewModel)
    {
      this.mainViewModel = mainViewModel;
    }

  @FXML
  private Label rocketFilePath;

  @FXML
  private Label atmosphereFilePath;
  
  @FXML
  private Label engineFilePath;

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
  void btnChooseFin() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Rocket File");
    File file = fileChooser.showOpenDialog(rocketFilePath.getScene().getWindow());
    if (file != null) {
      rocketFilePath.setText(file.getName());
      mainViewModel.getSimulation().setRocketFile(file.getAbsolutePath());
    }
  }

  @FXML
  void openAtmosphereFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Atmosphere File");
    configInitialDirectory(fileChooser);
    File file = fileChooser.showOpenDialog(atmosphereFilePath.getScene().getWindow());
    if (file != null) {
      atmosphereFilePath.setText(file.getName());
      mainViewModel.getSimulation().setAtmosphereFile(file.getAbsolutePath());
    }
  }

  @FXML
  void btnChooseMotor() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Engine File");
    File file = fileChooser.showOpenDialog(engineFilePath.getScene().getWindow());
    if (file != null) {
      engineFilePath.setText(file.getName());
      mainViewModel.getSimulation().setEngineFile(file.getAbsolutePath());
    }
  }

  private void configInitialDirectory(FileChooser fileChooser) {
    if (mainViewModel.getPresentWorkingDirectory() == null) {
      //If no present working directory, use default
      fileChooser.setInitialDirectory(
        new File(AppSettings.getInstance().getDefaultRocketPath()));
    } else {
      //If there's a present working directory, open up to that directory
      fileChooser.setInitialDirectory(mainViewModel.getPresentWorkingDirectory());
    }
    //Make sure the initial directory is a directory    
    if (!fileChooser.getInitialDirectory().isDirectory()) {
      logger.warning("Invalid initial directory path");
      fileChooser.setInitialDirectory(null);
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
  
  /**
   *
   */
  public void initialize() {
    length = mainViewModel.getSimulation().getLaunchRail().getLength();
    azimuthAngle = mainViewModel.getSimulation().getLaunchRail().getAzimuthAngle();
    polarAngle = mainViewModel.getSimulation().getLaunchRail().getPolarAngle();
    
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
