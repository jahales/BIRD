package controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.MainViewModel;
import models.Measurement;
import models.Unit;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.AppSettings;
import models.ISerializer;
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

  @FXML
  void btnSimulation() {
    prepareAndRunSimulation(false);

  }

  @FXML
  void btnMonteCarlo() {
    prepareAndRunSimulation(true);
  }

  private void prepareAndRunSimulation(boolean monteCarlo) {

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
      if (component instanceof Motor) {
        motors.add((Motor) component);
      } else {
        notMotors.add(component);
      }
    }

    if (!simpleRocketValidator(motors, finSets)) {
      return;
    }

    if (finChkBox.isSelected()
        && motorChkBox.isSelected()) {
      for (TrapezoidFinSet finSet : finSets) {
        for (Motor motor : motors) {
          runSimulation(finSet, motor, notMotors, notFins, monteCarlo);
        }
      }

    } else if (finChkBox.isSelected()
        && !motorChkBox.isSelected()) {
      Motor motor = (Motor) mainViewModel.getRocket().getPartByName(motorChcBox.getValue());
      if (motor == null) {
        MessageBoxController.showMessage("You have not selected any motors!", this.getView());
        return;
      }
      for (TrapezoidFinSet finSet : finSets) {
        runSimulation(finSet, motor, notMotors, notFins, monteCarlo);
      }

    } else if (!finChkBox.isSelected()
        && motorChkBox.isSelected()) {

      TrapezoidFinSet finSet = (TrapezoidFinSet) mainViewModel.getRocket().getPartByName(finChcBox.getValue());
      if (finSet == null) {
        MessageBoxController.showMessage("You have not selected any fin sets!", this.getView());
        return;
      }
      for (Motor motor : motors) {
        runSimulation(finSet, motor, notMotors, notFins, monteCarlo);
      }

    } else if (!finChkBox.isSelected()
        && !motorChkBox.isSelected()) {
      TrapezoidFinSet finSet = (TrapezoidFinSet) mainViewModel.getRocket().getPartByName(finChcBox.getValue());
      if (finSet == null) {
        MessageBoxController.showMessage("You have not selected any fin sets!", this.getView());
        return;
      }

      Motor motor = (Motor) mainViewModel.getRocket().getPartByName(motorChcBox.getValue());
      if (motor == null) {
        MessageBoxController.showMessage("You have not selected any motors!", this.getView());
        return;
      }
      runSimulation(finSet, motor, notMotors, notFins, monteCarlo);
    }
  }
  
  private void displayResult(DataTable dataTable)
  {
    try {
      ControllerFactory controllerFactory = new ControllerFactory();
      controllerFactory.addSharedInstance(dataTable);
      IController controller = controllerFactory.create("/views/Report.fxml");
      
      Scene scene = new Scene((Parent)controller.getView());
      Stage stage = new Stage();
      stage.setScene(scene);
      stage.setTitle("BIRD Results");
      stage.show();
    } catch (Exception ex) {
      logger.log(Level.WARNING, "Failed to create Report.fxml", ex);
    }
  }

  private void runSimulation(TrapezoidFinSet finSet, Motor motor,
      ArrayList<RocketComponent> notMotors, ArrayList<RocketComponent> notFins, boolean monteCarlo) {

    File tempRocketPath = createTemporaryRocketFile(finSet, motor, notFins, notMotors);
    Simulation simulation = createSimulation(tempRocketPath, motor, mainViewModel.getSimulation().getAtmosphereFile());
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

    exteriors.add(finSet);
    interiors.add(motor);

    Rocket rocket = mainViewModel.getRocket();
    rocket.setExteriorComponents(exteriors);
    rocket.setInteriorComponents(interiors);

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

  private boolean simpleRocketValidator(ArrayList<Motor> motors, ArrayList<TrapezoidFinSet> finSets) {
    if (motors.isEmpty()) {
      MessageBoxController.showMessage("Your rocket does not have any motors!", this.getView());
      return false;
    }
    if (finSets.isEmpty()) {
      MessageBoxController.showMessage("Your rocket does not have any fin sets!", this.getView());
      return false;
    }
    NoseCone noseCone = null;
    for (RocketComponent component : mainViewModel.getRocket().getExteriorComponents()) {
      if (component.getClass().isInstance(new NoseCone())) {
        noseCone = (NoseCone) component;
      }
    }
    if (noseCone == null) {
      MessageBoxController.showMessage("Your rocket does not have a nose cone", this.getView());
      return false;
    }

    CircularCylinder body = null;
    for (RocketComponent component : mainViewModel.getRocket().getExteriorComponents()) {
      if (component.getClass().isInstance(new CircularCylinder())) {
        body = (CircularCylinder) component;
      }
    }
    if (body == null) {
      MessageBoxController.showMessage("Your rocket does not have a body!", this.getView());
      return false;
    }
    if (mainViewModel.getSimulation().getAtmosphereFile() == null) {
      MessageBoxController.showMessage("You have not specified an atmosphere file!", this.getView());
      return false;
    }
    return true;
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
        if (!arg2.isEmpty()) {
          measurement.setUnit(Unit.valueOf(arg2));
        }
      }
    }
    );
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
          if (!arg2.isEmpty()) {
            measurement.setValue(Double.parseDouble(arg2));
          }
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
          if (!arg2.isEmpty()) {
            measurement.setError(Double.parseDouble(arg2));
          }
        } catch (NumberFormatException e) {
          e.printStackTrace();
        }
      }
    }
    );
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
    return;
  }
}
