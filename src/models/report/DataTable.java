package models.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generic Data Table. Stores values as {@link Number}
 *
 * @author Brian Woodruff
 *
 */
public class DataTable {
  /**
   *
   * @author Brian Woodruff
   *
   */
  public class RowFormatError extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * @param error
     *          error message
     */
    public RowFormatError(String error) {
      super(error);
    }
  }

  private Map<String, List<Number>> columnMap = new HashMap<String, List<Number>>();
  private List<String> columnNames = new ArrayList<String>();
  private List<List<Number>> data = new ArrayList<List<Number>>(); // List of

  // rows

  /**
   * Add a column to the table.
   *
   * @param columnName
   *          name of new column
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
   *          a list of {@link Number}
   * @throws RowFormatError
   *           row length doesn't match
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
   * @return a map of column name to column list
   */
  public Map<String, List<Number>> getColumnMap() {
    return columnMap;
  }

  /**
   * @return list of names for the columns
   */
  public List<String> getColumnNames() {
    return columnNames;
  }

  /**
   * Get a column by name.
   *
   * @param columnName
   *          name of column
   * @return the column as a list
   */
  public List<Number> getColumn(String columnName) {
    return columnMap.get(columnName);
  }

  /**
   * Get a row by row number.
   *
   * @param row
   *          row number
   * @return the row as a list
   */
  public List<Number> getRow(int row) {
    return data.get(row);
  }

  /**
   *
   * @return number of rows
   */
  public int getRows() {
    return data.size();
  }

  /**
   * @return the internal representation of the data
   */
  public List<List<Number>> getData() {
    return data;
  }
}
