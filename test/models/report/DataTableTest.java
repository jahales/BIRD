package models.report;

import java.util.List;

import javafx.collections.FXCollections;
import models.report.DataTable.RowFormatError;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DataTableTest {

  @Test
  public void addColumn() {
    DataTable dataTable = new DataTable();
    dataTable.addColumn("Column 1");
  }

  @Test(expectedExceptions = { RowFormatError.class })
  public void addRowTooManyColumns() throws RowFormatError {
    DataTable dataTable = new DataTable();
    List<Number> list = FXCollections.observableArrayList(1);

    dataTable.addColumn("Column 1");
    dataTable.addColumn("Column 2");

    dataTable.addRow(list);
  }

  @Test(expectedExceptions = { RowFormatError.class })
  public void addRowTooFewColumns() throws RowFormatError {
    DataTable dataTable = new DataTable();
    List<Number> list = FXCollections.observableArrayList(1, 2, 3);

    dataTable.addColumn("Column 1");
    dataTable.addColumn("Column 2");

    dataTable.addRow(list);
  }

  @Test
  public void addRowJustRight() throws RowFormatError {
    DataTable dataTable = new DataTable();
    List<Number> list = FXCollections.observableArrayList(1, 2);

    dataTable.addColumn("Column 1");
    dataTable.addColumn("Column 2");

    dataTable.addRow(list); // Shouldn't throw error in order to pass
  }

  @Test
  public void getColumn() throws RowFormatError {
    DataTable dataTable = new DataTable();
    List<Number> list1 = FXCollections.observableArrayList(1, 2, 3);
    List<Number> list2 = FXCollections.observableArrayList(4, 5, 6);

    dataTable.addColumn("Column 1");
    dataTable.addColumn("Column 2");
    dataTable.addColumn("Column 3");

    dataTable.addRow(list1);
    dataTable.addRow(list2);

    List<Number> column = dataTable.getColumn("Column 1");

    Assert.assertEquals(column.get(0), 1);
    Assert.assertEquals(column.get(1), 4);
  }

  @Test
  public void getColumnMap() {
    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void getColumnNames() throws RowFormatError {
    DataTable dataTable = new DataTable();

    dataTable.addColumn("Column 1");
    dataTable.addColumn("Column 2");
    dataTable.addColumn("Column 3");

    List<String> listNames = dataTable.getColumnNames();

    Assert.assertEquals(listNames.get(0), "Column 1");
    Assert.assertEquals(listNames.get(1), "Column 2");
    Assert.assertEquals(listNames.get(2), "Column 3");
  }

  @Test
  public void getData() {
    throw new RuntimeException("Test not implemented");
  }
}
