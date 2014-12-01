package controllers;

import javafx.scene.Node;

/**
 *
 * @author Jacob
 */
public interface IController {

  Node getView();

  void setView(Node view);
}
