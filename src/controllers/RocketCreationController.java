package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

/**
 * Controller for the rocket creation view
 * 
 * @author Jacob, Brian
 */
public class RocketCreationController {
    @FXML
    private TreeView<?> partList;
    
    @FXML
    private AnchorPane partViewer;

    @FXML
    void addPart(ActionEvent event) {

    }
    
    public void initialize() {
      try {
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("/views/parts/Fin.fxml"));
        partViewer.getChildren().add(root);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
}
