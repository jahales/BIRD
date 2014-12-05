package models.report;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javafx.collections.FXCollections;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CSVReaderTest {

  @Test
  public void read_CSV_File() throws Exception {
    String file = "Column 1,Column 2,Column 3\n" +
                  "1.0,2.0,3.0\n" +
                  "4.0,5.0,6.0\n" +
                  "7.0,8.0,9.0\n";
    List<String> columNames = FXCollections.observableArrayList("Column 1", "Column 2", "Column 3");
    List<Number> row0 = FXCollections.observableArrayList(1.0, 2.0, 3.0);
    List<Number> row1 = FXCollections.observableArrayList(4.0, 5.0, 6.0);
    List<Number> row2 = FXCollections.observableArrayList(7.0, 8.0, 9.0);
    InputStream isr = new ByteArrayInputStream(file.getBytes());
    CSVReader csvReader = new CSVReader();
    DataTable table = csvReader.deserialize(isr);
    
    Assert.assertEquals(table.getColumnNames().equals(columNames), true);
    Assert.assertEquals(table.getRow(0).equals(row0), true);
    Assert.assertEquals(table.getRow(1).equals(row1), true);
    Assert.assertEquals(table.getRow(2).equals(row2), true);
  }

  @Test
  public void write_CSV_File() throws Exception {
    DataTable table = new DataTable();
    table.addColumn("Column 1");
    table.addColumn("Column 2");
    table.addColumn("Column 3");
    table.addRow(FXCollections.observableArrayList(1, 2, 3));
    table.addRow(FXCollections.observableArrayList(4.0, 5.0, 6.0));
    table.addRow(FXCollections.observableArrayList(7, 8, 9));

    String file = "Column 1,Column 2,Column 3\n" +
                  "1,2,3\n" +
                  "4.0,5.0,6.0\n" +
                  "7,8,9\n";
    OutputStream os = new OutputStream() {
      private StringBuilder string = new StringBuilder();
      @Override
      public void write(int b) throws IOException {
        this.string.append((char) b);
      }
      
      public String toString() {
        return this.string.toString();
      }
    };
    
    CSVReader csvReader = new CSVReader();
    csvReader.serialize(table, os);
    
    Assert.assertEquals(os.toString().equals(file), true);
  }
}
