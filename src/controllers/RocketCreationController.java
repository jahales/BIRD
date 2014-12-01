package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controllers.parts.PartChooser;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import models.rocket.Rocket;
import models.rocket.parts.CircularCylinder;
import models.rocket.parts.ConicalFrustum;
import models.rocket.parts.Motor;
import models.rocket.parts.NoseCone;
import models.rocket.parts.Parachute;
import models.rocket.parts.RocketComponent;
import models.rocket.parts.TrapezoidFinSet;

/**
 * Controller for the rocket creation view
 *
 * @author Jacob, Brian
 */
public class RocketCreationController extends BaseController {

  /**
   * Tree Component
   *
   * @author Brian Woodruff
   *
   */
  private abstract class TreeComponent extends TreeItem<String> {

    /**
     * Used to set string variable for TreeItem<String>
     *
     * @param name a string that will be displayed in the tree
     */
    public TreeComponent(String name) {
      super(name);
    }

    /**
     * Usually left alone. But can be overloaded to say, set the view to a Parent of
     * {@link RocketPart}.
     *
     * @param pane an AchorPane to set the view
     */
    public void setPaneNode(AnchorPane pane) {
    }
  }

  /**
   * Individual Rocket Component that extends {@link TreeComponent}.
   * <p>
   * Overloads setPaneNode to set the view to Parent of {@link RocketPart}
   *
   * @author Brian Woodruff
   *
   */
  private class IndividualRocketComponent extends TreeComponent {

    private Parent view;

    /**
     * Set name and load view
     *
     * @param part a {@link RocketPart} to be stored in this {@link TreeComponent}
     */
    public IndividualRocketComponent(String url, RocketComponent component) {
      super(component.getName());
      view = loadComponentView(url, component);
    }

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
  private class InternalComponents extends TreeComponent {

    /**
     * Sets label text to 'Internal Components'
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
  private class ExternalComponents extends TreeComponent {

    /**
     * Sets label text to 'External Components'
     */
    public ExternalComponents() {
      super("External Components");
    }
  }

  /**
   * Generic rocket part interface
   *
   * @author Brian Woodruff
   *
   */
  public interface RocketPart {

    /**
     * @return a string of the value
     */
    String getValue();
  }

  /**
   * Internal rocket parts
   *
   * @author Brian Woodruff
   *
   */
  public enum InternalRocketPart implements RocketPart {

    CIRCULAR_CYLINDER, MOTOR, PARACHUTE;

    @Override
    public String getValue() {
      return toString();
    }
  }

  /**
   * External rocket parts
   *
   * @author Brian Woodruff
   *
   */
  public enum ExternalRocketPart implements RocketPart {

    CIRCULAR_CYLINDER, CONICAL_FRUSTUM, TRAPEZOID_FIN_SET, NOSE_CONE;

    @Override
    public String getValue() {
      return toString();
    }
  }

  /**
   * Tree cell to convert name to more friendly format
   *
   * @author Brian Woodruff
   *
   */
  private class MyTreeCell extends TreeCell<String> {

    @Override
    protected void updateItem(String item, boolean empty) {
      super.updateItem(item, empty);
      if (!empty) {
        setText(toCamelCase(item));
      }
    }

    /**
     * EXAMPLE_ONE to Example One
     *
     * @param string a string to be formatted from enum style to more human readable format
     * @return camel case string
     */
    private String toCamelCase(String string) {
      String[] parts = string.split("_");
      String camelCaseString = "";
      for (String part : parts) {
        camelCaseString += " " + toProperCase(part);
      }
      return camelCaseString;
    }

    /**
     * ExAmPLE to Example
     *
     * @param string a string to be formated
     * @return a string formated in proper case
     */
    private String toProperCase(String string) {
      return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }
  }

  @FXML
  private TreeView<String> partList;

  @FXML
  private AnchorPane partViewer;

  Rocket rocket;

  private TreeComponent internalTreePartsRoot = new InternalComponents();
  private TreeComponent externalTreePartsRoot = new ExternalComponents();

  private List<RocketPart> internalParts = FXCollections.observableArrayList(InternalRocketPart
      .values());
  private List<RocketPart> externalParts = FXCollections.observableArrayList(ExternalRocketPart
      .values());

  private Map<RocketPart, String> itemURL = new HashMap<>();
  private Map<RocketPart, Class<?>> itemType = new HashMap<>();

  private ChangeListener<TreeItem<String>> selectionEvent = new ChangeListener<TreeItem<String>>() {
    @Override
    public void changed(ObservableValue<? extends TreeItem<String>> reserved, TreeItem<String> old,
        TreeItem<String> current) {
      if (current instanceof TreeComponent) {
        ((TreeComponent) current).setPaneNode(partViewer);
      }
    }
  };

  /**
   * @param rocket a rocket that will be modified in this view.
   */
  public RocketCreationController(Rocket rocket) {
    this.rocket = rocket;
  }

  /**
   * Opens a {@link PartChooser} dialog window. Passes a list of internal and external parts to
   * choose from.
   * <p>
   * If window returns a part, it is added to tree view.
   */
  @FXML
  void addPart() throws Exception {
    // Allow the user to select a rocket part to add
    PartChooser partChooser = new PartChooser();
    RocketPart part = partChooser.showPartDialog(internalParts, externalParts, partViewer
        .getScene().getWindow());

    if (part == null) {
      return;
    }

    // Add the part to the rocket and component tree
    RocketComponent component = (RocketComponent) itemType.get(part).newInstance();
    String url = itemURL.get(part);
    component.setName(part.toString());

    if (part instanceof InternalRocketPart) {
      rocket.getInteriorComponents().add(component);

      internalTreePartsRoot.getChildren().add(new IndividualRocketComponent(url, component));
      internalTreePartsRoot.setExpanded(true);
      if (part == InternalRocketPart.MOTOR) {
        internalParts.remove(part);
      }
    } else if (part instanceof ExternalRocketPart) {
      rocket.getExteriorComponents().add(component);

      externalTreePartsRoot.getChildren().add(new IndividualRocketComponent(url, component));
      externalTreePartsRoot.setExpanded(true);
      if (part == ExternalRocketPart.NOSE_CONE || part == ExternalRocketPart.TRAPEZOID_FIN_SET) {
        externalParts.remove(part);
      }
    }
  }

  /**
   * Sets up the view for the Rocket Creation View. Tree View is populated with two roots: internal
   * and external.
   */
  public void initialize() {
    setMaps();

    partList.setCellFactory((event) -> {
      return new MyTreeCell();
    });

    TreeItem<String> treeViewRoot = new TreeItem<String>();

    treeViewRoot.setExpanded(true);
    treeViewRoot.getChildren().add(internalTreePartsRoot);
    treeViewRoot.getChildren().add(externalTreePartsRoot);

    partList.getSelectionModel().selectedItemProperty().addListener(selectionEvent);
    partList.setRoot(treeViewRoot);
    partList.setShowRoot(false);
  }

  /**
   * @param url location of component
   * @return the Parent from the url
   */
  private Parent loadComponentView(String url, RocketComponent component) {
    try {
      ControllerFactory controllerFactory = new ControllerFactory();
      IController controller = controllerFactory.load(url, new Object[]{component});
      return (Parent) controller.getView();
    } catch (IOException ex) {
      Logger.getLogger(RocketCreationController.class.getName()).log(Level.SEVERE, null, ex);
      return null;
    }
  }

  /**
   * Setup a map from RocketParts to the editor for each part.
   */
  private void setMaps() {
    itemURL.put(InternalRocketPart.CIRCULAR_CYLINDER, "/views/parts/CircularCylinder.fxml");
    itemURL.put(InternalRocketPart.MOTOR, "/views/parts/Motor.fxml");
    itemURL.put(InternalRocketPart.PARACHUTE, "/views/parts/Parachute.fxml");

    itemType.put(InternalRocketPart.CIRCULAR_CYLINDER, CircularCylinder.class);
    itemType.put(InternalRocketPart.MOTOR, Motor.class);
    itemType.put(InternalRocketPart.PARACHUTE, Parachute.class);

    itemURL.put(ExternalRocketPart.CIRCULAR_CYLINDER, "/views/parts/CircularCylinder.fxml");
    itemURL.put(ExternalRocketPart.CONICAL_FRUSTUM, "/views/parts/ConicalFrustum.fxml");
    itemURL.put(ExternalRocketPart.NOSE_CONE, "/views/parts/NoseCone.fxml");
    itemURL.put(ExternalRocketPart.TRAPEZOID_FIN_SET, "/views/parts/TrapezoidFinSet.fxml");

    itemType.put(ExternalRocketPart.CIRCULAR_CYLINDER, CircularCylinder.class);
    itemType.put(ExternalRocketPart.CONICAL_FRUSTUM, ConicalFrustum.class);
    itemType.put(ExternalRocketPart.NOSE_CONE, NoseCone.class);
    itemType.put(ExternalRocketPart.TRAPEZOID_FIN_SET, TrapezoidFinSet.class);
  }
}
