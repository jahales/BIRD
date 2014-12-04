package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.report.DataTable;
import models.report.DataTable.RowFormatError;
import models.report.ErrorBar;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * Controller for Report view. Gets axis choices from user and displays results
 * on graph.
 *
 * @author Brian Woodruff
 *
 */
public class ReportController extends BaseController {
  DataTable table = new DataTable();

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
  
  public ReportController(DataTable table) {
    this.table = table;
  }

  /**
   * initialize is called during FXMLoader call
   * @throws RowFormatError 
   */
  public void initialize() throws RowFormatError {
    List<String> words = FXCollections.observableArrayList("List", "Book", "Deer", "Rain", "Lint", "Fear");
    int rows = new Random().nextInt(1000);
    
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
    
    yAxisChoices.setCellFactory(CheckBoxListCell.forListView(new Callback<String, ObservableValue<Boolean>>() {

      @Override
      public ObservableValue<Boolean> call(String param) {
        ObservableValue<Boolean> thing = new SimpleBooleanProperty(false);
        thing.addListener(new ChangeListener<Boolean>() {
          @Override
          public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
              Boolean newValue) {
            System.out.println("Girl, you've changed " + param);
          }
        });
        return thing;
      }
    }));
    
    // X axis
    xAxisChoices.setItems(FXCollections.observableArrayList(table.getColumnNames()));
    xAxisChoices.setOnMouseClicked(doubleClick);

    // Y axis
    yAxisChoices.setItems(FXCollections.observableArrayList(table.getColumnNames()));
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
  private void setEnterEvent(TextField boundedAxis, ObservableList<String> axisListView) {
    boundedAxis.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
          // Update axis bounds
        }
      }
    });
  }

  private EventHandler<MouseEvent> doubleClick = new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent event) {
      if (event.getClickCount() == 2) {
        String xAxisSelected = xAxisChoices.selectionModelProperty().get().getSelectedItem();
        String yAxisSelected = yAxisChoices.selectionModelProperty().get().getSelectedItem();
        if (xAxisSelected == yAxisSelected ) {
          return;
        }
        
        List<Number> xAxisList = table.getColumn(xAxisSelected);
        List<Number> yAxisList = table.getColumn(yAxisSelected);

        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        series.setName(xAxisSelected);

        for (int i = 0; i < xAxisList.size(); i++) {
          XYChart.Data<Number, Number> data;
          data = new XYChart.Data<Number, Number>(xAxisList.get(i), yAxisList.get(i));
          data.setNode(new ErrorBar(7));
          series.getData().add(data);
        }

        graph.getData().add(series);
      }
    }
  };
}
