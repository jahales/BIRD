package controllers.parts;

import controllers.RocketCreationController.RocketPart;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author Jacob
 */
public class PartChooser {
  private RocketPart selectedRocketPart;
  private Stage stage;
  private ListView<RocketPart> partsList;

  /**
   *
   * @param window
   * @return
   */
  public RocketPart showPartDialog(Window window) {
    partsList = new ListView<RocketPart>();
    partsList.setItems(FXCollections.observableArrayList(RocketPart.values()));
    partsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<RocketPart>() {
          @Override
          public void changed(ObservableValue<? extends RocketPart> arg0, RocketPart arg1, RocketPart arg2) {
            selectedRocketPart = arg2;
          }
        });
    
    VBox vbox = new VBox();
    HBox hbox = new HBox(10);
    
    Button addPart = new Button("Add Part");
    Button cancel = new Button("Cancel");
    
    hbox.getChildren().addAll(addPart, cancel);
    vbox.getChildren().addAll(partsList, hbox);
    
    stage = new Stage();
    stage.setTitle("Add part");
    stage.initOwner(window);
    stage.initModality(Modality.WINDOW_MODAL);
    stage.setScene(new Scene(vbox));
    
    addPart.setOnAction((e) -> {
      stage.close();
    });
    cancel.setOnAction((e) -> {
      selectedRocketPart = null;
      stage.close();
    });
    stage.setOnCloseRequest((e) -> {
      selectedRocketPart = null;
      stage.close();
    });
    
    stage.showAndWait();
    
    return selectedRocketPart;
  }
}
