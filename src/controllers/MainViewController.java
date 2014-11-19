package controllers;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.AppSettings;

/**
 * Main view of our program.
 * 3 Tabs are included.
 * This controller focuses on menu items.
 * 
 * @author Brian Woodruff
 *
 */
public class MainViewController {

    /**
     * File->New
     * 
     * @param event
     */
    @FXML
    void fileNew(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        //Set initial file path
        fileChooser.setInitialDirectory(new File(
                System.getProperty(AppSettings.getInstance().getDefaultRocketPath())));
        
        fileChooser.showOpenDialog(new Stage());
    }

    /**
     * File->Open
     * 
     * @param event
     */
    @FXML
    void fileOpen(ActionEvent event) {

    }

    /**
     * File->Save
     * 
     * @param event
     */
    @FXML
    void fileSave(ActionEvent event) {

    }

    /**
     * File->SaveAs...
     * 
     * @param event
     */
    @FXML
    void fileSaveAs(ActionEvent event) {

    }

    /**
     * File->Quit
     * 
     * @param event
     */
    @FXML
    void fileQuit(ActionEvent event) {

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
