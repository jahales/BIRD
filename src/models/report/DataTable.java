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
  private List<String> columnNames;
  private Map<String, List<Double>> columnMap;
  private List<List<Number>> data; // List of rows

  /**
   * @return columnNames
   */
  public List<String> getColumnNames() {
    return columnNames;
  }

  /**
   * @param columnNames
   */
  public void setColumnNames(List<String> columnNames) {
    this.columnNames = columnNames;
  }

  /**
   * @return columnMap
   */
  public Map<String, List<Double>> getColumnMap() {
    return columnMap;
  }

  /**
   * @return columns
   */
  public List<List<Number>> getData() {
    return data;
  }

  public void addRow(List<Number> row) throws RowFormatError {
    if (row.size() == columnNames.size()) {
      data.add(row);
    } else {
      throw new RowFormatError("Size of row does match number of columns defined.");
    }
  }

  public class RowFormatError extends Exception {
    private static final long serialVersionUID = 1L;

    public RowFormatError(String error) {
      super(error);
    }
  }
}
