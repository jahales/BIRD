package controllers;

import static controllers.MainViewController.logger;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import models.MainViewModel;
import models.Measurement;
import models.Unit;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.FileChooser;
import models.AppSettings;
import models.ISerializer;
import models.rocket.Rocket;
import models.rocket.data.XmlRocketSerializer;
import models.rocket.parts.Motor;
import models.rocket.parts.RocketComponent;
import models.rocket.parts.TrapezoidFinSet;
import models.simulator.BirdSimulatorEngine;
import models.simulator.ISimulationEngine;
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
  private ChoiceBox<String> finChcBox;

  @FXML
  private ChoiceBox<String> motorChcBox;

  @FXML
  void setDefaultRail() {
    AppSettings.getInstance().setLaunchRail(mainViewModel.getSimulation().getLaunchRail());
  }

  @FXML
  void motorChkBoxChange() {
    if (motorChkBox.isSelected()) {
      motorChcBox.setDisable(true);
    } else {
      motorChcBox.setDisable(false);
    }
  }

  @FXML
  void clkMotorChcBox() {
    ArrayList<String> options = new ArrayList<>();
    for (RocketComponent component : mainViewModel.getRocket().getInteriorComponents()) {
      if (component.getClass().isInstance(new Motor())) {
        options.add(((Motor) component).getName());
      }
    }
    if (options.size() == 0) {
      options.add("No motors are defined");
    }
    motorChcBox.setItems(FXCollections.observableArrayList(options));
    motorChcBox.hide();
    motorChcBox.show();
  }

  @FXML
  void finChkBoxChange() {
    if (finChkBox.isSelected()) {
      finChcBox.setDisable(true);
    } else {
      finChcBox.setDisable(false);
    }
  }

  @FXML
  void clkFinChcBox() {
    ArrayList<String> options = new ArrayList<>();
    for (RocketComponent component : mainViewModel.getRocket().getExteriorComponents()) {
      if (component.getClass().isInstance(new TrapezoidFinSet())) {
        options.add(((TrapezoidFinSet) component).getName());
      }
    }
    if (options.size() == 0) {
      options.add("No fin sets are defined");
    }
    finChcBox.setItems(FXCollections.observableArrayList(options));
    finChcBox.hide();
    finChcBox.show();
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
    MessageBoxController.showMessage("Your rocket does not have any motors!");
    ArrayList<TrapezoidFinSet> finSets = new ArrayList<>();
    ArrayList<Motor> motors = new ArrayList<>();
    ArrayList<RocketComponent> notFins = new ArrayList<>();
    ArrayList<RocketComponent> notMotors = new ArrayList<>();

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
      MessageBoxController.showMessage("Your rocket does not have any motors!");
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
    //TODO: Check for nose cone and at least 1 exterior cylinder

    if (finChkBox.isSelected() && motorChkBox.isSelected()) {
      for (TrapezoidFinSet finSet : finSets) {
        for (Motor motor : motors) {
          runSimulation(finSet, motor, notMotors, notFins);
        }
      }

    } else if (finChkBox.isSelected() && !motorChkBox.isSelected()) {
      Motor motor = (Motor) mainViewModel.getRocket().getPartByName(motorChcBox.getValue());
      if (motor == null) {
        //TODO: inform user of error
        return;
      }
      for (TrapezoidFinSet finSet : finSets) {
        runSimulation(finSet, motor, notMotors, notFins);
      }

    } else if (!finChkBox.isSelected() && motorChkBox.isSelected()) {

      TrapezoidFinSet finSet = (TrapezoidFinSet) mainViewModel.getRocket().getPartByName(finChcBox.getValue());
      if (finSet == null) {
        //TODO: inform user of error
        return;
      }
      for (Motor motor : motors) {
        runSimulation(finSet, motor, notMotors, notFins);
      }

    } else if (!finChkBox.isSelected() && !motorChkBox.isSelected()) {
      TrapezoidFinSet finSet = (TrapezoidFinSet) mainViewModel.getRocket().getPartByName(finChcBox.getValue());
      if (finSet == null) {
        //TODO: inform user of error
        return;
      }

      Motor motor = (Motor) mainViewModel.getRocket().getPartByName(motorChcBox.getValue());
      if (motor == null) {
        //TODO: inform user of error
        return;
      }
      runSimulation(finSet, motor, notMotors, notFins);
    }
  }

  private void motorsLoop(TrapezoidFinSet finSet, ArrayList<Motor> motors,
    ArrayList<RocketComponent> notFins, ArrayList<RocketComponent> notMotors) {

    if (motorChkBox.isSelected()) {
      for (Motor motor : motors) {
        File tempRocketPath = createTemporaryRocketFile(finSet, motor, notFins, notMotors);
        Simulation simulation = createSimulation(tempRocketPath, motor, mainViewModel.getSimulation().getAtmosphereFile());
        ISimulationEngine sim = new BirdSimulatorEngine();
        sim.run(simulation);
      }
    } else {
      //get the user selected motor
    }
  }

  private void runSimulation(TrapezoidFinSet finSet, Motor motor,
    ArrayList<RocketComponent> notMotors, ArrayList<RocketComponent> notFins) {

    File tempRocketPath = createTemporaryRocketFile(finSet, motor, notFins, notMotors);
    Simulation simulation = createSimulation(tempRocketPath, motor, mainViewModel.getSimulation().getAtmosphereFile());
    ISimulationEngine sim = new BirdSimulatorEngine();
    sim.run(simulation);
  }

  private Simulation createSimulation(File tempRocketFile, Motor motor, String atmosphereFile) {
    Simulation simulation = new Simulation();
    simulation.setAtmosphereFile(atmosphereFile);
    simulation.setEngineFile(motor.getENGFilePath());
    simulation.setLaunchRail(mainViewModel.getSimulation().getLaunchRail());
    simulation.setRocketFile(tempRocketFile.getAbsolutePath());
    return simulation;
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
      ISerializer<Rocket> serializer = new XmlRocketSerializer();
      serializer.serialize(rocket, outStream);
    } catch (Exception ex) {
      logger.log(Level.WARNING, null, ex);
    }

    return rocketFile;
  }

  @FXML
  void btnMonteCarlo() {
    btnSimulation();
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

    motorChcBox.setTooltip(new Tooltip("Choose a motor"));
    finChcBox.setTooltip(new Tooltip("Choose a fin set"));
  }
}
