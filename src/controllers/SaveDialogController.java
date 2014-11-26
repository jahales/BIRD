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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Joseph
 */
public class SaveDialogController implements Initializable {

  private boolean doSave = false;
  private boolean doContinue = false;

  @FXML
  private javafx.scene.layout.Pane saveDialogRoot;

  public boolean isDoSave() {
    return doSave;
  }

  public boolean isDoContinue() {
    return doContinue;
  }

  @FXML
  void save() {
    doSave = true;
    doContinue = true;
    Stage stage = (Stage) saveDialogRoot.getScene().getWindow();
    stage.close();
  }

  @FXML
  void dontSave() {
    doSave = false;
    doContinue = true;
    Stage stage = (Stage) saveDialogRoot.getScene().getWindow();
    stage.close();
  }

  @FXML
  void cancel() {
    doSave = false;
    doContinue = false;
    Stage stage = (Stage) saveDialogRoot.getScene().getWindow();
    stage.close();
  }

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
  }

}
