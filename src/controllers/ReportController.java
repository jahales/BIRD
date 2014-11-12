package controllers;

import java.io.IOException;

import models.report.CSVReader;
import models.report.DataTable;
import models.report.ErrorBar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * Controller for Report view. Gets axis choices from user and displays results
 * on graph.
 *
 * @author Brian Woodruff
 *
 */
public class ReportController {

    DataTable table;

    @FXML
    private LineChart<Number, Number> graph;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private ListView<String> xAxisChoices;
    ObservableList<String> xList;

    @FXML
    private TextField xMax;

    @FXML
    private TextField xMin;

    @FXML
    private ListView<String> yAxisChoices;
    ObservableList<String> yList;

    /**
     * initialize is called during FXMLoader call
     */
    public void initialize() {
	try {
	    table = CSVReader.loadCSV("A file");
	} catch (IOException e) {
	    System.out.println("File wasn't read right");
	    //e.printStackTrace();
	}

	// X axis
	// xAxisChoices.setItems((ObservableList<String>)
	// table.getColumnNames());
	xList = FXCollections.observableArrayList("1 fish", "2 fish", "Red fish", "Blue fish");
	xAxisChoices.setItems(xList);
	xAxisChoices.setOnMouseClicked(doubleClick);

	// Y axis
	// yAxisChoices.setItems((ObservableList<String>)
	// table.getColumnNames());
	yList = FXCollections.observableArrayList("I am Sam", "Sam I am");
	yAxisChoices.setItems(yList);
	yAxisChoices.setOnMouseClicked(doubleClick);

	setEnterEvent(xMin, xList);
	setEnterEvent(xMax, yList);
    }

    /**
     * Generic event setter for a given axis min/max and list
     *
     * @param boundedAxis
     * @param axisListView
     */
    private void setEnterEvent(TextField boundedAxis,
	    ObservableList<String> axisListView) {
	boundedAxis.setOnKeyPressed(new EventHandler<KeyEvent>() {
	    @Override
	    public void handle(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
		    // Update axis bounds
		    double d = 0.0;
		    try {
			d = Double.parseDouble(boundedAxis.getText());
			System.out.println(Double.toString(d) + " is a number");
		    } catch (NumberFormatException e) {
			System.out.println(Double.toString(d)
				+ " is not a number");
		    }
		}
	    }
	});
    }

    private EventHandler<MouseEvent> doubleClick = new EventHandler<MouseEvent>() {
	@Override
	public void handle(MouseEvent event) {
	    if (event.getClickCount() == 2) {
		String value = xAxisChoices.selectionModelProperty().get()
			.getSelectedItem();

		XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
		series.setName(value);

		for (double i = 0; i < 2 * Math.PI; i += 0.05) {
		    XYChart.Data<Number, Number> data;
		    data = new XYChart.Data<Number, Number>(i, Math.sin(i));
		    ErrorBar errorBar = new ErrorBar(3 * i);
		    data.setNode(errorBar);
		    series.getData().add(data);
		}

		graph.getData().add(series);
	    }
	}
    };
}
