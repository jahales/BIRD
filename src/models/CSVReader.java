package models;

import java.io.BufferedReader;
import java.util.List;

/**
 * A csv reader that can read or write contents to a file. Contents may be
 * edited. This is a generic class. I would suggest using strings but ints or
 * whatever can be used.`
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
     * Returns the nth column. Does not include header name.
     * 
     * @param columnNumber
     * @return column
     */
    public List<Type> getColumn(int columnNumber) {
	return null;
    }

    /**
     * Returns the headers.
     * 
     * @return headers
     */
    public List<Type> getHeaders() {
	return headers;
    }

    /**
     * Return the nth row. 0 is first row of data, not the headers. Use
     * getHeaders for that.
     * 
     * @param rowNumber
     * @return row
     */
    public List<Type> getRow(int rowNumber) {
	return null;
    }

    /**
     * Read from a csv file.
     * 
     * @param fileName
     */
    public void importFromFile(String fileName) {

    }

    /**
     * Insert a row. Throw error if length doesn't match headers.
     * 
     * @param row
     */
    public void insertRow(List<Type> row) {

    }

    /**
     * Write contents to a csv file.
     * 
     * @param fileName
     */
    public void writeToFile(String fileName) {

    }
}
