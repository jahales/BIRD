package models;

import java.io.BufferedReader;
import java.util.List;

/**
 * A csv reader that can read or write contents to a file. Contents may be
 * edited. This is a generic class. I would suggest using strings but ints or
 * whatever can be used.
 * 
 * @author Brian Woodruff
 *
 * @param <Type>
 */
public class CSVReader<Type> {
    List<List<Type>> contents;
    List<Type> headers;
    BufferedReader reader;

    /**
     * Loads a CSV file and returns a data table representation of the file.
     * 
     * @param file
     * @return DataTable
     */
    public DataTable<Type> loadCSV(String file) {
	return null;
    }
}
