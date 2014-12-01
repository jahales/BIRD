package controllers;

import static controllers.MainViewController.logger;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Level;

import models.MainViewModel;
import models.Measurement;
import models.Unit;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import models.AppSettings;
import models.rocket.Rocket;
import models.rocket.data.BirdRocketSerializer;
import models.rocket.data.IRocketSerializer;
import models.rocket.parts.Motor;
import models.rocket.parts.RocketComponent;
import models.rocket.parts.TrapezoidFinSet;
import models.simulator.Simulation;

/**
 * Controller for simulation tab view
 *
 * @author Brian Woodruff
 *
 */
public class SimulationController extends BaseController {

  private Measurement length;
  private Measurement azimuthAngle;
  private Measurement polarAngle;

  MainViewModel mainViewModel;

  /**
   *
   * @param mainViewModel
   */
  public SimulationController(MainViewModel mainViewModel) {
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
  private CheckBox motorChkBox;

  @FXML
  private CheckBox finChkBox;

  @FXML
  private Button finBtn;

  @FXML
  private Button motorBtn;

  @FXML
  void setDefaultRail() {
    AppSettings.getInstance().setLaunchRail(mainViewModel.getSimulation().getLaunchRail());
  }

  @FXML
  void motorChkBoxChange() {
    if (motorChkBox.isSelected()) {
      motorBtn.setDisable(true);
    } else {
      motorBtn.setDisable(false);
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

  @FXML
  void finChkBoxChange() {
    if (finChkBox.isSelected()) {
      finBtn.setDisable(true);
    } else {
      finBtn.setDisable(false);
    }
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
  void btnSimulation() {
    ArrayList<TrapezoidFinSet> finSets = new ArrayList<TrapezoidFinSet>();
    ArrayList<Motor> motors = new ArrayList<Motor>();
    ArrayList<RocketComponent> notFins = new ArrayList<RocketComponent>();
    ArrayList<RocketComponent> notMotors = new ArrayList<RocketComponent>();

    for (RocketComponent component : mainViewModel.getRocket().getExteriorComponents()) {
      if (component.getClass().isInstance(new TrapezoidFinSet())) {
        finSets.add((TrapezoidFinSet) component);
      } else {
        notFins.add(component);
      }
    }
    for (RocketComponent component : mainViewModel.getRocket().getInteriorComponents()) {
      if (component.getClass().isInstance(new Motor())) {
        motors.add((Motor) component);
      } else {
        notMotors.add(component);
      }
    }
    if (motors.size() == 0) {
      //report an error to the user
      return;
    }
    if (finSets.size() == 0) {
      //report an error to the user
      return;
    }
    if (mainViewModel.getSimulation().getAtmosphereFile() == null) {
      //report an error to the user
      return;
    }

    if (finChkBox.isSelected()) {
      for (TrapezoidFinSet finSet : finSets) {
        motorsLoop(finSet, motors, notFins, notMotors);
      }
    } else {
      //get the user selected fin
    }
  }

  private void motorsLoop(TrapezoidFinSet finSet, ArrayList<Motor> motors, 
    ArrayList<RocketComponent> notFins, ArrayList<RocketComponent> notMotors) {
    
    if (motorChkBox.isSelected()) {
      for (Motor motor : motors) {
        File tempRocketPath = createTemporaryRocketFile(finSet, motor, notFins, notMotors);
        File simulationFile = createSimulationFile(tempRocketPath, motor, mainViewModel.getSimulation().getAtmosphereFile());
        //runSimulation(simulationFile); Who runs the simulation? Controller or model?
      }
    } else {
      //get the user selected motor
    }
  }

  private File createSimulationFile(File tempRocketFile, Motor motor, String atmosphereFile) {
    Simulation simulation = new Simulation();
    simulation.setAtmosphereFile(atmosphereFile);
    //simulation.setEngineFile(motor.getENG());
    simulation.setLaunchRail(mainViewModel.getSimulation().getLaunchRail());
    simulation.setRocketFile(tempRocketFile.getAbsolutePath());
    //simulation.createSimulationFile(); Need to create a 
    //simulation.run(); Who runs the simulation? Controller or model?
    return new File("");
  }

  private File createTemporaryRocketFile(TrapezoidFinSet finSet, Motor motor,
    ArrayList<RocketComponent> exteriors, ArrayList<RocketComponent> interiors) {

    Rocket rocket = new Rocket();
    exteriors.add(finSet);
    interiors.add(motor);
    rocket.setExteriorComponents(exteriors);
    rocket.setInteriorComponents(interiors);
    // TODO: set other aspects of the rocket

    File rocketFile = new File(mainViewModel.getPresentWorkingDirectory(), "tempRocket.xml");
    try {
      OutputStream outStream = new FileOutputStream(rocketFile);
      IRocketSerializer serializer = new BirdRocketSerializer();
      serializer.serialize(rocket, outStream);
    } catch (Exception ex) {
      logger.log(Level.WARNING, null, ex);
    }

    return rocketFile;
  }

  @FXML
  void btnMonteCarlo() {

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
