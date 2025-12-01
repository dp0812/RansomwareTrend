package fileReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * This class pull the data from a hardcoded site in the field FILE_URL to create a csv file called recent.csv . 
 * For the class to behave properly, the datasets directory must exists inside the src directory. 
 * This class is designed to be system independent - the file path will work for both Linux and Windows. 
 */
public class CsvSaver {
    /** Where the info is fetch from. DO NOT fetch more than 1 time every 5 mins. */
    private static final String FILE_URL = "https://bazaar.abuse.ch/export/csv/recent";
    /** Default csv file name. */
    private static final String FILE_NAME = "recent.csv";
    /** Default directory. Make accessible to prevent relying certain methods in ReadMalwareInfo.java */
    public static final String TARGET_DIRECTORY = "datasets";

    /**
     * This fetch information from a site to build a csv file for data processing.
     * After using this method, the caller must impose a wait on the thread to clear the transient lock on the csv file before reading the file.
     * Else, exception will be thrown.
     * @return Name of the file created, with no directory or path attached to it. 
     */
    public static String importCsvFromWeb() {
        try {
            Path targetDir = Paths.get(TARGET_DIRECTORY);
            Path targetFilePath = targetDir.resolve(FILE_NAME);
            //create if needed:
            IO.println("Ensuring target directory exists: " + targetDir.toAbsolutePath());
            Files.createDirectories(targetDir);
            //connect
            URI uriObject = new URI(FILE_URL);
            URL url = uriObject.toURL();
            IO.println("Attempting to download from: " + FILE_URL);
            //use Files.copy to download
            try (InputStream in = url.openStream()) {
                long bytesCopied = Files.copy(in, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
                IO.println("Download successful! Copy: " + bytesCopied + " bytes to required file: " + FILE_NAME);
                IO.println("File saved to: " + targetFilePath.toAbsolutePath());
            }
        } catch (IOException e) {
            IO.println("An error occurred during download or file operations.");
            e.printStackTrace();
        } catch (Exception e) {
            IO.println("An unexpected error occurred.");
            e.printStackTrace();
        }
        return FILE_NAME;
    }
}