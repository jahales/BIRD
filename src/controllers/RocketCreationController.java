package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import models.rocket.Rocket;
import models.rocket.parts.CircularCylinder;
import models.rocket.parts.ConicalFrustum;
import models.rocket.parts.Motor;
import models.rocket.parts.NoseCone;
import models.rocket.parts.Parachute;
import models.rocket.parts.RocketComponent;
import models.rocket.parts.TrapezoidFinSet;
import controllers.parts.PartChooser;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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
  private abstract class RocketComponentTreeItem extends TreeItem<String> {

    /**
     * Used to set string variable for TreeItem<String>
     *
     * @param name
     *          a string that will be displayed in the tree
     */
    public RocketComponentTreeItem(String name) {
      super(name);
    }

    /**
     * Usually left alone. But can be overloaded to say, set the view to a
     * Parent of {@link RocketPart}.
     *
     * @param pane
     *          an AchorPane to set the view
     */
    public void setPaneNode(Pane pane) {
    }

    /**
     *
     * @param component
     */
    public void updateSelectedComponent() {
    }
  }

  /**
   * Individual Rocket Component that extends {@link RocketComponentTreeItem}.
   * <p>
   * Overloads setPaneNode to set the view to Parent of {@link RocketPart}
   *
   * @author Brian Woodruff
   *
   */
  private class IndividualRocketComponentTreeItem extends RocketComponentTreeItem {
    private RocketComponent component;
    private Parent view;

    /**
     * Set name and create view
     *
     * @param part
     *          a {@link RocketPart} to be stored in this
     *          {@link RocketComponentTreeItem}
     */
    public IndividualRocketComponentTreeItem(String url, RocketComponent component) {
      super(component.getName());
      this.component = component;
      view = loadComponentView(url, component);
    }

    @Override
    public void setPaneNode(Pane pane) {
      pane.getChildren().clear();
      pane.getChildren().add(view);
    }

    @Override
    public void updateSelectedComponent() {
      currentRocketComponent = component;
    }
  }

  /**
   * Internal Components
   *
   * @author Brian Woodruff
   *
   */
  private class InternalComponents extends RocketComponentTreeItem {

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
  private class ExternalComponents extends RocketComponentTreeItem {

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

  @FXML
  private TreeView<String> partList;

  @FXML
  private VBox partViewer;

  Rocket rocket;

  private RocketComponentTreeItem internalTreePartsRoot = new InternalComponents();
  private RocketComponentTreeItem externalTreePartsRoot = new ExternalComponents();

  private List<RocketPart> internalParts = FXCollections.observableArrayList(InternalRocketPart
      .values());
  private List<RocketPart> externalParts = FXCollections.observableArrayList(ExternalRocketPart
      .values());

  private Map<RocketPart, String> itemURL = new HashMap<>();
  private Map<RocketPart, Class<?>> itemType = new HashMap<>();

  private RocketComponent currentRocketComponent;
  private RocketComponentTreeItem currentTreeItem;

  private ChangeListener<TreeItem<String>> selectionEvent = (reserved, old, current) -> {
    if (current instanceof RocketComponentTreeItem) {
      ((RocketComponentTreeItem) current).setPaneNode(RocketCreationController.this.partViewer);
      ((RocketComponentTreeItem) current).updateSelectedComponent();
      RocketCreationController.this.currentTreeItem = (RocketComponentTreeItem) current;
    }
  };

  /**
   * @param rocket
   *          a rocket that will be modified in this view.
   */
  public RocketCreationController(Rocket rocket) {
    this.rocket = rocket;
    
  }
  
  private void initializeModel()
  {
    setMaps();

    try {
      for (RocketComponent component : rocket.getExteriorComponents()) {
        if (component instanceof CircularCylinder) {
          addPartToTreeView(component, ExternalRocketPart.CIRCULAR_CYLINDER, false);
        } else if (component instanceof ConicalFrustum) {
          addPartToTreeView(component, ExternalRocketPart.CONICAL_FRUSTUM, false);
        } else if (component instanceof TrapezoidFinSet) {
          addPartToTreeView(component, ExternalRocketPart.TRAPEZOID_FIN_SET, false);
        } else if (component instanceof NoseCone) {
          addPartToTreeView(component, ExternalRocketPart.NOSE_CONE, false);
        }
      }

      for (RocketComponent component : rocket.getInteriorComponents()) {
        if (component instanceof Motor) {
          addPartToTreeView(component, InternalRocketPart.MOTOR, false);
        } else if (component instanceof Parachute) {
          addPartToTreeView(component, InternalRocketPart.PARACHUTE, false);
        } else if (component instanceof CircularCylinder) {
          addPartToTreeView(component, InternalRocketPart.CIRCULAR_CYLINDER, false);
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Opens a {@link PartChooser} dialog window. Passes a list of internal and
   * external parts to choose from.
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
    component.setName(part.toString());

    addPartToTreeView(component, part, true);
  }

  private void addPartToTreeView(RocketComponent component, RocketPart type,
      boolean addComponentToRocket) {

    String url = itemURL.get(type);
    IndividualRocketComponentTreeItem newTreeItem;
    newTreeItem = new IndividualRocketComponentTreeItem(url, component);

    if (type instanceof InternalRocketPart) {
      if (addComponentToRocket) {
        rocket.getInteriorComponents().add(component);
      }

      internalTreePartsRoot.getChildren().add(newTreeItem);
      internalTreePartsRoot.setExpanded(true);
    } else if (type instanceof ExternalRocketPart) {
      if (addComponentToRocket) {
        rocket.getExteriorComponents().add(component);
      }
      externalTreePartsRoot.getChildren().add(newTreeItem);
      externalTreePartsRoot.setExpanded(true);
    }
    partList.getSelectionModel().select(newTreeItem);
  }

  /**
   * Sets up the view for the Rocket Creation View. Tree View is populated with
   * two roots: internal and external.
   */
  public void initialize() {
    setMaps();

    ContextMenu contextMenu = new ContextMenu();
    MenuItem menuItem = new MenuItem("Delete");
    contextMenu.getItems().add(menuItem);
    partList.setContextMenu(contextMenu);

    TreeItem<String> treeViewRoot = new TreeItem<String>();

    treeViewRoot.setExpanded(true);
    treeViewRoot.getChildren().add(internalTreePartsRoot);
    treeViewRoot.getChildren().add(externalTreePartsRoot);

    menuItem.setOnAction(event -> {
      if (RocketCreationController.this.internalTreePartsRoot.getChildren().contains(
          RocketCreationController.this.currentTreeItem)) {
        RocketCreationController.this.internalTreePartsRoot.getChildren().remove(
            RocketCreationController.this.currentTreeItem);
        RocketCreationController.this.rocket.getInteriorComponents().remove(
            RocketCreationController.this.currentRocketComponent);
        System.out.println("Remove internal component");
      } else if (RocketCreationController.this.externalTreePartsRoot.getChildren().contains(
          RocketCreationController.this.currentTreeItem)) {
        RocketCreationController.this.externalTreePartsRoot.getChildren().remove(
            RocketCreationController.this.currentTreeItem);
        RocketCreationController.this.rocket.getExteriorComponents().remove(
            RocketCreationController.this.currentRocketComponent);
        System.out.println("Remove external component");
      } else {
        System.out.println("Nothing removed"
            + RocketCreationController.this.currentTreeItem.getClass());
      }
    });

    partList.getSelectionModel().selectedItemProperty().addListener(selectionEvent);
    partList.setRoot(treeViewRoot);
    partList.setShowRoot(false);
    
    initializeModel();
  }

  /**
   * @param url
   *          location of component
   * @return the Parent from the url
   */
  private Parent loadComponentView(String url, RocketComponent component) {
    try {
      ControllerFactory controllerFactory = new ControllerFactory();
      controllerFactory.addSharedInstance(component);
      IController controller = controllerFactory.create(url);
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
    if (itemURL.size() > 0) {
      return;
    }

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
