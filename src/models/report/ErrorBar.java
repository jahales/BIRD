package models.report;

import javafx.scene.Group;
import javafx.scene.shape.Line;

/**
 *
 * @author Jacob
 */
public class ErrorBar extends Group {
    private Line topBar;
    private Line bottomBar;
    private Line verticalBar;
    private double topBarHeight;
    private double topBarWidth;
    private double bottomBarHeight;
    private double bottomBarWidth;

  /**
   *
   * @param error
   */
  public ErrorBar(double error) {
	topBarHeight = error;
	topBarWidth = 5;
	topBar = new Line(-topBarWidth, topBarHeight, topBarWidth, topBarHeight);
	
	bottomBarHeight = error;
	bottomBarWidth = 5;
	bottomBar = new Line(-bottomBarWidth, -bottomBarHeight, bottomBarWidth, -bottomBarHeight);
	
	verticalBar = new Line(0, topBarHeight, 0, -bottomBarHeight);
	getChildren().addAll(topBar, bottomBar, verticalBar);
    }
}
