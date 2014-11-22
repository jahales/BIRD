package controllers.parts;

import controllers.RocketCreationController.RocketPart;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author Jacob, Brian Woodruff
 */
public class PartChooser {
  private RocketPart selectedRocketPart;
  private Stage stage = new Stage();
  private ListView<RocketPart> partsList = new ListView<RocketPart>();;

  private Button addPart = new Button("Add Part");
  private Button cancel = new Button("Add Part");

  /**
   * Selection dialog that returns the selected part.
   * 
   * @param window
   * @return selected Rocket Part
   */
  public RocketPart showPartDialog(Window window) {
    partsList.setItems(FXCollections.observableArrayList(RocketPart.values()));
    partsList.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<RocketPart>() {
          @Override
          public void changed(ObservableValue<? extends RocketPart> reserved, RocketPart old,
              RocketPart current) {
            selectedRocketPart = current;
          }
        });

    VBox vbox = new VBox();
    HBox hbox = new HBox(10);

    hbox.getChildren().addAll(addPart, cancel);
    vbox.getChildren().addAll(partsList, hbox);

    stage.setTitle("Add part");
    stage.initOwner(window);
    stage.initModality(Modality.WINDOW_MODAL);
    stage.setScene(new Scene(vbox));

    keyEvent();
    setOnAction();

    stage.showAndWait();

    return selectedRocketPart;
  }

  private void keyEvent() {
    partsList.setOnKeyPressed((event) -> {
      if (event.getCode() == KeyCode.ENTER) {
        stage.close();
      }
    });

    partsList.setOnMouseClicked((event) -> {
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
