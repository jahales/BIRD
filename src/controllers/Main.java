package controllers;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.ModelState;
import views.ViewFactory;

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
      Object view = ViewFactory.create("/views/MainView.fxml", new Object[] { ModelState.getInstance() });
      Scene scene = new Scene((Parent)view);
      primaryStage.setScene(scene);
      primaryStage.setTitle("BIRD");
      primaryStage.show();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
