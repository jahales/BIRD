/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controllers.ControllerFactory;
import controllers.IController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.rocket.data.RaspEngineSerializer;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import models.rocket.Rocket;
import models.rocket.data.XmlRocketSerializer;
import controllers.Main;
import controllers.MessageBoxController;
import java.io.FileNotFoundException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.report.CSVReader;
import models.report.DataTable;
import models.rocket.parts.Motor;

/**
 *
 * @author Joseph
 */
public class FileHelper {

  final static Logger logger = Logger.getLogger(FileHelper.class.getName());

  /**
   * Opens a CSV file and loads it into a new report view.
   *
   * @param mainViewModel
   * @param root
   */
  public static void openCSV(MainViewModel mainViewModel, Node root) {
    File openFile;
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    // Set initial file path
    configInitialDirectory(fileChooser);
    fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV file", "*.csv"));
    openFile = fileChooser.showOpenDialog(root.getScene().getWindow());

    if (openFile != null) {
      AppSettings.getInstance().setPresentWorkingDirectory(openFile.getParentFile());

      try {
        DataTable dataTable = new CSVReader().deserialize(new FileInputStream(openFile));
        ControllerFactory controllerFactory = new ControllerFactory();
        controllerFactory.addSharedInstance(dataTable);
        IController controller = controllerFactory.create("/views/Report.fxml");

        Scene scene = new Scene((Parent) controller.getView());
        Stage stage = new Stage();
        stage.initOwner(root.getScene().getWindow());
        stage.setScene(scene);
        stage.setTitle("BIRD Results");
        stage.show();
      } catch (Exception ex) {
        logger.log(Level.WARNING, "Failed to create Report.fxml", ex);
      }
    }
  }

  /**
   * Loads a rocket, and opens a new window with a new mainviewmodel
   *
   * @param mainViewModel
   * @param root
   */
  public static void openRocket(MainViewModel mainViewModel, Node root) {
    File openFile;
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    // Set initial file path
    configInitialDirectory(fileChooser);
    fileChooser.getExtensionFilters().add(new ExtensionFilter("XML file", "*.xml"));
    openFile = fileChooser.showOpenDialog(root.getScene().getWindow());

    if (openFile == null) {
      return;
    }

    AppSettings.getInstance().setPresentWorkingDirectory(openFile.getParentFile());

    try {
      MainViewModel newModel = new MainViewModel();
      newModel.setPresentWorkingFile(openFile);
      newModel.setRocket(loadRocket(openFile));
      newModel.setNeverBeenSaved(false);
      Main.startNewMainView(newModel);
    } catch (Exception ex) {
      MessageBoxController.showMessage("Loading Failed!", root);
      logger.log(Level.SEVERE, "Load rocket failed");
    }

  }

  /**
   * Saves the rocket with a prompt for location
   *
   * @param mainViewModel
   * @param root
   */
  static public void RocketSaveAs(MainViewModel mainViewModel, Node root) {
    File saveFile;
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    configInitialDirectory(fileChooser);
    fileChooser.getExtensionFilters().add(new ExtensionFilter("XML file", "*.xml"));
    saveFile = fileChooser.showSaveDialog(root.getScene().getWindow());

    if (saveFile == null) {
      return;
    }

    try {
      writeRocket(saveFile, mainViewModel);
      mainViewModel.setNeverBeenSaved(false);
      mainViewModel.setPresentWorkingFile(saveFile);
    } catch (Exception ex) {
      MessageBoxController.showMessage("Saving Failed!", root);
    }
  }

  /**
   * Saves the rocket, if it hasn't been saved, it prompts for location
   *
   * @param mainViewModel
   * @param root
   */
  public static void saveRocket(MainViewModel mainViewModel, Node root) {
    File saveFile;
    if (mainViewModel.hasNeverBeenSaved()) {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Open Resource File");
      configInitialDirectory(fileChooser);
      fileChooser.getExtensionFilters().add(new ExtensionFilter("XML file", "*.xml"));
      saveFile = fileChooser.showSaveDialog(root.getScene().getWindow());

      if (saveFile == null) {
        return;
      }

      try {
        writeRocket(saveFile, mainViewModel);
        mainViewModel.setNeverBeenSaved(false);
        mainViewModel.setPresentWorkingFile(saveFile);
      } catch (Exception ex) {
        MessageBoxController.showMessage("Saving Failed!", root);
      }
    } else {
      if (mainViewModel.isUnsaved()) {
        // Only attempt to save if there's actually been a change
        saveFile = mainViewModel.getPresentWorkingFile();
        try {
          writeRocket(saveFile, mainViewModel);
          mainViewModel.setNeverBeenSaved(false);
          mainViewModel.setPresentWorkingFile(saveFile);
        } catch (Exception ex) {
          MessageBoxController.showMessage("Saving Failed!", root);
        }
      }
    }
  }

  /**
   * Gets the location of a motor file, but doesn't read any information
   * @param root
   * @return 
   */
  static public String openMotorFile(Node root) {
    File openFile;
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    // Set initial file path
    configInitialDirectory(fileChooser);
    fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV file", "*.csv"));
    openFile = fileChooser.showOpenDialog(root.getScene().getWindow());
    if (openFile != null) {
      return openFile.getAbsolutePath();
    } else {
      return "";
    }
  }

  /**
   * Takes a CSV file and relevant motor and creates a .eng file
   * @param motor
   * @param root 
   */
  public static void exportMotorFile(Motor motor, Node root) {
    // Make sure there is a .CSV file attached to the motor
    if (motor == null || motor.getThrustFile() == null || motor.getThrustFile().equals("")) {
      MessageBoxController.showMessage("No thrust .csv file is attached to the motor.", root);
      return;
    }

    // Select a file to export to
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Export Thrust Data");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("ENG file", "*.eng"));
    configInitialDirectory(fileChooser);
    File exportFile = fileChooser.showSaveDialog(root.getScene().getWindow());

    if (exportFile == null) {
      return;
    }
    try {
      OutputStream outStream = new FileOutputStream(exportFile);
      ISerializer serializer = new RaspEngineSerializer();
      serializer.serialize(motor, outStream);
      AppSettings.getInstance().setPresentWorkingDirectory(exportFile.getParentFile());
    } catch (Exception ex) {
      MessageBoxController.showMessage("Exporting to .ENG Failed!", root);
      logger.log(Level.WARNING, ".ENG file export failed");
    }
  }

  /**
   * Given a file path, this function loads a rocket into a mainViewModel
   * @param openFile
   * @return
   * @throws Exception 
   */
  private static Rocket loadRocket(File openFile) throws Exception {
    try {
      try {
        AppSettings.getInstance().setPresentWorkingDirectory(openFile.getParentFile());
        InputStream inputStream = new FileInputStream(openFile);
        ISerializer<Rocket> serializer = new XmlRocketSerializer();
        return serializer.deserialize(inputStream);
      } catch (Exception ex) {
        logger.log(Level.WARNING, "Failed to load rocket");
        throw new Exception();
      }
    } catch (NullPointerException npex) {
      logger.log(Level.WARNING, "The selected file is null");
      throw new Exception();
    }
  }

  /**
   * Given a filepath, this writes a rocket to a file
   * @param saveFile
   * @param mainViewModel
   * @throws Exception 
   */
  static private void writeRocket(File saveFile, MainViewModel mainViewModel) throws Exception {
    try {
      // Set the new present working directory to the save file's directory
      AppSettings.getInstance().setPresentWorkingDirectory(saveFile.getParentFile());
    } catch (NullPointerException npex) {
      logger.log(Level.WARNING, "File Chooser did not choose a file", npex);
      throw new Exception();
    }

    try {
      OutputStream outStream = new FileOutputStream(saveFile);
      ISerializer<Rocket> serializer = new XmlRocketSerializer();
      serializer.serialize(mainViewModel.getRocket(), outStream);
    } catch (Exception ex) {
      logger.log(Level.WARNING, null, ex);
      throw new Exception();
    }
  }

  /**
   * gets the location, but does not read, an atmosphere file
   * @param mainViewModel
   * @param root
   * @return 
   */
  static public String openAtmosphereFile(MainViewModel mainViewModel, Node root) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Atmosphere File");
    FileHelper.configInitialDirectory(fileChooser);
    fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV file", "*.csv"));
    File file = fileChooser.showOpenDialog(root.getScene().getWindow());
    if (file != null) {
      mainViewModel.getSimulation().setAtmosphereFile(file.getAbsolutePath());
      return file.getName();
    }
    return "";
  }

  /**
   * Helper function to help set up a file chooser.
   * @param fileChooser 
   */
  static private void configInitialDirectory(FileChooser fileChooser) {
    fileChooser.setInitialDirectory(AppSettings.getInstance().getPresentWorkingDirectory());
    // Make sure the initial directory is a directory
    if (!fileChooser.getInitialDirectory().isDirectory()) {
      logger.warning("Invalid initial directory path");
      fileChooser.setInitialDirectory(null);
    }
  }

  /**
   * Creates a new instance of the application
   * @param rocket
   * @param mainViewModel 
   */
  public static void spawnNewInstance(Rocket rocket, MainViewModel mainViewModel) {
    MainViewModel newModel = new MainViewModel();
    newModel.setPresentWorkingFile(mainViewModel.getPresentWorkingFile());
    newModel.setRocket(rocket);
    Main.startNewMainView(newModel);
  }

  /**
   * Creates a directory to be used for storing results files.
   * @param mainViewModel
   * @param root
   * @return
   * @throws IOException 
   */
  public static File createRocketDir(MainViewModel mainViewModel, Node root) throws IOException {
    File rocketFolder;
    if (mainViewModel.getPresentWorkingFile() == null) {
      saveRocket(mainViewModel, root);
    }
    int dotIndex = mainViewModel.getPresentWorkingFile().getName().indexOf(".");
    rocketFolder = new File(mainViewModel.getPresentWorkingFile().getParentFile().getPath(),
      mainViewModel.getPresentWorkingFile().getName().substring(0, dotIndex));
    rocketFolder.mkdir();
    return rocketFolder;
  }

  /**
   * creates a folder for the results of one specific variant of a rocket
   * @param rocketFolder
   * @param finNum
   * @param motorNum
   * @return
   * @throws IOException 
   */
  public static File createResultsFolder(File rocketFolder, int finNum, int motorNum)
    throws IOException {
    File file = new File(rocketFolder, "Variant-Motor" + motorNum + "-FinSet" + finNum);
    file.mkdir();
    return file;
  }

  /**
   * Creates the file for the results of one specific variant of a rocket
   * @param resultsFolder
   * @param finNum
   * @param motorNum
   * @return
   * @throws IOException 
   */
  public static File spawnResultsFilePath(File resultsFolder, int finNum, int motorNum)
    throws IOException {
    File file = new File(resultsFolder, "Variant-Motor" + motorNum + "-FinSet" + finNum + ".csv");
    file.createNewFile();
    return file;
  }
}
