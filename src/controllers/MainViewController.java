package controllers;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.FileHelper;
import models.MainViewModel;
import models.rocket.Rocket;

/**
 * Main view of our program. 3 Tabs are included. This controller focuses on menu items.
 *
 * @author Brian Woodruff, Joseph Hales
 *
 */
public class MainViewController extends BaseController {

  final static Logger logger = Logger.getLogger(MainViewController.class.getName());
  MainViewModel mainViewModel;

  @FXML
  private javafx.scene.layout.Pane root;

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
  void fileNewRocket() {
    // Spawn new instance with mostly empty data
    FileHelper.spawnNewInstance(new Rocket(), this.mainViewModel);
  }

  @FXML
  void fileOpenReport() {
    FileHelper.openCSV(mainViewModel, root);
  }

  /**
   * File->Open ...
   *
   * @param event
   */
  @FXML
  void fileOpenRocket() {
    FileHelper.openRocket(this.mainViewModel, this.root);
  }

  /**
   * File->Save
   *
   * @param event
   */
  @FXML
  void fileSave() {
    FileHelper.saveRocket(this.mainViewModel, this.root);
  }

  /**
   * File->SaveAs...
   *
   * @param event
   */
  @FXML
  void fileSaveAs() {
    FileHelper.RocketSaveAs(this.mainViewModel, this.root);
  }

  /**
   * File->Quit
   *
   * @param event
   */
  @FXML
  void fileQuit() {
    if (mainViewModel.isUnsaved()) {
      if (promptSave()) {
        Stage stage = (Stage) this.root.getScene().getWindow();
        stage.close();
      }
    } else {
      Stage stage = (Stage) this.root.getScene().getWindow();
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

  private boolean promptSave() {
    try {
      // Create the view and controller
      ControllerFactory controllerFactory = new ControllerFactory();
      SaveDialogController controller = (SaveDialogController) controllerFactory
        .create("/views/SaveDialog.fxml");

      // Setup the stage
      Scene scene = new Scene((Parent) controller.getView());
      Stage stage = new Stage();
      stage.setScene(scene);
      stage.initOwner(this.root.getScene().getWindow());
      stage.setTitle("Do you wish to save?");
      stage.showAndWait();

      // get results from the controller
      if (controller.isDoSave()) {
        fileSave();
      }
      return controller.isDoContinue();

    } catch (Exception ex) {
      logger.log(Level.WARNING, "Failed to show save dialog.", ex);
      return false;
    }
  }
}
