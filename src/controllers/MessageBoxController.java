/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Joseph
 */
public class MessageBoxController extends BaseController {
  @FXML
  private Label lblMessage;
  
  @FXML
  void btnOK() {
      ((Stage)lblMessage.getScene().getWindow()).close();
  }

  static public void showMessage(String message, Node owner) {
    try {
    ControllerFactory controllerFactory = new ControllerFactory();
    MessageBoxController controller = (MessageBoxController) controllerFactory
      .create("/views/MessageBox.fxml");

    //Setup the stage
    controller.lblMessage.setText(message);
    Scene scene = new Scene((Parent) controller.getView());
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle(message);
    stage.initOwner(owner.getScene().getWindow());
    stage.showAndWait();
    } catch (Exception ex){
      ex.printStackTrace();
    }
  }
}
