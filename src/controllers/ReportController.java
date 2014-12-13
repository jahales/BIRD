package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.transform.Scale;
import javafx.util.Callback;
import models.report.DataTable;
import models.report.DataTable.RowFormatError;
import models.report.ErrorBar;

/**
 * Controller for Report view. Gets axis choices from user and displays results on graph.
 *
 * @author Brian Woodruff
 *
 */
public class ReportController extends BaseController {

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

  @FXML
  Button printButton;

  Map<String, XYChart.Series<Number, Number>> independentVariableList = new HashMap<String, XYChart.Series<Number, Number>>();
  List<Number> dependentVariableList = new ArrayList<Number>();
  DataTable table = null;
  private static final int MAX_NODES_IN_GRAGH = 50;

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

    ObservableList<String> names = FXCollections.observableArrayList();

    for (String name : table.getColumnNames()) {
      if (!name.contains("Error")) {
        names.add(name);
      }
    }

    // X axis
    xAxisChoices.getItems().addAll(names);
    xAxisChoices.setValue(names.get(0));
    xAxisChoices.getSelectionModel().selectedItemProperty()
        .addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
          dependentVariableList = table.getColumn(newValue);
          independentVariableList.clear();
          graph.getData().clear();
          yAxisChoices.getItems().add(oldValue);
          yAxisChoices.getItems().remove(newValue);
        });

    // Y axis
    yAxisChoices.setItems(names);
    yAxisChoices.setCellFactory(checkBoxFactory);

    dependentVariableList = table.getColumn(xAxisChoices.getValue());
    yAxisChoices.getItems().remove(xAxisChoices.getValue());

    // When the user clicks the print button the webview node is printed
    printButton.setOnAction(aEvent -> {
      print(getView());
    });
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
        List<Number> errorColumn = table.getColumn(independentAxis + "Error");

        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        series.setName(independentAxis);

        double skips = (double) dependentVariableList.size() / (double) MAX_NODES_IN_GRAGH;
        for (double i = 0; i < dependentVariableList.size(); i += skips) {
          XYChart.Data<Number, Number> data;
          data = new XYChart.Data<Number, Number>(dependentVariableList.get((int) i),
              independantVariableColumn.get((int) i));
          if (errorColumn != null) {
            data.setNode(new ErrorBar(errorColumn.get((int) i).doubleValue()));
          }
          series.getData().add(data);
        }

        independentVariableList.put(independentAxis, series);
        graph.getData().add(series);
      } else {
        graph.getData().remove(independentVariableList.get(independentAxis));
      }
    };
  }

  private void print(final Node node) {
    // Attempt to select a printer that can print to PDF files
    Printer printer = Printer.getDefaultPrinter();

    for (Printer p : Printer.getAllPrinters()) {
      if (p.getName().contains("PDF") || p.getName().contains("pdf")) {
        printer = p;
        break;
      }
    }

    PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
    double scaleX = pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
    double scaleY = pageLayout.getPrintableHeight() / node.getBoundsInParent().getHeight();
    node.getTransforms().add(new Scale(scaleX, scaleY));
    
    PrinterJob job = PrinterJob.createPrinterJob(printer);
    if (job != null) {
      boolean success = job.printPage(node);
      if (success) {
        job.endJob();
      }
    }
    
    node.getTransforms().clear();
  }
}
