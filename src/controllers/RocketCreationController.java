package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import controllers.parts.PartChooser;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import models.rocket.Rocket;
import views.ViewFactory;

/**
 * Controller for the rocket creation view
 *
 * @author Jacob, Brian
 */
public class RocketCreationController {

  /**
   * Tree Component
   * 
   * @author Brian Woodruff
   *
   */
  abstract class TreeComponent extends TreeItem<String> {
    /**
     * Used to set string variable for TreeItem<String>
     * 
     * @param name
     */
    public TreeComponent(String name) {
      super(name);
    }

    /**
     * Usually left alone for non-individual pieces
     * 
     * @param pane
     */
    public void setPaneNode(AnchorPane pane) {
    }
  }

  /**
   * Individual Rocket Component
   * 
   * @author Brian Woodruff
   *
   */
  class IndividualRocketComponent extends TreeComponent {
    private Parent view;

    /**
     * Set name and load view
     * 
     * @param part
     */
    public IndividualRocketComponent(RocketPart part) {
      super(part.toString());
      view = loadComponentView(itemURL.get(part));
    }

    /**
     * Set pane view to this view
     * 
     * @param pane
     */
    @Override
    public void setPaneNode(AnchorPane pane) {
      pane.getChildren().clear();
      pane.getChildren().add(view);
    }
  }

  /**
   * Internal Components
   * 
   * @author Brian Woodruff
   *
   */
  class InternalComponents extends TreeComponent {
    /**
     * Set label 'Internal Components'
     */
    public InternalComponents() {
      super("Internal Components");
    }
  }

  /**
   * External Components
   * 
   * @author Brian Woodruff
   *
   */
  class ExternalComponents extends TreeComponent {
    /**
     * Set label 'External Components'
     */
    public ExternalComponents() {
      super("External Components");
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
  }

  Rocket rocket;

  private TreeItem<String> treeViewRoot = new TreeItem<String>();

  private TreeComponent internalTreePartsRoot = new InternalComponents();
  private TreeComponent externalTreePartsRoot = new ExternalComponents();

  private Map<RocketPart, String> itemURL = new HashMap<>();

  @FXML
  private TreeView<String> partList;

  @FXML
  private AnchorPane partViewer;

  private ChangeListener<TreeItem<String>> selectionEvent = new ChangeListener<TreeItem<String>>() {
    @Override
    public void changed(ObservableValue<? extends TreeItem<String>> arg0, TreeItem<String> arg1,
        TreeItem<String> arg2) {
      if (arg2 instanceof TreeComponent) {
        ((TreeComponent) arg2).setPaneNode(partViewer);
      }
    }
  };

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
  void addPart() throws IOException {
    PartChooser partChooser = new PartChooser();
    RocketPart part = partChooser.showPartDialog(partViewer.getScene().getWindow());
    if (part != null) {
      internalTreePartsRoot.getChildren().add(new IndividualRocketComponent(part));
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
    treeViewRoot.getChildren().add(internalTreePartsRoot);
    treeViewRoot.getChildren().add(externalTreePartsRoot);

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
      Object view = viewFactory.create(url, new Object[] { rocket });
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
