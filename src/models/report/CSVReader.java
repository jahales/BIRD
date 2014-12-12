package models.report;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import models.ISerializer;

/**
 * A csv reader that can read or write contents to a file. Contents may be
 * edited. This is a generic class. I would suggest using strings but
 * {@link Number} or whatever can be used.
 *
 * @author Brian Woodruff
 *
 */
public class CSVReader implements ISerializer<DataTable> {

  /**
   * Convert a list of strings to a list of numbers
   *
   * @param stringList
   *          a list of strings to convert to a list of numbers
   * @return a list of numbers
   */
  private static List<Number> stringListToNumberList(List<String> stringList) {
    List<Number> numberList = new ArrayList<Number>();
    for (String item : stringList) {
      numberList.add(Double.parseDouble(item));
    }
    return numberList;
  }

  @Override
  public void serialize(DataTable dataTable, OutputStream outputStream) throws Exception {
    String columnNames = "";
    List<String> columnNameList = dataTable.getColumnNames();
    for (int i = 0; i < columnNameList.size(); i++) {
      columnNames += columnNameList.get(i)
          + (i + 1 < columnNameList.size() ? "," : dataTable.getRows() == 0 ? "" : "\n");
    }
    outputStream.write(columnNames.getBytes(Charset.forName("UTF-8")));
    for (List<Number> row : dataTable.getData()) {
      String rowAsString = "";
      for (int i = 0; i < row.size(); i++) {
        rowAsString += row.get(i)
            + (i + 1 < row.size() ? "," : dataTable.getRows() == i ? "" : "\n");
      }
      outputStream.write(rowAsString.getBytes(Charset.forName("UTF-8")));
    }
  }

  @Override
  public DataTable deserialize(InputStream inputStream) throws Exception {
    BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
    DataTable dataTable = new DataTable();
    String delimiter = ",";
    String line;

    // Read column headers
    if ((line = bf.readLine()) != null) {
      for (String name : line.split(delimiter)) {
        dataTable.addColumn(name);
      }
    }

    // Read the rest, row by row
    while ((line = bf.readLine()) != null) {
      dataTable.addRow(stringListToNumberList(Arrays.asList(line.split(delimiter))));
    }

    bf.close();
    return dataTable;
  }
}
