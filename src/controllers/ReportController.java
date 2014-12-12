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
    if (table == null || table.getColumnNames().size() < 1) {
      return;
    }

    // X axis
    xAxisChoices.setItems(FXCollections.observableArrayList(table.getColumnNames()));
    xAxisChoices.setValue(table.getColumnNames().get(0));
    xAxisChoices.getSelectionModel().selectedItemProperty()
        .addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
          dependentVariableList = table.getColumn(newValue);
          independentVariableList.clear();
          graph.getData().clear();
          yAxisChoices.getItems().add(oldValue);
          yAxisChoices.getItems().remove(newValue);
        });

    // Y axis
    yAxisChoices.setItems(FXCollections.observableArrayList(table.getColumnNames()));
    yAxisChoices.setCellFactory(checkBoxFactory);

    dependentVariableList = table.getColumn(xAxisChoices.getValue());
    yAxisChoices.getItems().remove(xAxisChoices.getValue());
  }

  Callback<ListView<String>, ListCell<String>> checkBoxFactory = CheckBoxListCell
      .forListView(new Callback<String, ObservableValue<Boolean>>() {
        @Override
        public ObservableValue<Boolean> call(String param) {
          ObservableValue<Boolean> selectedValue = new SimpleBooleanProperty(false);
          selectedValue.addListener(setCheckBoxListener(param));
          return selectedValue;
        }
      });

  ChangeListener<Boolean> setCheckBoxListener(String independentAxis) {
    return (observable, oldValue, newValue) -> {
      if (newValue == true) {
        List<Number> independantVariableColumn = table.getColumn(independentAxis);

        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        // :TODO Look at this line of code below
        series.setName(xAxisChoices.getValue());

        double skips = (double) dependentVariableList.size() / (double) MAX_NODES_IN_GRAGH;
        for (double i = 0; i < dependentVariableList.size(); i += skips) {
          XYChart.Data<Number, Number> data;
          data = new XYChart.Data<Number, Number>(dependentVariableList.get((int) i),
              independantVariableColumn.get((int) i));
          data.setNode(new ErrorBar(7));
          series.getData().add(data);
        }

        independentVariableList.put(independentAxis, series);
        graph.getData().add(series);
      } else {
        graph.getData().remove(independentVariableList.get(independentAxis));
      }
    };
  }
}
