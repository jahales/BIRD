package controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * Controller for Report view.
 * Gets axis choices from user and displays results on graph.
 * 
 * @author Brian Woodruff
 *
 */
public class ReportController {

    @FXML
    private NumberAxis yAxis;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private ListView<?> xAxisChoices;

    @FXML
    private Label xMax;

    @FXML
    private Label xMin;

    @FXML
    private ListView<?> yAxisChoices;

}
