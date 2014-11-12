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
     * Constructor
     * 
     * @param xAxis
     * @param yAxis
     */
    public ScatterPlot(Axis<Number> xAxis, Axis<Number> yAxis) {
	super(xAxis, yAxis);
    }

    /**
     * Add a point to the chart.
     * 
     * @param data
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
     */
    public void setChartTitle(String title) {

    }

    /**
     * Set X-axis name.
     * 
     * @param name
     */
    public void setXAxisName(String name) {

    }

    /**
     * Set Y-axis name.
     * @param name
     */
    public void setYAxisName(String name) {

    }

}
