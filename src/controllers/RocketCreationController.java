package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import controllers.parts.PartChooser;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

/**
 * Controller for the rocket creation view
 * 
 * @author Jacob, Brian
 */
public class RocketCreationController {
  public enum RocketPart {
    CircularCylinder, ConicalFrustum, TrapezoidFinSet, Motor, NoseCone, Parachute
  };
  
  private TreeItem<RocketPart> treeViewRoot;
  private Map<RocketPart, String> itemFilePath = new HashMap<RocketPart, String>();
  private Map<RocketPart, Parent> itemParent = new HashMap<RocketPart, Parent>();

  @FXML
  private TreeView<RocketPart> partList;

  @FXML
  private AnchorPane partViewer;

  @FXML
  void addPart(ActionEvent event) {
    PartChooser partChooser = new PartChooser();
    RocketPart part = partChooser.showPartDialog(partViewer.getScene().getWindow());
    TreeItem<RocketPart> newPart = new TreeItem<RocketPart>(part);
    treeViewRoot.getChildren().add(newPart);
  }

  private ChangeListener<TreeItem<RocketPart>> selectionEvent = new ChangeListener<TreeItem<RocketPart>>() {
    @Override
    public void changed(ObservableValue<? extends TreeItem<RocketPart>> arg0, TreeItem<RocketPart> arg1, TreeItem<RocketPart> arg2) {
      partViewer.getChildren().clear();
      if (itemParent.get(arg2.getValue()) != null) {
        partViewer.getChildren().add(itemParent.get(arg2.getValue()));
      }
    }
  };
  
  private void setMaps() throws IOException {
    itemFilePath.put(RocketPart.CircularCylinder, "/views/parts/CircularCylinder.fxml");
    itemFilePath.put(RocketPart.ConicalFrustum,   "/views/parts/ConicalFrustum.fxml");
    itemFilePath.put(RocketPart.Motor,            "/views/parts/Motor.fxml");
    itemFilePath.put(RocketPart.NoseCone,         "/views/parts/NoseCone.fxml");
    itemFilePath.put(RocketPart.Parachute,        "/views/parts/Parachute.fxml");
    itemFilePath.put(RocketPart.TrapezoidFinSet,  "/views/parts/TrapezoidFinSet.fxml");
    
    Parent circularCylinder = (Parent) FXMLLoader.load(getClass().getResource(itemFilePath.get(RocketPart.CircularCylinder))); // Good
    Parent conicalFrustum   = (Parent) FXMLLoader.load(getClass().getResource(itemFilePath.get(RocketPart.ConicalFrustum))); // Bad
    Parent motor            = (Parent) FXMLLoader.load(getClass().getResource(itemFilePath.get(RocketPart.Motor))); // Good
    Parent noseCone         = (Parent) FXMLLoader.load(getClass().getResource(itemFilePath.get(RocketPart.NoseCone))); // Bad
    Parent parachute        = (Parent) FXMLLoader.load(getClass().getResource(itemFilePath.get(RocketPart.Parachute))); // Bad
    Parent trapezoidFinSet  = (Parent) FXMLLoader.load(getClass().getResource(itemFilePath.get(RocketPart.TrapezoidFinSet))); // Good

    itemParent.put(RocketPart.CircularCylinder, circularCylinder);
    itemParent.put(RocketPart.ConicalFrustum, conicalFrustum);
    itemParent.put(RocketPart.Motor, motor);
    itemParent.put(RocketPart.NoseCone, noseCone);
    itemParent.put(RocketPart.Parachute, parachute);
    itemParent.put(RocketPart.TrapezoidFinSet, trapezoidFinSet);
  }

  public void initialize() throws IOException {
    
    setMaps();
    partViewer.getChildren().add(itemParent.get(RocketPart.CircularCylinder));
    partViewer.getChildren().clear();
    partViewer.getChildren().add(itemParent.get(RocketPart.Motor));
    partViewer.getChildren().clear();
    partViewer.getChildren().add(itemParent.get(RocketPart.TrapezoidFinSet));
    
    treeViewRoot = new TreeItem<RocketPart>();
    treeViewRoot.setExpanded(true);
    partList.getSelectionModel().selectedItemProperty().addListener(selectionEvent);
    partList.setRoot(treeViewRoot);
    partList.setShowRoot(false);
  }
}
