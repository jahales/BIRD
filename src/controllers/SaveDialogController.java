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
public class SaveDialogController extends BaseController implements Initializable {

  private boolean doSave = false;
  private boolean doContinue = false;

  @FXML
  private javafx.scene.layout.Pane saveDialogRoot;

  public boolean isDoSave() {
    return this.doSave;
  }

  public boolean isDoContinue() {
    return this.doContinue;
  }

  @FXML
  void save() {
    this.doSave = true;
    this.doContinue = true;
    Stage stage = (Stage) this.saveDialogRoot.getScene().getWindow();
    stage.close();
  }

  @FXML
  void dontSave() {
    this.doSave = false;
    this.doContinue = true;
    Stage stage = (Stage) this.saveDialogRoot.getScene().getWindow();
    stage.close();
  }

  @FXML
  void cancel() {
    this.doSave = false;
    this.doContinue = false;
    Stage stage = (Stage) this.saveDialogRoot.getScene().getWindow();
    stage.close();
  }

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
  }

}
