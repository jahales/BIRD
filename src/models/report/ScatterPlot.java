package models.report;

import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

/**
 * An extension of LineChart that enables customization of the node for each
 * point. This class uses ErrorBars as the node.
 *
 * @author Brian Woodruff
 *
 */
public class ScatterPlot extends LineChart<Number, Number> {
  /**
   * @param xAxis
   *          the x-axis
   * @param yAxis
   *          the y-axis
   */
  public ScatterPlot(Axis<Number> xAxis, Axis<Number> yAxis) {
    super(xAxis, yAxis);
  }

  /**
   * Add a point to the chart.
   *
   * @param data
   *          a point
   */
  public void addPoint(XYChart.Data<Number, Number> data) {

  }

  /**
   * Remove all points in the chart.
   */
  public void clear() {

  }

  /**
   * Set chart title.
   *
   * @param title
   *          title for the chart
   */
  public void setChartTitle(String title) {

  }

  /**
   * Set X-axis name
   *
   * @param name
   *          x-axis name
   */
  public void setXAxisName(String name) {

  }

  /**
   * Set Y-axis name
   *
   * @param name
   *          y-axis name
   */
  public void setYAxisName(String name) {

  }
}
