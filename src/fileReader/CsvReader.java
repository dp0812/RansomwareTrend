package fileReader;

import java.util.ArrayList;

/**
 * All class that reads from csv must implement this interface. 
 */
public interface CsvReader {
    /**
     * All class that reads from file must have this.
     * @param filePath read from what file, with the paramater could potentially contains directory. 
     */
    public abstract void readCsvFile(String filePath);
    /** @return Content of file, per line, separate by the delimiter and put in to String array. */
    public abstract ArrayList<String[]> getFileContent();
    /** @return An ArrayList of Object info, which should be used to write to file. */
    public abstract ArrayList<WritableObject> getWritableArrayList();
    /** @return String array containing name of the categories: does not guarantee to remove whitespace. */
    public abstract String[] getCategoryLine();

}
