package controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Jacob
 */
public class Main extends Application {
  public static void main(String[] args)
  {
    Application.launch(Main.class, (java.lang.String[])null);
  }

  @Override
    public void start(Stage primaryStage) {
        try {
            Parent page = (Parent) FXMLLoader.load(Main.class.getResource("/views/MainView.fxml"));
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("BIRD");
            primaryStage.show();
        } catch (Exception ex) {
          ex.printStackTrace();
        }
    }
}
