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
 * A Java program to download a CSV file from a URL and save it to src/datasets
 * The program assumes the target directory 'src/datasets' already exists.
 */
public class CsvSaver {

    private static final String FILE_URL = "https://bazaar.abuse.ch/export/csv/recent";
    private static final String FILE_NAME = "recent.csv";
    private static final String TARGET_DIRECTORY = "src/datasets";

    public static String importCsvFromWeb() {
        try {
            Path targetDir = Paths.get(TARGET_DIRECTORY);
            Path targetFilePath = targetDir.resolve(FILE_NAME);
            //connect.
            URI uriObject = new URI(FILE_URL);
            URL url = uriObject.toURL();
            IO.println("Attempting to download from: " + FILE_URL);
            //Use Files.copy to download. 
            try (InputStream in = url.openStream()) {
                long bytesCopied = Files.copy(in, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
                IO.println("Download successful! Copy: " + bytesCopied + " to required file.");
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