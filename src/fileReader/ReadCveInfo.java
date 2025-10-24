package fileReader;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import entities.Cve;
import utilities.Parser;

/**
 * Reads data from csv, created cve objects, create 3 ArrayList representation: 
 * one contains the cve objects, 
 * one contains String array form of the dataset,
 * one contains the writable objects (use in writing to file).
 */
public class ReadCveInfo implements CsvReader{
    /** stores any file lines that are read */
    private ArrayList <String[]> fileContent = new ArrayList<>();
    /** stores cve objects taken from the file read */
    private ArrayList <Cve> cveArrayList = new ArrayList<>();
    /** stores writable version of cve objects taken from the file read */
    private ArrayList <WritableObject> writableCveArrayList = new ArrayList<>();
    /** for creating the objects no matter how much the order of the columns in the csv has changed */
    private LinkedHashMap <String, Integer> categoryTable = new LinkedHashMap<>();

    /**
     * Accepts a file name, read from said csv file using BufferedReader and add the content to ArrayList of String array. 
     */
    public final void readCsvFile(String filePath)   {    
        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
            BufferedReader input = new BufferedReader( new InputStreamReader(inputStream))){
            String line ="";
            String categoryLine = input.readLine(); //this is the category line
            String[] categoryArr = Parser.simpleParser(categoryLine, ',');
            setCategoryTableContentForReadFile(categoryArr);
        
            while ((line = input.readLine())!= null){
                String[] arr = Parser.simpleParser(line, ',');
                fileContent.add(arr);
            }

        }catch (NullPointerException errNull) {
            System.out.println("Unintialized resouce for InputStream: " + errNull);
        }catch (IOException errIO){
            System.out.println("Failed to open file: " + errIO);
        }
    }
    
    /** Simple per line information from file. */
    public final ArrayList<String[]> getFileContent(){
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

    /** Gets the writable list of writable cve objects. */
    public final ArrayList<WritableObject> getWritableArrayList(){
        processList();
        return this.writableCveArrayList;
    }
    /** Gets the Cve category line. */
    public final String[] getCategoryLine(){
        return Cve.getVisualizationInfoArray(cveArrayList.get(0));
    }

    /** 
     * Processes the file content list to create cve objects for each line and update 2 arrayList: 
     * cveArrayList and writableCveArrayList
     */    
    private void processList(){
        for (String[] someLine : fileContent){
            Cve cveObject = createCveObject(someLine);
            WritableObject writableCveObject = createCveObject(someLine);
            cveArrayList.add(cveObject);
            writableCveArrayList.add(writableCveObject);

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