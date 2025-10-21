package fileReader;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import entities.Cve;

/**
 * Reads data from csv, created cve objects, create 2 ArrayList representation: 
 * One contains the cve objects, the other contains String array form of the dataset. 
 */
public class ReadCveInfo implements CsvReader{
    /* stores any file lines that are read */
    private ArrayList <String[]> fileContent = new ArrayList<>();
    /* stores cve objects taken from the file read */
    private ArrayList <Cve> cveArrayList = new ArrayList<>();
    /* for creating the objects no matter how much the order of the columns in the csv has changed */
    private LinkedHashMap <String, Integer> categoryTable = new LinkedHashMap<>();

    public ReadCveInfo(){}

    /**
     * Accepts a file name, read from said csv file using BufferedReader and add the content to ArrayList of String array. 
     * @param fileName file name of the data csv
     */
    public final void readCsvFile(String fileName)   {    
   
        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
            BufferedReader input = new BufferedReader( new InputStreamReader(inputStream))){
            String line ="";
            String skipFirstLine = input.readLine(); //this is the category line
            String[] categoryArr = simpleParser(skipFirstLine, ',');
            setCategoryTableContentForReadFile(categoryArr);
        
            while ((line = input.readLine())!= null){
                String[] arr = simpleParser(line, ',');
                fileContent.add(arr);
            }

        }catch (NullPointerException errNull) {
            System.out.println("Unintialized resouce for InputStream: " + errNull);
        }catch (IOException errIO){
            System.out.println("Failed to open file: " + errIO);
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
    public final ArrayList<Cve> getCveArrayList(){
        processList();
        return this.cveArrayList;
    }

    /**
     * Parsing input from file, delimited by delimiter parameter, ignoring all delimiters enclosed in double quotes "". 
     * @param input some String input, presumably read from file.
     * @param delimiter a character delimiter. Example: comma ','
     * @return String array 
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
        for (String[] someLine : fileContent){
            Cve cveObject = createCveObject(someLine);
            cveArrayList.add(cveObject);
        }
    }

    /**
     * Initialize the attribute categoryTable: key: category name, value: index of data.  
     * index of data is the index of required data in the rows excluding the category row in the csv. 
     * categoryTable.get(key) with key as a string (a category) will return the correct index in the csv
     * @param arr string array representation of the category name in the csv
     */
    private void setCategoryTableContentForReadFile(String[] arr){
        int index  = 0;
        for (String category: arr){
            this.categoryTable.put(category, index++);
        }
    }

    /** 
     * Creates the Cve objects with the given info from the file.
     * @param someLine the file line stored as a string array
     */
    private Cve createCveObject(String[] someLine){
        //spelling of keys: 
        //cveID,vendorProject,product,vulnerabilityName,dateAdded,shortDescription,requiredAction,
        //dueDate,knownRansomwareCampaignUse,notes,cwes
        Cve cveObject = new Cve(
            someLine[categoryTable.get("cveID").intValue()],
            someLine[categoryTable.get("vendorProject").intValue()],
            someLine[categoryTable.get("product").intValue()],
            someLine[categoryTable.get("vulnerabilityName").intValue()],
            someLine[categoryTable.get("dateAdded").intValue()],
            someLine[categoryTable.get("shortDescription").intValue()],
            someLine[categoryTable.get("requiredAction").intValue()],
            someLine[categoryTable.get("dueDate").intValue()],
            someLine[categoryTable.get("knownRansomwareCampaignUse").intValue()],
            someLine[categoryTable.get("notes").intValue()],
            someLine[categoryTable.get("cwes").intValue()]
        );
        return cveObject;
    }
}