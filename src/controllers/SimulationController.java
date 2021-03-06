package controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.AppSettings;
import models.FileHelper;
import models.ISerializer;
import models.MainViewModel;
import models.Measurement;
import models.Unit;
import models.report.DataTable;
import models.rocket.Rocket;
import models.rocket.data.XmlRocketSerializer;
import models.rocket.parts.CircularCylinder;
import models.rocket.parts.Motor;
import models.rocket.parts.NoseCone;
import models.rocket.parts.RocketComponent;
import models.rocket.parts.TrapezoidFinSet;
import models.simulator.BirdSimulatorEngine;
import models.simulator.ISimulationEngine;
import models.simulator.LaunchRail;
import models.simulator.Simulation;

/**
 * Controller for simulation tab view
 *
 * @author Brian Woodruff
 *
 */
public class SimulationController extends BaseController {

  final static Logger logger = Logger.getLogger(SimulationController.class.getName());
  private LaunchRail launchRail;

  MainViewModel mainViewModel;

  /**
   *
   * @param mainViewModel
   */
  public SimulationController(MainViewModel mainViewModel) {
    this.mainViewModel = mainViewModel;
  }

  @FXML
  private VBox root;

  @FXML
  private Label atmosphereFilePath;

  @FXML
  private TextField numberMonteCarlo;

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
      if (component instanceof Motor) {
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
      if (component instanceof TrapezoidFinSet) {
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
    atmosphereFilePath.setText(FileHelper.openAtmosphereFile(mainViewModel, finChkBox));
  }

  @FXML
  void btnSimulation() {
    prepareAndRunSimulation(false);

  }

  @FXML
  void btnMonteCarlo() {
    prepareAndRunSimulation(true);
  }

  /**
   * Spawns a report view with a given dataTable
   * @param dataTable 
   */
  public static void startNewReportView(DataTable dataTable) {
    try {
      Stage stage = new Stage();
      ControllerFactory controllerFactory = new ControllerFactory();
      controllerFactory.addSharedInstance(dataTable);
      IController controller = controllerFactory.create("/views/Report.fxml");

      Scene scene = new Scene((Parent) controller.getView());
      stage.setScene(scene);
      stage.setTitle("BIRD");
      stage.showAndWait();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Does simple validation, data entry into a simulation object, and sends it off to be run
   * @param monteCarlo 
   */
  private void prepareAndRunSimulation(boolean monteCarlo) {

    ArrayList<TrapezoidFinSet> finSets = new ArrayList<>();
    ArrayList<Motor> motors = new ArrayList<>();
    ArrayList<RocketComponent> notFins = new ArrayList<>();
    ArrayList<RocketComponent> notMotors = new ArrayList<>();

    for (RocketComponent component : mainViewModel.getRocket().getExteriorComponents()) {
      if (component instanceof TrapezoidFinSet) {
        finSets.add((TrapezoidFinSet) component);
      } else {
        notFins.add(component);
      }
    }
    for (RocketComponent component : mainViewModel.getRocket().getInteriorComponents()) {
      if (component instanceof Motor) {
        motors.add((Motor) component);
      } else {
        notMotors.add(component);
      }
    }

    if (!simpleRocketValidator(motors, finSets)) {
      return;
    }

    // Create a directory with the same name as the saved rocket
    File rocketDir = null;
    try {
      rocketDir = FileHelper.createRocketDir(mainViewModel, finChkBox);
    } catch (IOException ex) {
      logger.log(Level.SEVERE, "Simulations failed to run! Check file permissions.");
    }

    int finNum = 1;
    int motorNum = 1;
    if (finChkBox.isSelected() && motorChkBox.isSelected()) {
      for (TrapezoidFinSet finSet : finSets) {
        for (Motor motor : motors) {
          runSimulation(finSet, motor, notMotors, notFins, monteCarlo, rocketDir, finNum, motorNum);
          motorNum++;
        }
        finNum++;
      }

    } else if (finChkBox.isSelected() && !motorChkBox.isSelected()) {
      Motor motor = (Motor) mainViewModel.getRocket().getPartByName(motorChcBox.getValue());
      if (motor == null) {
        MessageBoxController.showMessage("You have not selected any motors!", root);
        return;
      }
      else if (motor.getThrustFile() == null || motor.getThrustFile().equals(""))
      {
        MessageBoxController.showMessage("The selected motor does not have an associated thrust file!", root);
        return;
      }
      
      for (TrapezoidFinSet finSet : finSets) {
        runSimulation(finSet, motor, notMotors, notFins, monteCarlo, rocketDir, finNum, motorNum);
        finNum++;
      }

    } else if (!finChkBox.isSelected() && motorChkBox.isSelected()) {
      TrapezoidFinSet finSet = (TrapezoidFinSet) mainViewModel.getRocket().getPartByName(
          finChcBox.getValue());
      if (finSet == null) {
        MessageBoxController.showMessage("You have not selected any fin sets!", root);
        return;
      }
      for (Motor motor : motors) {
        runSimulation(finSet, motor, notMotors, notFins, monteCarlo, rocketDir, finNum, motorNum);
        motorNum++;
      }

    } else if (!finChkBox.isSelected() && !motorChkBox.isSelected()) {
      TrapezoidFinSet finSet = (TrapezoidFinSet) mainViewModel.getRocket().getPartByName(
          finChcBox.getValue());
      if (finSet == null) {
        MessageBoxController.showMessage("You have not selected any fin sets!", root);
        return;
      }

      Motor motor = (Motor) mainViewModel.getRocket().getPartByName(motorChcBox.getValue());
      if (motor == null) {
        MessageBoxController.showMessage("You have not selected any motors!", root);
        return;
      }
      runSimulation(finSet, motor, notMotors, notFins, monteCarlo, rocketDir, finNum, motorNum);
    }
  }

  /**
   * Displays the results in the report view
   * @param dataTable 
   */
  private void displayResult(DataTable dataTable) {
    try {
      ControllerFactory controllerFactory = new ControllerFactory();
      controllerFactory.addSharedInstance(dataTable);
      IController controller = controllerFactory.create("/views/Report.fxml");

      Scene scene = new Scene((Parent) controller.getView());
      Stage stage = new Stage();
      stage.initOwner(atmosphereFilePath.getScene().getWindow());
      stage.setScene(scene);
      stage.setTitle("BIRD Results");
      stage.show();
    } catch (Exception ex) {
      logger.log(Level.WARNING, "Failed to create Report.fxml", ex);
    }
  }

  /**
   * Creates and runs a simulation
   * @param finSet
   * @param motor
   * @param notMotors
   * @param notFins
   * @param monteCarlo
   * @param rocketDir
   * @param finNum
   * @param motorNum 
   */
  private void runSimulation(TrapezoidFinSet finSet, Motor motor,
      ArrayList<RocketComponent> notMotors, ArrayList<RocketComponent> notFins, boolean monteCarlo,
      File rocketDir, int finNum, int motorNum) {

    File resultDir;
    try {
      resultDir = FileHelper.createResultsFolder(rocketDir, finNum, motorNum);
    } catch (IOException ex) {
      logger.log(Level.SEVERE, "A simulation failed to run, check file pathing.");
      return;
    }
    File resultsFile;
    try {
      resultsFile = FileHelper.spawnResultsFilePath(resultDir, finNum, motorNum);
    } catch (IOException ex) {
      logger.log(Level.SEVERE, "A simulation failed to run, check file pathing.");
      return;
    }

    Rocket innerRocket = createTempRocket(finSet, motor, notFins, notMotors);
    File innerRocketFile = createInnerRocketFile(innerRocket, resultDir, finNum, motorNum);

    Simulation simulation = createSimulation(innerRocketFile, resultsFile, motor, mainViewModel
        .getSimulation().getAtmosphereFile());

    simulation.setIsMonteCarlo(monteCarlo);
    try {
      simulation.setMonteNumber(Integer.parseInt(numberMonteCarlo.getText()));
    } catch (NumberFormatException nfe) {
      simulation.setIsMonteCarlo(false);
      simulation.setMonteNumber(1);
    }

    ISimulationEngine sim = new BirdSimulatorEngine();
    DataTable result = sim.run(simulation);
    displayResult(result);
  }

  /**
   * Creates an simulation object
   * @param tempRocketFile
   * @param resultsOutput
   * @param motor
   * @param atmosphereFile
   * @return 
   */
  private Simulation createSimulation(File tempRocketFile, File resultsOutput, Motor motor,
      String atmosphereFile) {
    Simulation simulation = new Simulation();
    simulation.setOutputFile(resultsOutput.toString());
    simulation.setAtmosphereFile(atmosphereFile);
    simulation.setEngineFile(motor.getENGFilePath());
    simulation.setLaunchRail(mainViewModel.getSimulation().getLaunchRail());
    simulation.setRocketFile(tempRocketFile.getAbsolutePath());
    return simulation;
  }

  /**
   * Creates a temporary rocket object to write to a file that contains only 1 motor/ 1 fin
   * @param finSet
   * @param motor
   * @param exteriors
   * @param interiors
   * @return 
   */
  private Rocket createTempRocket(TrapezoidFinSet finSet, Motor motor,
      ArrayList<RocketComponent> exteriors, ArrayList<RocketComponent> interiors) {
    exteriors.add(finSet);
    interiors.add(motor);

    Rocket rocket = mainViewModel.getRocket();
    rocket.setExteriorComponents(exteriors);
    rocket.setInteriorComponents(interiors);
    return rocket;
  }

  /**
   * Writes a rocket file that is to be placed among the results.
   * @param rocket
   * @param rocketSimDir
   * @param finNum
   * @param motorNum
   * @return 
   */
  private File createInnerRocketFile(Rocket rocket, File rocketSimDir, int finNum, int motorNum) {
    File rocketFile = new File(rocketSimDir, rocketSimDir.getName() + ".xml");
    try {
      OutputStream outStream = new FileOutputStream(rocketFile);
      ISerializer<Rocket> serializer = new XmlRocketSerializer();
      serializer.serialize(rocket, outStream);
    } catch (Exception ex) {
      logger.log(Level.WARNING, null, ex);
    }

    return rocketFile;
  }

  /**
   * A simple validator. Deep validation to be done in the model
   * @param motors
   * @param finSets
   * @return 
   */
  private boolean simpleRocketValidator(ArrayList<Motor> motors, ArrayList<TrapezoidFinSet> finSets) {
    if (motors.isEmpty()) {
      MessageBoxController.showMessage("Your rocket does not have any motors!", root);
      return false;
    }
    if (finSets.isEmpty()) {
      MessageBoxController.showMessage("Your rocket does not have any fin sets!", root);
      return false;
    }
    NoseCone noseCone = null;
    for (RocketComponent component : mainViewModel.getRocket().getExteriorComponents()) {
      if (component instanceof NoseCone) {
        noseCone = (NoseCone) component;
      }
    }
    if (noseCone == null) {
      MessageBoxController.showMessage("Your rocket does not have a nose cone", root);
      return false;
    }

    CircularCylinder body = null;
    for (RocketComponent component : mainViewModel.getRocket().getExteriorComponents()) {
      if (component instanceof CircularCylinder) {
        body = (CircularCylinder) component;
      }
    }
    if (body == null) {
      MessageBoxController.showMessage("Your rocket does not have a body!", root);
      return false;
    }

    // Will be added in when the simulator can handle atmosphere files (soon, so
    // says the owner)
    /*
     * if (mainViewModel.getSimulation().getAtmosphereFile() == null) {
     * MessageBoxController
     * .showMessage("You have not specified an atmosphere file!", root); return
     * false; }
     */
    return true;
  }

  /**
   * Updates the unit when user selects a unit.
   *
   * @param field
   * @param measurement
   */
  private void addUnitListener(ChoiceBox<String> field, Measurement measurement) {
    field.getSelectionModel().selectedItemProperty()
    .addListener((ChangeListener<String>) (arg0, arg1, arg2) -> {
      if (!arg2.isEmpty()) {
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
    field.textProperty().addListener((ChangeListener<String>) (arg0, arg1, arg2) -> {
      try {
        if (!arg2.isEmpty()) {
          measurement.setValue(Double.parseDouble(arg2));
        }
      } catch (NumberFormatException e) {
        e.printStackTrace();
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
    field.textProperty().addListener((ChangeListener<String>) (arg0, arg1, arg2) -> {
      try {
        if (!arg2.isEmpty()) {
          measurement.setError(Double.parseDouble(arg2));
        }
      } catch (NumberFormatException e) {
        e.printStackTrace();
      }
    });
  }

  /**
   *
   */
  public void initialize() {
    launchRail = AppSettings.getInstance().getLaunchRail();
    mainViewModel.getSimulation().setLaunchRail(launchRail);

    initializeLaunchControls(launchRail);

    motorChcBox.setTooltip(new Tooltip("Choose a motor"));
    finChcBox.setTooltip(new Tooltip("Choose a fin set"));
  }

  private void initializeLaunchControls(LaunchRail launchRail) {
    lengthValue.setText(Double.toString(launchRail.getLength().getValue()));
    lengthError.setText(Double.toString(launchRail.getLength().getError()));
    lengthUnits.setValue(launchRail.getLength().getUnit().toString());

    polarAngleValue.setText(Double.toString(launchRail.getPolarAngle().getValue()));
    polarAngleError.setText(Double.toString(launchRail.getPolarAngle().getError()));
    polarAngleUnits.setValue(launchRail.getPolarAngle().getUnit().toString());

    azimuthAngleValue.setText(Double.toString(launchRail.getAzimuthAngle().getValue()));
    azimuthAngleError.setText(Double.toString(launchRail.getAzimuthAngle().getError()));
    azimuthAngleUnits.setValue(launchRail.getAzimuthAngle().getUnit().toString());

    addValueListener(lengthValue, launchRail.getLength());
    addValueListener(polarAngleValue, launchRail.getPolarAngle());
    addValueListener(azimuthAngleValue, launchRail.getAzimuthAngle());

    addErrorListener(lengthError, launchRail.getLength());
    addErrorListener(polarAngleError, launchRail.getPolarAngle());
    addErrorListener(azimuthAngleError, launchRail.getAzimuthAngle());

    addUnitListener(lengthUnits, launchRail.getLength());
    addUnitListener(polarAngleUnits, launchRail.getPolarAngle());
    addUnitListener(azimuthAngleUnits, launchRail.getAzimuthAngle());
  }
}
