package fileReader;
/**
 * All class that reads from csv must implement this interface. 
 */
public interface CsvReader {
    /**
     * All class that reads from csv must have this.
     * @param fileName read from what file.
     */
    public abstract void readCsvFile(String fileName);
}
