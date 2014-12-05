package controllers.parts;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import controllers.RocketCreationController.RocketPart;

/**
 * Part chooser dialog window. Pass a list of internal and external parts to choose from.
 *
 * @author Jacob, Brian Woodruff
 */
public class PartChooser {

  private RocketPart selectedRocketPart;
  private Stage stage = new Stage();
  private ListView<RocketPart> internalPartsList = new ListView<RocketPart>();
  private ListView<RocketPart> externalPartsList = new ListView<RocketPart>();

  private Button addPart = new Button("Add Part");
  private Button cancel = new Button("Cancel");

  private TabPane tabs = new TabPane();

  private Tab internalPartsTab = new Tab("Internal");
  private Tab externalPartsTab = new Tab("External");

  private Callback<ListView<RocketPart>, ListCell<RocketPart>> cellFactory = (event) -> {
    return new MyListCell();
  };

  /**
   * Show dialog that returns the selected part
   *
   * @param internal list of internal {@link RocketPart} to choose from
   * @param external list of external {@link RocketPart} to choose from
   * @param window the parent window that will own this child window
   * @return the selected {@link RocketPart}
   * <p>
   * <b>Note:</b> returns null if nothing was selected
   */
  public RocketPart showPartDialog(List<RocketPart> internal,
                                   List<RocketPart> external,
                                   Window window) {
    externalPartsList.setCellFactory(cellFactory);
    internalPartsList.setCellFactory(cellFactory);

    externalPartsList.setItems(FXCollections.observableArrayList(external));
    internalPartsList.setItems(FXCollections.observableArrayList(internal));

    keyEvent(internalPartsList);
    keyEvent(externalPartsList);

    selectedItem(internalPartsList, internalPartsTab);
    selectedItem(externalPartsList, externalPartsTab);

    setOnAction();

    internalPartsTab.setContent(internalPartsList);
    externalPartsTab.setContent(externalPartsList);

    tabs.getTabs().addAll(internalPartsTab, externalPartsTab);

    VBox vbox = new VBox();
    HBox hbox = new HBox();

    hbox.getChildren().addAll(addPart, cancel);
    vbox.getChildren().addAll(tabs, hbox);

    stage.setTitle("Add part");
    stage.initOwner(window);
    stage.initModality(Modality.WINDOW_MODAL);
    stage.setScene(new Scene(vbox));
    stage.showAndWait();

    return selectedRocketPart;
  }

  /**
   * Custom list cell to manually display name in more readable format
   *
   * @author Brian Woodruff
   *
   */
  private class MyListCell extends ListCell<RocketPart> {

    @Override
    protected void updateItem(RocketPart item, boolean empty) {
      super.updateItem(item, empty);
      if (!empty) {
        setText(toCamelCase(item.toString()));
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

  /**
   * If tab is selected, set selected part to whatever is selected in the list
   *
   * @param list list to insert into tab
   * @param tab tab to insert list into
   */
  private void selectedItem(ListView<RocketPart> list, Tab tab) {
    list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<RocketPart>() {
      @Override
      public void changed(ObservableValue<? extends RocketPart> reserved, RocketPart old,
          RocketPart current) {
        if (tab.isSelected()) {
          selectedRocketPart = current;
        }
      }
    });
  }

  /**
   * Set event for pressing 'Enter' and double clicking
   *
   * @param list list to setup listeners for
   */
  private void keyEvent(ListView<RocketPart> list) {
    list.setOnKeyPressed((event) -> {
      if (event.getCode() == KeyCode.ENTER) {
        stage.close();
      }
    });

    list.setOnMouseClicked((event) -> {
      if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
        stage.close();
      }
    });
  }

  /**
   * Set events for pushing buttons and stuff
   */
  private void setOnAction() {
    addPart.setOnAction((event) -> {
      stage.close();
    });
    cancel.setOnAction((event) -> {
      selectedRocketPart = null;
      stage.close();
    });
    stage.setOnCloseRequest((event) -> {
      selectedRocketPart = null;
      stage.close();
    });
  }
}
