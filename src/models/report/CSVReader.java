package models.report;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import models.report.DataTable.RowFormatError;

/**
 * A csv reader that can read or write contents to a file. Contents may be
 * edited. This is a generic class. I would suggest using strings but ints or
 * whatever can be used.
 * 
 * @author Brian Woodruff
 *
 */
public class CSVReader {
    /**
     * Loads a CSV file and returns a data table representation of the file.
     * 
     * @param file
     * @return DataTable
     * @throws IOException 
     */
    public static DataTable loadCSV(String file) throws IOException {
	DataTable dataTable = new DataTable();
	BufferedReader bf = new BufferedReader(new FileReader(file));
	String delimiter = ",";
	String line;
	
	// Read column headers
	if ((line = bf.readLine()) != null) {
	    line.split(delimiter);
	    dataTable.setColumnNames(Arrays.asList(line.split(delimiter)));
	}
	
	// Read the rest, row by row
	while ((line = bf.readLine()) != null) {
	    try {
		dataTable.addRow(stringListToNumberList(Arrays.asList(line.split(delimiter))));
	    } catch (RowFormatError e) {
		e.printStackTrace();
	    }
	}
	
	bf.close();
	return dataTable;
    }
    
    /**
     * Convert a list of strings to a list of numbers
     * 
     * @param stringList
     * @return numberList
     */
    private static List<Number> stringListToNumberList(List<String> stringList) {
	List<Number> numberList = new ArrayList<Number>();
	for (String item : stringList) {
	    numberList.add(Double.parseDouble(item));
	}
	return numberList;
    }
}








