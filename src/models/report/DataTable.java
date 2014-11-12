package models.report;

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

  private Map<String, List<Number>> columnMap;
  private List<String> columnNames;
  private List<List<Number>> data; // List of rows

  /**
   * Add a column to the table.
   * 
   * @param columnName
   */
  public void addColumn(String columnName) {
    columnNames.add(columnName);
    for (List<Number> row : data) {
      row.add(0);
    }
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
   * @return data
   */
  public List<List<Number>> getData() {
    return data;
  }
}
