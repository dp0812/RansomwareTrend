package utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Utility class providing static methods for serialization and deserialization of core data structures to and from a String format.<p>
 * The generated String format is intentionally compact and optimized for 
 * program consumption and parsing speed, not for human readability or inspection.
 */
public final class Parser {

    /**
     * Parsing input from given String into a String array, delimited by delimiter parameter, ignoring all delimiters enclosed in double quotes "". 
     * @param input some String input, presumably read from file.
     * @param delimiter a character delimiter. Example: comma ',' file separator - windows '\\', unix '/'
     * @return String array with each element enclosed in delimiter as a separate String.
     */
    public static String[] simpleParser(String input, char delimiter){
        ArrayList<String> result = new ArrayList<>();
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

    /**
     * Create and return a new LinkedHashMap that is sorted in descending order, based on the values mapped to the key.<p>
     * Examples: if inputMap = {Dang=5, Anny=10, Jinsei=8, Ethan=9 } then return = {Anny=10, Ethan=9, Jinsei=8, Dang=5}.<p>
     * This method DOES NOT guarantee the handling of null values in the map. 
     * @param inputMap A LinkedHashMap with no NULL values. 
     * @return a LinkedHashMap, sorted in descending order. 
     */
    public static LinkedHashMap<String,Integer> sortByValue(LinkedHashMap<String,Integer> inputMap){
        List<Map.Entry<String,Integer>> inputList = new ArrayList<>(inputMap.entrySet());
        Comparator<Map.Entry<String,Integer>> mapComparator = new Comparator<Map.Entry<String,Integer>>(){
            public int compare(Map.Entry<String,Integer> o1, Map.Entry<String,Integer> o2){
                return (o1.getValue()).compareTo(o2.getValue());
            }
        };
        Collections.sort(inputList, mapComparator.reversed());
        LinkedHashMap<String, Integer> sortLinkedHashMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : inputList) sortLinkedHashMap.put(entry.getKey(), entry.getValue());
        return sortLinkedHashMap;
    }

    /**
     * Transform a LinkedHashMap into a string array, the first element is all the key combined, 
     * the second element is all the value combined, both delimited by the delimiter. <p>
     * This method guarantees an array of String with the components properly delimited by the delimiter, with no trailing delimiter.
     * @param someMap LinkedHashMap containing the data with name and occurence. 
     * @param delimiter delimiter between each items
     * @return String array representation of the items. 
     */
    public static String[] flattenStructure(LinkedHashMap<String, Integer> someMap, char delimiter){
        String[] arr = new String[2];
        StringBuilder header = new StringBuilder(77);
        StringBuilder info = new StringBuilder(77);

        someMap.forEach((key,values) -> {
            header.append(key).append(delimiter); //.trim()
            info.append(values).append(delimiter);
        });

        if (header.length() > 0) { //trim the last delimiter. 
            //setLength method discard all characters in the original String whose index>newLength. 
            header.setLength(header.length() - 1); 
            info.setLength(info.length() - 1);
            arr[0] = header.toString();
            arr[1] = info.toString();
        }
        return arr;

    }

    /**
     * Composed the elements of the array to a single String, properly delimited by the delimiter, 
     * in the exact order of the original array, with no trailing delimiter. 
     * @param someArray A typical String array, 
     * @param delimiter Any delimiter
     * @return A string composed of the element in the array, in that exact order, delimited by the delimiter
     */
    public static String flattenStructure(String[] someArray, char delimiter){
        StringBuilder info = new StringBuilder(77);
        Arrays.stream(someArray).forEach(in -> {info.append(in).append(delimiter);});
        //setLength method discard all characters in the original String whose index>newLength. 
        if (info.length() > 0) info.setLength(info.length() - 1); //trim the last delimiter. 
        return info.toString(); 
    }

    /**
     * Produced a properly formated filePath, inside the default directory. If caller supply an Optional.empty() argument for directory, the default will be datasets. <p>
     * This method DOES NOT guarantee the correctness of the defaultDirectory parameter - that is on the caller. 
     * Attempt to create a file (and directory) if the specified file is not found.  
     * @param optionalDefaultDirectory directory where the file is located. 
     * @param fileName fileName to be processed to standard format. 
     * @return A standard format file path which delimiter is dependent of the system running.
     */
    public static String createSuitableFilePath(String fileName, Optional<String> optionalDefaultDirectory){
        String directory  = "datasets";
        if (optionalDefaultDirectory.isPresent()) directory = optionalDefaultDirectory.get();
        String defaultFilePath = directory + File.separator + fileName;
        String[] tempDir = Parser.simpleParser(fileName, File.separatorChar);
        //if more than just the file name and file separator exist, override the default settings.
        if (tempDir.length > 1) defaultFilePath = fileName;

        //Creation of the file if the file (and its path) does not exist. 
        Path path = Path.of(defaultFilePath);
        Path directoryPath = path.getParent();
        if (directoryPath == null) return defaultFilePath;
        try {
            // Creates the directory and any nonexistent parent directories
            Files.createDirectories(directoryPath); 
        } catch (IOException e) {
            System.out.println("Fail to create directory '" + directoryPath + "' due to: " + e);
            return defaultFilePath; 
        }

        return defaultFilePath;
    }
}
