package controllers;

import java.io.IOException;
import java.net.URL;
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
  private final class RocketPartTreeItem extends TreeItem<RocketPart> {
    private String name;
    
    public RocketPartTreeItem(String name) {
      this.name = name;
      System.out.println("Rocket tree part item Constructor");
    }
    
    @Override
    public String toString() {
      System.out.println("Rocket tree part item toString()");
      return name;
    }
  }

  /**
   *
   */
   public enum RocketPart {

    /**
     *
     */
    CircularCylinder,

    /**
     *
     */
    ConicalFrustum,

    /**
     *
     */
    TrapezoidFinSet,

    /**
     *
     */
    Motor,

    /**
     *
     */
    NoseCone,

    /**
     *
     */
    Parachute
  };

  private TreeItem<RocketPart> treeViewRoot = new TreeItem<RocketPart>();
  
  private RocketPartTreeItem internalTreePartsRoot = new RocketPartTreeItem("Internal");
  private RocketPartTreeItem externalTreePartsRoot = new RocketPartTreeItem("External");
  
//  private TreeItem<RocketPart> internalTreePartsRoot = new TreeItem<RocketPart>();
//  private TreeItem<RocketPart> externalTreePartsRoot = new TreeItem<RocketPart>();
  
  private Map<RocketPart, URL> itemURL = new HashMap<RocketPart, URL>();
  private Map<RocketPart, Parent> itemParent = new HashMap<RocketPart, Parent>();

  @FXML
  private TreeView<RocketPart> partList;

  @FXML
  private AnchorPane partViewer;

  /**
   * Open PartChooser dialog window and add chosen part to tree view
   * 
   * @param event
   * @throws IOException 
   */
  @FXML
  void addPart(ActionEvent event) throws IOException {
    PartChooser partChooser = new PartChooser();
    RocketPart part = partChooser.showPartDialog(partViewer.getScene().getWindow());
    if (part != null) {
      treeViewRoot.getChildren().add(new TreeItem<RocketPart>(part));
      itemParent.put(part, (Parent) FXMLLoader.load(itemURL.get(part)));
    }
  }

  private ChangeListener<TreeItem<RocketPart>> selectionEvent = new ChangeListener<TreeItem<RocketPart>>() {
    @Override
    public void changed(ObservableValue<? extends TreeItem<RocketPart>> arg0, TreeItem<RocketPart> arg1, TreeItem<RocketPart> arg2) {
      partViewer.getChildren().clear();
      if (itemURL.get(arg2.getValue()) != null) {
        partViewer.getChildren().add(itemParent.get(arg2.getValue()));
      }
    }
  };
  
  /**
   * Setup a map from RocketParts to the editor for each part.
   * 
   * @throws IOException
   */
  private void setMaps() throws IOException {
    itemURL.put(RocketPart.CircularCylinder, getClass().getResource("/views/parts/CircularCylinder.fxml"));
    itemURL.put(RocketPart.ConicalFrustum,   getClass().getResource("/views/parts/ConicalFrustum.fxml"));
    itemURL.put(RocketPart.Motor,            getClass().getResource("/views/parts/Motor.fxml"));
    itemURL.put(RocketPart.NoseCone,         getClass().getResource("/views/parts/NoseCone.fxml"));
    itemURL.put(RocketPart.Parachute,        getClass().getResource("/views/parts/Parachute.fxml"));
    itemURL.put(RocketPart.TrapezoidFinSet,  getClass().getResource("/views/parts/TrapezoidFinSet.fxml"));
  }

  /**
   * Set tree view, listeners and maps
   * 
   * @throws IOException
   */
  public void initialize() throws IOException {
    setMaps();
    
    treeViewRoot.setExpanded(true);
    treeViewRoot.getChildren().add(internalTreePartsRoot);
    treeViewRoot.getChildren().add(externalTreePartsRoot);
    
    partList.getSelectionModel().selectedItemProperty().addListener(selectionEvent);
    partList.setRoot(treeViewRoot);
    partList.setShowRoot(false);
  }
}
