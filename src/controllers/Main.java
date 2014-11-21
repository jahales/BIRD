package controllers;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.ModelState;
import views.ViewFactory;

/**
 * Main class that contains the entry point for the JavaFX application.
 * @author Jacob
 */
public class Main extends Application {
  
  final static Logger logger = Logger.getLogger(Main.class.getName());

  /**
   * Entry point for the application.
   * @param args Command line arguments.
   */
  public static void main(String[] args) {
    Application.launch(Main.class, (java.lang.String[]) null);
  }

  @Override
  public void start(Stage primaryStage) {
    startNewInstance(primaryStage, new ModelState());
  }

  /**
   * Creates a new instance of the main view.
   */
  public static void startNewInstance() {
    startNewInstance(new Stage(), new ModelState());
  }
  
  /**
   * Creates a new instance of the main view.
   * @param model Model representing the initial state of the view.
   */
  public static void startNewInstance(ModelState model) {
    startNewInstance(new Stage(), model);
  }

  /**
   * Creates a new instance of the main view.
   * @param stage Stage that will host the main view.
   * @param model Model representing the initial state of the view.
   */
  public static void startNewInstance(Stage stage, ModelState model) {
    try {
      ViewFactory viewFactory = new ViewFactory();
      Object view = viewFactory.create("/views/MainView.fxml", new Object[]{model});
      Scene scene = new Scene((Parent) view);
      stage.setScene(scene);
      stage.setTitle("BIRD");
      stage.show();
    } catch (Exception ex) {
      logger.log(Level.WARNING, "Failed to create new MainView.", ex);
    }
  }
}
