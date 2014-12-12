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
    this.barHeight = error;
    this.topBar = new Line(-this.barWidth, this.barHeight, this.barWidth, this.barHeight);
    // new Line(startX, startY, endX, endY);
    this.bottomBar = new Line(-this.barWidth, -this.barHeight, this.barWidth, -this.barHeight);

    this.verticalBar = new Line(0, this.barHeight, 0, -this.barHeight);
    getChildren().addAll(this.topBar, this.bottomBar, this.verticalBar);
  }
}
