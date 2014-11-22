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

  private Tab internalParts = new Tab("Internal");
  private Tab externalParts = new Tab("External");
  
  Callback<ListView<RocketPart>, ListCell<RocketPart>> cellFactory = new Callback<ListView<RocketPart>, ListCell<RocketPart>>() {
    @Override
    public ListCell<RocketPart> call(ListView<RocketPart> arg0) {
      return new MyListCell();
    }
  };

  /**
   * Selection dialog that returns the selected part.
   * 
   * @param window
   * @return selected Rocket Part
   */
  public RocketPart showPartDialog(List<RocketPart> internal, List<RocketPart> external, Window window) {
    externalPartsList.setCellFactory(cellFactory);
    internalPartsList.setCellFactory(cellFactory);

    externalPartsList.setItems(FXCollections.observableArrayList(internal));
    internalPartsList.setItems(FXCollections.observableArrayList(external));

    selectedItem(internalPartsList, internalParts);
    selectedItem(externalPartsList, externalParts);

    VBox vbox = new VBox();
    HBox hbox = new HBox();

    internalParts.setContent(internalPartsList);
    externalParts.setContent(externalPartsList);

    tabs.getTabs().addAll(internalParts, externalParts);

    hbox.getChildren().addAll(addPart, cancel);
    vbox.getChildren().addAll(tabs, hbox);

    stage.setTitle("Add part");
    stage.initOwner(window);
    stage.initModality(Modality.WINDOW_MODAL);
    stage.setScene(new Scene(vbox));

    keyEvent(internalPartsList);
    keyEvent(externalPartsList);

    setOnAction();

    stage.showAndWait();

    return selectedRocketPart;
  }

  static class MyListCell extends ListCell<RocketPart> {
    @Override
    protected void updateItem(RocketPart item, boolean empty) {
      super.updateItem(item, empty);
      if (!empty) {
        setText(toCamelCase(item.toString()));
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

  private void selectedItem(ListView<RocketPart> list, Tab tab) {
    list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<RocketPart>() {
      @Override
      public void changed(ObservableValue<? extends RocketPart> reserved, RocketPart old, RocketPart current) {
        if (tab.isSelected()) {
          selectedRocketPart = current;
        }
      }
    });
  }

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
