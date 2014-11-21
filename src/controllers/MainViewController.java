package controllers;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import models.AppSettings;
import models.ModelState;

/**
 * Main view of our program. 3 Tabs are included. This controller focuses on
 * menu items.
 *
 * @author Brian Woodruff
 *
 */
public class MainViewController {
  final static Logger logger = Logger.getLogger(MainViewController.class.getName());
  ModelState modelState;

  /**
   * @param modelState
   */
  public MainViewController(ModelState modelState) {
    this.modelState = modelState;
  }

  /**
   * File->New
   *
   * @param event
   */
  @FXML
  void fileNew() {
    if (modelState.isUnsaved()) {
      // prompt user if he wants to save or not
    }
    // Create a new rocket and begin work
  }

  /**
   * File->Open
   * ...
   * @param event
   */
  @FXML
  void fileOpen() {
    File openFile;
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    // Set initial file path
    try {
      // If there's a present working directory, open up to that directory
      if (modelState.getPresentWorkingDirectory() == null) {
        fileChooser.setInitialDirectory(new File(AppSettings.getInstance().getDefaultRocketPath()));
      } else {
        fileChooser.setInitialDirectory(modelState.getPresentWorkingDirectory());
      }
      openFile = fileChooser.showOpenDialog(null); // How do I access the view's
                                                   // window?
    } catch (Exception ex) {
      logger.warning("Invalid initial directory path");
      fileChooser.setInitialDirectory(null);
      openFile = fileChooser.showOpenDialog(null);
    }

    try {
      // Set the new present working directory to the save file's directory
      modelState.setPresentWorkingDirectory(openFile.getParentFile());
      // Call appropiate model load function
    } catch (NullPointerException npe) {
      logger.log(Level.FINE, "File Chooser did not choose a file");
    }

    // This should be placed inside of the model save function, but is here for
    // testing
    modelState.setNeverBeenSaved(false);
    modelState.setUnsaved(false);
  }

  /**
   * File->Save
   *
   * @param event
   */
  @FXML
  void fileSave() {
    File saveFile;
    if (modelState.hasNeverBeenSaved()) {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Open Resource File");
      // Set initial file path
      try {
        // If there's a present working directory, open up to that directory
        if (modelState.getPresentWorkingDirectory() == null) {
          fileChooser.setInitialDirectory(new File(AppSettings.getInstance().getDefaultRocketPath()));
        } else {
          fileChooser.setInitialDirectory(modelState.getPresentWorkingDirectory());
        }
        saveFile = fileChooser.showOpenDialog(null);
      } catch (Exception ex) {
        logger.warning("Invalid initial directory path");
        fileChooser.setInitialDirectory(null);
        saveFile = fileChooser.showSaveDialog(null);
      }

      try {
        // Set the new present working directory to the save file's directory
        modelState.setPresentWorkingDirectory(saveFile.getParentFile());
        // Call appropiate model load function
      } catch (NullPointerException npe) {
        logger.log(Level.FINE, "File Chooser did not choose a file");
      }
    } else {
      if (modelState.isUnsaved()) {
        saveFile = modelState.getPresentWorkingFile();
        try {
          // Set the new present working directory to the save file's directory
          modelState.setPresentWorkingDirectory(saveFile.getParentFile());
          // Call appropiate model load function
        } catch (NullPointerException npe) {
          logger.log(Level.FINE, "File Chooser did not choose a file");
        }
      }
    }

    // This should be placed inside of the model save function, but is here for
    // testing
    modelState.setNeverBeenSaved(false);
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
    // Set initial file path
    try {
      // If there's a present working directory, open up to that directory
      if (modelState.getPresentWorkingDirectory() == null) {
        fileChooser.setInitialDirectory(new File(AppSettings.getInstance().getDefaultRocketPath()));
      } else {
        fileChooser.setInitialDirectory(modelState.getPresentWorkingDirectory());
      }
      saveFile = fileChooser.showOpenDialog(null);
    } catch (Exception ex) {
      logger.warning("Invalid initial directory path");
      fileChooser.setInitialDirectory(null);
      saveFile = fileChooser.showSaveDialog(null);
    }

    try {
      // Set the new present working directory to the save file's directory
      modelState.setPresentWorkingDirectory(saveFile.getParentFile());
      // Call appropiate model load function
    } catch (NullPointerException npe) {
      logger.log(Level.FINE, "File Chooser did not choose a file");
    }
    // This should be placed inside of the model save function, but is here for
    // testing
    modelState.setNeverBeenSaved(false);
  }

  /**
   * File->Quit
   *
   * @param event
   */
  @FXML
  void fileQuit() {
    // ???
  }

  /**
   * Help->About
   *
   * @param event
   */
  @FXML
  void helpAbout() {

  }
}
