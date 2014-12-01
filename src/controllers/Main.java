package controllers;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.MainViewModel;

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
    startNewMainView(primaryStage, new MainViewModel());
  }

  /**
   * Creates a new instance of the main view.
   */
  public static void startNewMainView() {
    startNewMainView(new Stage(), new MainViewModel());
  }
  
  /**
   * Creates a new instance of the main view.
   * @param model Model representing the initial state of the view.
   */
  public static void startNewMainView(MainViewModel model) {
    startNewMainView(new Stage(), model);
  }

  /**
   * Creates a new instance of the main view.
   * @param stage Stage that will host the main view.
   * @param model Model representing the initial state of the view.
   */
  public static void startNewMainView(Stage stage, MainViewModel model) {
    try {
      ControllerFactory controllerFactory = new ControllerFactory();
      IController controller = controllerFactory.load("/views/MainView.fxml", new Object[]{model, model.getRocket()});
      Scene scene = new Scene((Parent)controller.getView());
      stage.setScene(scene);
      stage.setTitle("BIRD");
      stage.show();
    } catch (Exception ex) {
      logger.log(Level.WARNING, "Failed to create new MainView.", ex);
    }
  }
}
