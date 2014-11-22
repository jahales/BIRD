package models.report;

import javafx.scene.Group;
import javafx.scene.shape.Line;

/**
 * A group of lines and stuff that look like 'I'. Width is defined in the
 * constructor
 * 
 * @author Jacob, Brian
 */
public class ErrorBar extends Group {
  private Line topBar;
  private Line bottomBar;
  private Line verticalBar;
  private double barHeight;
  private double barWidth = 5;

  /**
   * @param error
   *          height of error bar
   */
  public ErrorBar(double error) {
    barHeight = error;
    topBar = new Line(-barWidth, barHeight, barWidth, barHeight);

    bottomBar = new Line(-barWidth, barHeight, barWidth, barHeight);

    verticalBar = new Line(0, barHeight, 0, -barHeight);
    getChildren().addAll(topBar, bottomBar, verticalBar);
  }
}
