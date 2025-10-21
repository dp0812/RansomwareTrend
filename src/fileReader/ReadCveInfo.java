package fileReader;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;

import entities.Cve;

/**
 * Reads data from csv, created cve objects, and provides processed list of file lines.
 */
public class ReadCveInfo implements CsvReader{
    /* stores any file lines that are read */
    private ArrayList <String[]> fileContent = new ArrayList<>();
    /* stores cve objects taken from the file read */
    private ArrayList <Cve> processedFile = new ArrayList<>();
    /* for creating the objects no matter how much the order of the columns in the csv has changed */
    private Hashtable <String, int[]> categoryTable = new Hashtable<>();

    public ReadCveInfo(){}

    /**
     * Accepts a file name, read from said csv file using BufferedReader and add the content to ArrayList of String array. 
     * @param fileName file name of the data csv
     */
    public final void readCsvFile(String fileName)   {    
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);   
        try(BufferedReader input = new BufferedReader( new InputStreamReader(inputStream))){
            String line ="";
            String skipFirstLine = input.readLine(); //this is the category line
            String[] categoryArr = simpleParser(skipFirstLine, ',');
            setCategoryTableContentForReadFile(categoryArr);
        
            while ((line = input.readLine())!= null){
                String[] arr = simpleParser(line, ',');
                fileContent.add(arr);
            }

        } catch (IOException error){
            System.out.println("Error: " + error);
        }
    }
    
    /** 
     * @return the file content list
     */
    public final ArrayList<String[]> getfileContent(){
        return this.fileContent;
    }

    /** 
     * Gets the processed list of cve objects.
     * @return the ArrayList of cve objects
     */
    public final ArrayList<Cve> getProccessedList(){
        processList();
        return this.processedFile;
    }

    /**
     * Parsing input from file, delimited by delimiter parameter, ignoring all delimiters enclosed in double quotes. 
     * @param input some String input, presumably read from file.
     * @param delimiter a character delimiter. Example: comma ','
     * @return  String array 
     */
    private String[] simpleParser(String input, char delimiter){
        ArrayList<String> result = new ArrayList<String>();
        int start = 0;
        boolean inQuotes = false;
        for (int current = 0; current < input.length(); current++) {
            if (input.charAt(current) == '\"') inQuotes = !inQuotes; // toggle state
            else if (input.charAt(current) == delimiter && !inQuotes) {
                result.add(input.substring(start, current));
                start = current + 1;
            }
        }
        result.add(input.substring(start));
        String[] arr = new String[result.size()];
        return result.toArray(arr);
    }

    /** Processes the file content list to create cve objects for each line and update the processed file list.  */
    private void processList(){
        //cveSth has datatype of Superclass cveObject, but referencing to subclass object. Method will behaves like a subclass object if invoke. 
        for (String[] someLine : fileContent){
            Cve cveObject = new Cve();
            createCveObject(cveObject, someLine);
            processedFile.add(cveObject);
        }
    }

    /**
     * Return the Hashtable with category:{index of data} with category as a string, and 
     * index of data is the index of required data in the rows excluding the category row in the csv. 
     * In order to use this: categoryTable.get(key)[0] will return the correct index in the csv
     * @param arr string array representation of the category name in the csv
     */
    private void setCategoryTableContentForReadFile(String[] arr){
        int index  = 0;
        for (String category: arr){
            int[] temp2 = {index};
            this.categoryTable.put(category, temp2);
            index+=1;
        }
    }

    /** 
     * Creates the Cve objects with the given info from the file.
     * @param cveObject the Cve object to update 
     * @param someLine the file line stored as a string array
     */
    private void createCveObject(Cve cveObject, String[] someLine){
        //TODO: call the long constructor here. 
    }
}