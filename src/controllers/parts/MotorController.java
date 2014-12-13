package controllers.parts;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.FileHelper;
import models.rocket.parts.Motor;
import controllers.BaseController;
import controllers.MessageBoxController;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.List;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import models.report.CSVReader;
import models.report.DataTable;

/**
 * Controller for the {@link Motor} view
 *
 * @author Brian Woodruff
 *
 */
public class MotorController extends BaseController {

  private Motor motor;

  @FXML
  private TextField manufacturerValue;

  @FXML
  private TextField delaysValue;

  @FXML
  private TextField fuelMassValue;

  @FXML
  private TextField fuelMassError;

  @FXML
  private ChoiceBox<String> fuelMassUnits;

  @FXML
  private TextField polarAngleValue;

  @FXML
  private TextField polarAngleError;

  @FXML
  private ChoiceBox<String> polarAngleUnits;

  @FXML
  private TextField azimuthAngleValue;

  @FXML
  private TextField azimuthAngleError;

  @FXML
  private ChoiceBox<String> azimuthAngleUnits;

  @FXML
  private Label thrustFile;

  @FXML
  private Button exportThrustButton;

  @FXML
  private void loadThrustFile() {
    // TODO: Joe implement me
    // update <thrustFile>
    String fileName = FileHelper.openMotorFile(thrustFile);
    thrustFile.setText(fileName);
    motor.setThrustFile(fileName);
  }

  @FXML
  private void exportThrustFile() throws Exception {
    // Make sure there is a .CSV file attached to the motor
    if (this.motor == null || motor.getThrustFile() == null || motor.getThrustFile().equals(""))
    {
      MessageBoxController.showMessage("No thrust .csv file is attached to the motor.", this.exportThrustButton);
    }
    
    // Select a file to export to
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Export Thrust Data");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("ENG file", "*.eng"));
    File exportFile = fileChooser.showSaveDialog(this.exportThrustButton.getScene().getWindow());

    if (exportFile == null) {
      return;
    }

    // Load the thrust data
    CSVReader reader = new CSVReader();
    DataTable csv = reader.deserialize(new FileInputStream(motor.getThrustFile()));
    
    // Create the .ENG string
    StringBuilder sb = new StringBuilder();
    sb.append(motor.getName());
    sb.append(" ");
    sb.append(motor.getDiameter().getValue());
    sb.append(" ");
    sb.append(motor.getAxialLength().getValue());
    sb.append(" ");
    sb.append(motor.getDelays());
    sb.append(" ");
    sb.append(motor.getFuelMass().getValue());
    sb.append(" ");
    sb.append(motor.getMass().getValue());
    sb.append(" ");
    sb.append(motor.getManufacturer());
    sb.append(" ");
    sb.append("\r\n");
    
    List<Number> t = csv.getColumn("t");
    List<Number> f = csv.getColumn("N");
    
    for (int i = 0; i < csv.getRows(); i++)
    {
      sb.append(t.get(i));
      sb.append(" ");
      sb.append(f.get(i));
      sb.append("\r\n");
    }
    
    // Write the .ENG file
    PrintWriter out = new PrintWriter(exportFile);
    out.print(sb.toString());
    out.close();
  }

  /**
   * @param rocket a rocket this view will modify
   */
  public MotorController(Motor motor) {
    this.motor = motor;
  }

  /**
   * Initialize values and set listeners
   */
  public void initialize() {
    // Populate fields with whatever values we got
    manufacturerValue.setText(motor.getManufacturer());
    delaysValue.setText(motor.getDelays());
    thrustFile.setText(motor.getThrustFile());

    fuelMassValue.setText(Double.toString(motor.getFuelMass().getValue()));
    fuelMassError.setText(Double.toString(motor.getFuelMass().getError()));
    fuelMassUnits.setValue(motor.getFuelMass().getUnit().toString());

    polarAngleValue.setText(Double.toString(motor.getPolarAngle().getValue()));
    polarAngleError.setText(Double.toString(motor.getPolarAngle().getError()));
    polarAngleUnits.setValue(motor.getPolarAngle().getUnit().toString());

    azimuthAngleValue.setText(Double.toString(motor.getAzimuthAngle().getValue()));
    azimuthAngleError.setText(Double.toString(motor.getAzimuthAngle().getError()));
    azimuthAngleUnits.setValue(motor.getAzimuthAngle().getUnit().toString());

    // Set listeners
    manufacturerValue.textProperty().addListener(
        (ChangeListener<String>) (observable, oldValue, newValue) -> MotorController.this.motor
        .setManufacturer(newValue));
    delaysValue.textProperty().addListener(
        (ChangeListener<String>) (observable, oldValue, newValue) -> MotorController.this.motor
        .setDelays(newValue));
    thrustFile.textProperty().addListener(
        (ChangeListener<String>) (observable, oldValue, newValue) -> MotorController.this.motor
        .setThrustFile(newValue));

    ListenerHelpers.addValueListener(fuelMassValue, motor.getFuelMass());
    ListenerHelpers.addValueListener(polarAngleError, motor.getPolarAngle());
    ListenerHelpers.addValueListener(azimuthAngleValue, motor.getAzimuthAngle());

    ListenerHelpers.addErrorListener(fuelMassError, motor.getFuelMass());
    ListenerHelpers.addErrorListener(polarAngleError, motor.getPolarAngle());
    ListenerHelpers.addErrorListener(azimuthAngleError, motor.getAzimuthAngle());

    ListenerHelpers.addUnitListener(fuelMassUnits, motor.getFuelMass());
    ListenerHelpers.addUnitListener(polarAngleUnits, motor.getPolarAngle());
    ListenerHelpers.addUnitListener(azimuthAngleUnits, motor.getAzimuthAngle());
  }
}
