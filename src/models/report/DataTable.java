package models.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generic Data Table.
 * 
 * @author Brian Woodruff
 *
 */
public class DataTable {
  /**
   * Exception class of my own. Don't know if I really want it though.
   * 
   * @author Brian Woodruff
   *
   */
  public class RowFormatError extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Make an error with whatever message you want.
     * 
     * @param error
     */
    public RowFormatError(String error) {
      super(error);
    }
  }

  private Map<String, List<Number>> columnMap = new HashMap<String, List<Number>>();
  private List<String> columnNames = new ArrayList<String>();
  private List<List<Number>> data = new ArrayList<List<Number>>(); // List of rows

  /**
   * Add a column to the table.
   * 
   * @param columnName
   */
  public void addColumn(String columnName) {
    columnNames.add(columnName);
    List<Number> column = new ArrayList<Number>();
    for (List<Number> row : data) {
      row.add(0);
      column.add(row.get(row.size() - 1));
    }
    columnMap.put(columnName, column);
  }

  /**
   * Add a row to the data table. Throws error if length does not match column
   * header length.
   * 
   * @param row
   * @throws RowFormatError
   */
  public void addRow(List<Number> row) throws RowFormatError {
    if (row.size() == columnNames.size()) {
      data.add(row);
      for (int i = 0; i < columnNames.size(); i++) {
        columnMap.get(columnNames.get(i)).add(row.get(i));
      }
    } else {
      throw new RowFormatError("Size of row does match number of columns defined.");
    }
  }

  /**
   * @return columnMap
   */
  public Map<String, List<Number>> getColumnMap() {
    return columnMap;
  }

  /**
   * @return columnNames
   */
  public List<String> getColumnNames() {
    return columnNames;
  }

  /**
   * Get a column by name.
   * 
   * @param columnName
   * @return column
   */
  public List<Number> getColumn(String columnName) {
    return columnMap.get(columnName);
  }

  /**
   * @return data
   */
  public List<List<Number>> getData() {
    return data;
  }
}
