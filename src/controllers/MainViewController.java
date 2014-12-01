package controllers;

import static controllers.Main.startNewMainView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.AppSettings;
import models.MainViewModel;
import models.rocket.Rocket;
import models.rocket.data.BirdRocketSerializer;
import models.rocket.data.IRocketSerializer;

/**
 * Main view of our program. 3 Tabs are included. This controller focuses on menu items.
 *
 * @author Brian Woodruff, Joseph Hales
 *
 */
public class MainViewController extends BaseController {

  final static Logger logger = Logger.getLogger(MainViewController.class.getName());
  MainViewModel mainViewModel;

  @FXML private javafx.scene.layout.Pane root;
  
  /**
   * @param modelState
   */
  public MainViewController(MainViewModel mainViewState) {
    this.mainViewModel = mainViewState;
  }

  /**
   * File->New
   *
   * @param event
   */
  @FXML
  void fileNew() {
    //Spawn new instance with mostly empty data
    spawnNewInstance(new Rocket());
  }

  /**
   * File->Open ...
   *
   * @param event
   */
  @FXML
  void fileOpen() {
    if (mainViewModel.isUnsaved()) {
      //prompt user if he wants to save or not
    }
    File openFile;
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    //Set initial file path
    configInitialDirectory(fileChooser);
    openFile = fileChooser.showOpenDialog((Stage) root.getScene().getWindow());

    try {
      spawnNewInstance(loadRocket(openFile));
    } catch (Exception ex) {
      //Needs a prompt to let the user know that loading the file errored
      logger.log(Level.SEVERE, null, ex);
    }
  }

  /**
   * File->Save
   *
   * @param event
   */
  @FXML
  void fileSave() {
    File saveFile;
    if (mainViewModel.hasNeverBeenSaved()) {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Open Resource File");
      configInitialDirectory(fileChooser);
      saveFile = fileChooser.showSaveDialog((Stage) root.getScene().getWindow());

      try {
        saveRocket(saveFile);
        mainViewModel.setNeverBeenSaved(false);
      } catch (Exception ex) {
        //Inform user that the saving did not work.
      }
    } else {
      if (mainViewModel.isUnsaved()) {
        //Only attempt to save if there's actually been a change
        saveFile = mainViewModel.getPresentWorkingFile();
        try {
          saveRocket(saveFile);
          mainViewModel.setNeverBeenSaved(false);
        } catch (Exception ex) {
          //Inform user that the saving did not work.
        }
      }
    }
  }

  /**
   * File->SaveAs...
   *
   * @param event
   */
  @FXML
  void fileSaveAs() {
    File saveFile;
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    configInitialDirectory(fileChooser);
    saveFile = fileChooser.showSaveDialog((Stage) root.getScene().getWindow());

    try {
      saveRocket(saveFile);
      mainViewModel.setNeverBeenSaved(false);
    } catch (Exception ex) {
      //Inform user that the saving did not work.
    }
  }

  /**
   * File->Quit
   *
   * @param event
   */
  @FXML
  void fileQuit() {
    if (promptSaveQuit()) {
      Stage stage = (Stage) root.getScene().getWindow();
      stage.close();
    }
  }

  /**
   * Help->About
   *
   * @param event
   */
  @FXML
  void helpAbout() {

  }

  private Rocket loadRocket(File openFile) throws Exception {
    try {
      //Set the new present working directory to the save file's directory
      mainViewModel.setPresentWorkingDirectory(openFile.getParentFile());
    } catch (NullPointerException npex) {
      logger.log(Level.WARNING, "File Chooser did not choose a file");
      throw new Exception();
    }

    try {
      InputStream inputStream = new FileInputStream(openFile);
      IRocketSerializer serializer = new BirdRocketSerializer();
      return serializer.deserialize(inputStream);
    } catch (Exception ex) {
      logger.log(Level.WARNING, null, ex);
      throw new Exception();
    }
  }

  private void saveRocket(File saveFile) throws Exception {
    try {
      //Set the new present working directory to the save file's directory
      mainViewModel.setPresentWorkingDirectory(saveFile.getParentFile());
    } catch (NullPointerException npex) {
      logger.log(Level.WARNING, "File Chooser did not choose a file", npex);
      throw new Exception();
    }

    try {
      OutputStream outStream = new FileOutputStream(saveFile);
      IRocketSerializer serializer = new BirdRocketSerializer();
      serializer.serialize(mainViewModel.getRocket(), outStream);
    } catch (Exception ex) {
      logger.log(Level.WARNING, null, ex);
      throw new Exception();
    }
  }

  private void spawnNewInstance(Rocket rocket) {
    MainViewModel newModel = new MainViewModel();
    newModel.setPresentWorkingDirectory(mainViewModel.getPresentWorkingDirectory());
    newModel.setRocket(rocket);
    startNewMainView(newModel);
  }

  private boolean promptSaveQuit() {
    try {
      //Create the view and controller
      ControllerFactory controllerFactory = new ControllerFactory();
      SaveDialogController controller = (SaveDialogController)controllerFactory
          .create("/views/SaveDialog.fxml");      
      
      //Setup the stage
      Scene scene = new Scene((Parent) controller.getView());
      Stage stage = new Stage();
      stage.setScene(scene);
      stage.initOwner((Stage) root.getScene().getWindow());
      stage.setTitle("Do you wish to save?");
      stage.showAndWait();
      
      //get results from the controller
      if (controller.isDoSave()) {
        fileSave();
      }
      
      return controller.isDoContinue();
    } catch (Exception ex) {
      logger.log(Level.WARNING, "Failed to show save dialog.", ex);
      return false;
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
}
