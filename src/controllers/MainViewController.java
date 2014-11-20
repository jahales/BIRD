package controllers;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.AppSettings;
import models.AppState;
import models.rocket.Rocket;

/**
 * Main view of our program. 3 Tabs are included. This controller focuses on
 * menu items.
 *
 * @author Brian Woodruff
 *
 */
public class MainViewController {

    final static Logger logger = Logger.getLogger(MainViewController.class.getName());
    AppState appState;
        
    public MainViewController(AppState appState)
    {
      this.appState = appState;
    }
    
    /**
     * File->New
     *
     * @param event
     */
    @FXML
    void fileNew(ActionEvent event) {
        if (appState.isUnsaved()) {
            //prompt user if he wants to save or not
        }
        // Create a new rocket and begin work
    }

    /**
     * File->Open
     *
     * @param event
     */
    @FXML
    void fileOpen(ActionEvent event) {
        File openFile;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        //Set initial file path
        try {
            //If there's a present working directory, open up to that directory
            if (appState.getPresentWorkingDirectory() == null) {
                fileChooser.setInitialDirectory(
                        new File(AppSettings.getInstance().getDefaultRocketPath()));
            } else {
                fileChooser.setInitialDirectory(appState.getPresentWorkingDirectory());
            }
            openFile = fileChooser.showOpenDialog(null); //How do I access the view's window?
        } catch (Exception ex) {
            logger.warning("Invalid initial directory path");
            fileChooser.setInitialDirectory(null);
            openFile = fileChooser.showOpenDialog(null);
        }

        try {
            //Set the new present working directory to the save file's directory
            appState.setPresentWorkingDirectory(openFile.getParentFile());
            //Call appropiate model load function
        } catch (NullPointerException npe) {
            logger.log(Level.FINE, "File Chooser did not choose a file");
        }

        //This should be placed inside of the model save function, but is here for testing
        appState.setNeverBeenSaved(false);
        appState.setUnsaved(false);
    }

    /**
     * File->Save
     *
     * @param event
     */
    @FXML
    void fileSave(ActionEvent event) {
        File saveFile;
        if (appState.hasNeverBeenSaved()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            //Set initial file path
            try {
                //If there's a present working directory, open up to that directory
                if (appState.getPresentWorkingDirectory() == null) {
                    fileChooser.setInitialDirectory(
                            new File(AppSettings.getInstance().getDefaultRocketPath()));
                } else {
                    fileChooser.setInitialDirectory(appState.getPresentWorkingDirectory());
                }
                saveFile = fileChooser.showOpenDialog(null);
            } catch (Exception ex) {
                logger.warning("Invalid initial directory path");
                fileChooser.setInitialDirectory(null);
                saveFile = fileChooser.showSaveDialog(null);
            }

            try {
                //Set the new present working directory to the save file's directory
                appState.setPresentWorkingDirectory(saveFile.getParentFile());
                //Call appropiate model load function
            } catch (NullPointerException npe) {
                logger.log(Level.FINE, "File Chooser did not choose a file");
            }
        } else {
            if (appState.isUnsaved()) {
                saveFile = appState.getPresentWorkingFile();

                try {
                    //Set the new present working directory to the save file's directory
                    appState.setPresentWorkingDirectory(saveFile.getParentFile());
                    //Call appropiate model load function
                } catch (NullPointerException npe) {
                    logger.log(Level.FINE, "File Chooser did not choose a file");
                }
            }
        }

        //This should be placed inside of the model save function, but is here for testing
        appState.setNeverBeenSaved(false);
    }

    /**
     * File->SaveAs...
     *
     * @param event
     */
    @FXML
    void fileSaveAs(ActionEvent event) {
        File saveFile;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        //Set initial file path
        try {
            //If there's a present working directory, open up to that directory
            if (appState.getPresentWorkingDirectory() == null) {
                fileChooser.setInitialDirectory(
                        new File(AppSettings.getInstance().getDefaultRocketPath()));
            } else {
                fileChooser.setInitialDirectory(appState.getPresentWorkingDirectory());
            }
            saveFile = fileChooser.showOpenDialog(null);
        } catch (Exception ex) {
            logger.warning("Invalid initial directory path");
            fileChooser.setInitialDirectory(null);
            saveFile = fileChooser.showSaveDialog(null);
        }

        try {
            //Set the new present working directory to the save file's directory
            appState.setPresentWorkingDirectory(saveFile.getParentFile());
            //Call appropiate model load function
        } catch (NullPointerException npe) {
            logger.log(Level.FINE, "File Chooser did not choose a file");
        }
        //This should be placed inside of the model save function, but is here for testing
        appState.setNeverBeenSaved(false);
    }

    /**
     * File->Quit
     *
     * @param event
     */
    @FXML
    void fileQuit(ActionEvent event) {
        //???
    }

    /**
     * Help->About
     *
     * @param event
     */
    @FXML
    void helpAbout(ActionEvent event) {

    }
}
