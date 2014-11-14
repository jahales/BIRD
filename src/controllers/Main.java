package controllers;

import controllers.RocketCreationController.RocketPart;
import controllers.parts.PartChooser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Jacob
 */
public class Main extends Application {
  /**
   *
   * @param args
   */
  public static void main(String[] args) {
    Application.launch(Main.class, (java.lang.String[]) null);
  }

  @Override
  public void start(Stage primaryStage) {
    try {
      Scene scene = new Scene(FXMLLoader.load(Main.class.getResource("/views/MainView.fxml")));
      primaryStage.setScene(scene);
      primaryStage.setTitle("BIRD");
      primaryStage.show();
      PartChooser partChooser = new PartChooser();
      //RocketPart part 
      int part = partChooser.showPartDialog(primaryStage.getScene().getWindow());
      System.out.println("You finally picked: " + part);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
