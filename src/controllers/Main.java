package controllers;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.rocket.Rocket;

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
      // Initialize the FXML loader 
      ControllerFactory factory = new ControllerFactory();
      factory.addSingleton(new Rocket());
      
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(Main.class.getResource("/views/MainView.fxml"));
      loader.setControllerFactory(factory);
      
      Scene scene = new Scene(loader.load());
      primaryStage.setScene(scene);
      primaryStage.setTitle("BIRD");
      primaryStage.show();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
