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
     * ArrayList size can be expand at constant time and add for n elements at O(n) time, which is why it is choosen as the implementation. 
     * @param fileName file name of the scientist data csv
     */
    public final void readCsvFile(String fileName)   {    
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);   
        try(BufferedReader input = new BufferedReader( new InputStreamReader(inputStream))){
            String line ="";
            String skipFirstLine = input.readLine();//this is the category line. no point to create this as an object.
            String[] categoryArr = skipFirstLine.split(",");
            setCategoryTableContentForReadFile(categoryArr);
        
            while ((line = input.readLine())!= null){
                //the problem of , enclosed in "" must be resolved here. 
                String[] arr =line.split(","); 
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
     * Gets the processed list of space objects.
     * @return the list of space objects
     */
    public final ArrayList<Cve> getProccessedList(){
        processList();
        return this.processedFile;
    }

    /** Processes the file content list to create space objects for each line and update the processed file list.  */
    private void processList(){
        //spaceSth has datatype of Superclass SpaceObject, but referencing to subclass object. Method will behaves like a subclass object if invoke. 
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