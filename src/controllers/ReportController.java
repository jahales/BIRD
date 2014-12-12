package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.util.Callback;
import models.report.DataTable;
import models.report.DataTable.RowFormatError;
import models.report.ErrorBar;

/**
 * Controller for Report view. Gets axis choices from user and displays results
 * on graph.
 *
 * @author Brian Woodruff
 *
 */
public class ReportController extends BaseController {
  DataTable table = null;
  private static final int MAX_NODES_IN_GRAGH = 50;

  @FXML
  private LineChart<Number, Number> graph;

  @FXML
  private NumberAxis yAxis;

  @FXML
  private NumberAxis xAxis;

  @FXML
  private ChoiceBox<String> xAxisChoices;

  @FXML
  private ListView<String> yAxisChoices;

  Map<String, XYChart.Series<Number, Number>> independentVariableList = new HashMap<String, XYChart.Series<Number, Number>>();

  List<Number> dependentVariableList = new ArrayList<Number>();

  public ReportController(DataTable table) {
    this.table = table;
  }

  /**
   * initialize is called during FXMLoader call
   *
   * @throws RowFormatError
   */
  public void initialize() throws RowFormatError {
    if (this.table == null || this.table.getColumnNames().size() < 1) {
      return;
    }

    // X axis
    this.xAxisChoices.setItems(FXCollections.observableArrayList(this.table.getColumnNames()));
    this.xAxisChoices.setValue(this.table.getColumnNames().get(0));
    this.xAxisChoices
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (ChangeListener<String>) (observable, oldValue, newValue) -> {
              ReportController.this.dependentVariableList = ReportController.this.table
                  .getColumn(newValue);
              ReportController.this.independentVariableList.clear();
              ReportController.this.graph.getData().clear();
              ReportController.this.yAxisChoices.getItems().add(oldValue);
              ReportController.this.yAxisChoices.getItems().remove(newValue);
            });

    // Y axis
    this.yAxisChoices.setItems(FXCollections.observableArrayList(this.table.getColumnNames()));
    this.yAxisChoices.setCellFactory(this.checkBoxFactory);

    this.dependentVariableList = this.table.getColumn(this.xAxisChoices.getValue()); // First
                                                                                     // time
    this.yAxisChoices.getItems().remove(this.xAxisChoices.getValue()); // Again,
                                                                       // first
                                                                       // time
  }

  Callback<ListView<String>, ListCell<String>> checkBoxFactory = CheckBoxListCell
      .forListView(param -> {
        ObservableValue<Boolean> selectedValue = new SimpleBooleanProperty(false);
        selectedValue.addListener(setCheckBoxListener(param));
        return selectedValue;
      });

  ChangeListener<Boolean> setCheckBoxListener(String independentAxis) {
    return (observable, oldValue, newValue) -> {
      if (newValue == true) {
        List<Number> independantVariableColumn = ReportController.this.table
            .getColumn(independentAxis);

        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        // :TODO Look at this line of code below
        series.setName(ReportController.this.xAxisChoices.getValue());

        double skips = (double) ReportController.this.dependentVariableList.size()
            / (double) MAX_NODES_IN_GRAGH;
        for (double i = 0; i < ReportController.this.dependentVariableList.size(); i += skips) {
          XYChart.Data<Number, Number> data;
          data = new XYChart.Data<Number, Number>(
              ReportController.this.dependentVariableList.get((int) i),
              independantVariableColumn.get((int) i));
          data.setNode(new ErrorBar(7));
          series.getData().add(data);
        }

        ReportController.this.independentVariableList.put(independentAxis, series);
        ReportController.this.graph.getData().add(series);
      } else {
        ReportController.this.graph.getData().remove(
            ReportController.this.independentVariableList.get(independentAxis));
      }
    };
  }
}
