package controllers;

import java.io.IOException;
import java.net.URL;
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
import models.MainViewModel;
import models.rocket.Rocket;
import views.ViewFactory;

/**
 * Controller for the rocket creation view
 *
 * @author Jacob, Brian
 */
public class RocketCreationController {

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
  }

//  private final class RocketPartTreeItem extends TreeItem<RocketPart> {
//    private String name;
//    
//    public RocketPartTreeItem(String name) {
//      this.name = name;
//    }
//    
//    @Override
//    public String toString() {
//      return name;
//    }
//  };
  Rocket rocket;

  private TreeItem<RocketPart> treeViewRoot = new TreeItem<RocketPart>();

//  private RocketPartTreeItem internalTreePartsRoot = new RocketPartTreeItem("Internal");
//  private RocketPartTreeItem externalTreePartsRoot = new RocketPartTreeItem("External");
  private Map<RocketPart, String> itemURL = new HashMap<>();
  private Map<RocketPart, Parent> itemParent = new HashMap<RocketPart, Parent>();

  @FXML
  private TreeView<RocketPart> partList;

  @FXML
  private AnchorPane partViewer;

  private ChangeListener<TreeItem<RocketPart>> selectionEvent = new ChangeListener<TreeItem<RocketPart>>() {
    @Override
    public void changed(ObservableValue<? extends TreeItem<RocketPart>> arg0, TreeItem<RocketPart> arg1, TreeItem<RocketPart> arg2) {
      partViewer.getChildren().clear();
      if (arg2.getValue() != null) {
        if (itemURL.get(arg2.getValue()) != null) {

          partViewer.getChildren().add(itemParent.get(arg2.getValue()));
        }
      }
    }
  };

  /**
   *
   */
  public RocketCreationController() {
  }

  /**
   *
   * @param mainViewModel
   */
  public RocketCreationController(Rocket rocket) {
    this.rocket = rocket;
  }

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
      if (itemURL.get(part) != null) {
        ViewFactory factory = new ViewFactory();
        treeViewRoot.getChildren().add(new TreeItem<RocketPart>(part));
        Parent parent = (Parent) factory.create(itemURL.get(part), new Object[]{rocket});
        itemParent.put(part, parent);
      }
    }
  }

  /**
   * Set tree view, listeners and maps
   *
   * @throws IOException
   */
  public void initialize() throws IOException {
    setMaps();

    treeViewRoot.setExpanded(true);
//    treeViewRoot.getChildren().add(internalTreePartsRoot);
//    treeViewRoot.getChildren().add(externalTreePartsRoot);

    partList.getSelectionModel().selectedItemProperty().addListener(selectionEvent);
    partList.setRoot(treeViewRoot);
    partList.setShowRoot(false);
  }

  /**
   * @param url
   * @return view
   */
  private Parent loadComponentView(String url) {
    try {
      ViewFactory viewFactory = new ViewFactory();
      Object view = viewFactory.create(url, new Object[]{rocket});
      return (Parent) view;
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
    itemURL.put(RocketPart.CircularCylinder, "/views/parts/CircularCylinder.fxml");
    itemURL.put(RocketPart.ConicalFrustum, "/views/parts/ConicalFrustum.fxml");
    itemURL.put(RocketPart.Motor, "/views/parts/Motor.fxml");
    itemURL.put(RocketPart.NoseCone, "/views/parts/NoseCone.fxml");
    itemURL.put(RocketPart.Parachute, "/views/parts/Parachute.fxml");
    itemURL.put(RocketPart.TrapezoidFinSet, "/views/parts/TrapezoidFinSet.fxml");
  }
}
