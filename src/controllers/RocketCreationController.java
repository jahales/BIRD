package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import controllers.parts.PartChooser;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import models.AppState;

/**
 * Controller for the rocket creation view
 *
 * @author Jacob, Brian
 */
public class RocketCreationController {

  AppState appState;
  
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
  
  public RocketCreationController()
  {
  }
  
  public RocketCreationController(AppState appState)
    {
      this.appState = appState;
    }

  private TreeItem<RocketPart> treeViewRoot;

  private Map<RocketPart, Parent> itemParent = new HashMap<RocketPart, Parent>();

  @FXML
  private TreeView<RocketPart> partList;

  @FXML
  private AnchorPane partViewer;

  /**
   * Open PartChooser dialog window and add chosen part to tree view
   *
   * @param event
   */
  @FXML
  void addPart(ActionEvent event) {
    PartChooser partChooser = new PartChooser();
    RocketPart part = partChooser.showPartDialog(partViewer.getScene().getWindow());
    TreeItem<RocketPart> newPart = new TreeItem<RocketPart>(part);
    if (newPart != null) {
      treeViewRoot.getChildren().add(newPart);
    }
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

  private Parent loadComponentView(String url)  {
    try {
      ControllerFactory factory = new ControllerFactory();
      factory.addSingleton(appState);
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource(url));
      loader.setControllerFactory(factory);
      return loader.load();
    } catch (IOException ex) {
      Logger.getLogger(RocketCreationController.class.getName()).log(Level.SEVERE, null, ex);
      return null;
    }
  }

  /**
   * Setup a map from RocketParts to the editor for each part.
   *
   * @throws IOException
   */
  private void setMaps() throws IOException {
    itemParent.put(RocketPart.CircularCylinder, loadComponentView("/views/parts/CircularCylinder.fxml"));
    itemParent.put(RocketPart.ConicalFrustum, loadComponentView("/views/parts/ConicalFrustum.fxml"));
    itemParent.put(RocketPart.Motor, loadComponentView("/views/parts/Motor.fxml"));
    itemParent.put(RocketPart.NoseCone, loadComponentView("/views/parts/NoseCone.fxml"));
    itemParent.put(RocketPart.Parachute, loadComponentView("/views/parts/Parachute.fxml"));
    itemParent.put(RocketPart.TrapezoidFinSet, loadComponentView("/views/parts/TrapezoidFinSet.fxml"));
  }

  /**
   * Set tree view, listeners and maps
   *
   * @throws IOException
   */
  public void initialize() throws IOException {
    setMaps();

    treeViewRoot = new TreeItem<RocketPart>();
    treeViewRoot.setExpanded(true);
    partList.getSelectionModel().selectedItemProperty().addListener(selectionEvent);
    partList.setRoot(treeViewRoot);
    partList.setShowRoot(false);
  }
}
