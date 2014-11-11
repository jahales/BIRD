package models;

import java.util.List;
import java.util.Map;

/**
 * Generic Data Table
 * 
 * @author Brian Woodruff
 *
 * @param <T>
 */
public class DataTable<T> {
    private List<String> columnNames;
    private Map<String, List<Double>> columnMap;
    private List<List<T>> columns;
    private List<List<T>> rows;
    
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
     * @param columnMap
     */
    public void setColumnMap(Map<String, List<Double>> columnMap) {
        this.columnMap = columnMap;
    }
    
    /**
     * @return columns
     */
    public List<List<T>> getColumns() {
        return columns;
    }
    
    /**
     * @param columns
     */
    public void setColumns(List<List<T>> columns) {
        this.columns = columns;
    }
    
    /**
     * @return rows
     */
    public List<List<T>> getRows() {
        return rows;
    }
    
    /**
     * @param rows
     */
    public void setRows(List<List<T>> rows) {
        this.rows = rows;
    }
}
