package controllers;

import javafx.scene.Node;

/**
 *
 * @author Jacob
 */
public class BaseController implements IController {

  private Node view;

  @Override
  public Node getView() {
    return view;
  }

  @Override
  public void setView(Node view) {
    this.view = view;
  }

}
