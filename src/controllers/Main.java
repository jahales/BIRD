package controllers;

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
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
