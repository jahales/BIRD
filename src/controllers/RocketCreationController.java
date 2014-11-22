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
import javafx.util.Callback;
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

  public interface RocketPart {
    String getValue();
  }

  public enum InternalRocketPart implements RocketPart {
    CIRCULAR_CYLINDER, CONICAL_FRUSTUM, MOTOR, PARACHUTE;

    @Override
    public String getValue() {
      return toString();
    }
  }

  public enum ExternalRocketPart implements RocketPart {
    CIRCULAR_CYLINDER, CONICAL_FRUSTUM, TRAPEZOID_FIN_SET, NOSE_CONE;

    @Override
    public String getValue() {
      return toString();
    }
  }

  static class MyTreeCell extends TreeCell<String> {

    @Override
    protected void updateItem(String item, boolean empty) {
      super.updateItem(item, empty);
      if (!empty) {
        setText(toCamelCase(item));
      }
    }

    static String toCamelCase(String string) {
      String[] parts = string.split("_");
      String camelCaseString = "";
      for (String part : parts) {
        camelCaseString += " " + toProperCase(part);
      }
      return camelCaseString;
    }

    static String toProperCase(String string) {
      return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }
  }

  Rocket rocket;

  private TreeItem<String> treeViewRoot = new TreeItem<String>();

  private TreeComponent internalTreePartsRoot = new InternalComponents();
  private TreeComponent externalTreePartsRoot = new ExternalComponents();

  private List<RocketPart> internalParts = FXCollections.observableArrayList(InternalRocketPart
      .values());
  private List<RocketPart> externalParts = FXCollections.observableArrayList(ExternalRocketPart
      .values());

  private Map<RocketPart, String> itemURL = new HashMap<>();

  @FXML
  private TreeView<String> partList;

  @FXML
  private AnchorPane partViewer;

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
    RocketPart part = partChooser.showPartDialog(internalParts, externalParts, partViewer
        .getScene().getWindow());
    
    if (part != null) {
      if (part instanceof InternalRocketPart) {
        internalTreePartsRoot.getChildren().add(new IndividualRocketComponent(part));
        if (part == InternalRocketPart.MOTOR) {
          internalParts.remove(part);
        }
      } else {
        externalTreePartsRoot.getChildren().add(new IndividualRocketComponent(part));
        if (part == ExternalRocketPart.NOSE_CONE || part == ExternalRocketPart.TRAPEZOID_FIN_SET) {
          externalParts.remove(part);
        }
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

    partList.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
      @Override
      public TreeCell<String> call(TreeView<String> arg0) {
        return new MyTreeCell();
      }
    });

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
    itemURL.put(InternalRocketPart.CIRCULAR_CYLINDER, "/views/parts/CircularCylinder.fxml");
    itemURL.put(InternalRocketPart.CONICAL_FRUSTUM, "/views/parts/ConicalFrustum.fxml");
    itemURL.put(InternalRocketPart.MOTOR, "/views/parts/Motor.fxml");
    itemURL.put(InternalRocketPart.PARACHUTE, "/views/parts/Parachute.fxml");

    itemURL.put(ExternalRocketPart.CIRCULAR_CYLINDER, "/views/parts/CircularCylinder.fxml");
    itemURL.put(ExternalRocketPart.CONICAL_FRUSTUM, "/views/parts/ConicalFrustum.fxml");
    itemURL.put(ExternalRocketPart.NOSE_CONE, "/views/parts/NoseCone.fxml");
    itemURL.put(ExternalRocketPart.TRAPEZOID_FIN_SET, "/views/parts/TrapezoidFinSet.fxml");
  }
}
