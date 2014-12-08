package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import models.report.DataTable;
import models.report.DataTable.RowFormatError;
import models.report.ErrorBar;
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

/**
 * Controller for Report view. Gets axis choices from user and displays results
 * on graph.
 *
 * @author Brian Woodruff
 *
 */
public class ReportController extends BaseController {
  DataTable table = null;

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

  private void debug() throws RowFormatError {
    List<String> words = FXCollections.observableArrayList("List", "Book", "Deer", "Rain", "Lint",
        "Fear");
    int rows = new Random().nextInt(2000);

    // Add column names
    for (String word : words) {
      table.addColumn(word);
    }

    // Add some rows
    for (int row = 0; row < rows; row++) {
      List<Number> rowList = new ArrayList<Number>();
      for (int i = 0; i < words.size(); i++) {
        rowList.add(Math.random());
      }
      table.addRow(rowList);
    }
  }

  /**
   * initialize is called during FXMLoader call
   * 
   * @throws RowFormatError
   */
  public void initialize() throws RowFormatError {
    if (this.table == null || this.table.getColumnNames().size() < 1)
    {
      return;
    }

    // X axis
    xAxisChoices.setItems(FXCollections.observableArrayList(table.getColumnNames()));
    xAxisChoices.setValue(table.getColumnNames().get(0));
    xAxisChoices.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        dependentVariableList = table.getColumn(newValue);
        independentVariableList.clear();
        graph.getData().clear();
        yAxisChoices.getItems().add(oldValue);
        yAxisChoices.getItems().remove(newValue);
      }
    });

    // Y axis
    yAxisChoices.setItems(FXCollections.observableArrayList(table.getColumnNames()));
    yAxisChoices.setCellFactory(checkBoxFactory);
    
    dependentVariableList = table.getColumn(xAxisChoices.getValue()); // First time
    yAxisChoices.getItems().remove(xAxisChoices.getValue());          // Again, first time
  }

  Callback<ListView<String>, ListCell<String>> checkBoxFactory = CheckBoxListCell.forListView(new Callback<String, ObservableValue<Boolean>>() {
        @Override
        public ObservableValue<Boolean> call(String param) {
          ObservableValue<Boolean> selectedValue = new SimpleBooleanProperty(false);
          selectedValue.addListener(setCheckBoxListener(param));
          return selectedValue;
        }
      });

  ChangeListener<Boolean> setCheckBoxListener(String independentAxis) {
    return new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (newValue == true) {
          List<Number> independantVariableColumn = table.getColumn(independentAxis);

          XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
          series.setName(xAxisChoices.getValue());

          for (int i = 0; i < dependentVariableList.size(); i++) {
            XYChart.Data<Number, Number> data;
            data = new XYChart.Data<Number, Number>(dependentVariableList.get(i), independantVariableColumn.get(i));
            data.setNode(new ErrorBar(7));
            series.getData().add(data);
          }
          
          independentVariableList.put(independentAxis, series);
          graph.getData().add(series);
        } else {
          graph.getData().remove(independentVariableList.get(independentAxis));
        }
      }
    };
  }
}
