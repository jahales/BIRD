package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SimulationController {
  @FXML
  private Label engineFilePath;

  @FXML
  private TextField length;

  @FXML
  private TextField asimuthAngle;

  @FXML
  private TextField polarAngle;

  @FXML
  private Label atmosphereFilePath;

  @FXML
  private Label launchRailFilePath;

  @FXML
  private Label rocketFilePath;

  @FXML
  private ChoiceBox<String> lengthUnits;

  @FXML
  private ChoiceBox<String> polarAngleUnits;

  @FXML
  private ChoiceBox<String> azimuthAngleUnits;

  @FXML
  void runSimulation(ActionEvent event) {

  }

  @FXML
  void openRocketFile(ActionEvent event) {

  }

  @FXML
  void openAtmosphereFile(ActionEvent event) {

  }

  @FXML
  void openEngineFile(ActionEvent event) {

  }

  @FXML
  void openLaunchRail(ActionEvent event) {

  }

  public void initialize() {

  }
}
