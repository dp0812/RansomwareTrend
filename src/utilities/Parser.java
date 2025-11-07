package utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Optional;

public class Parser {
    
    /**
     * Parsing input from given String, delimited by delimiter parameter, ignoring all delimiters enclosed in double quotes "". 
     * @param input some String input, presumably read from file.
     * @param delimiter a character delimiter. Example: comma ',' file separator - windows '\\', unix '/'
     * @return String array with each element enclosed in delimiter as a separate String.
     */
    public static String[] simpleParser(String input, char delimiter){
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

    /**
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
     * Produced a properly formated filePath, inside the default directory.
     * This method DOES NOT guarantee the correctness of the defaultDirectory parameter - that is on the caller. 
     * If caller supply an Optional.empty() argument for directory, the default will be datasets. 
     * @param optionalDefaultDirectory directory where the file is located. 
     * @param fileName fileName to be processed to standard format. 
     * @return a standard format file path which delimiter is dependent of the system running.
     */
    public static String createSuitableFilePath(String fileName, Optional<String> optionalDefaultDirectory){
        String directory  = "datasets";
        if (optionalDefaultDirectory.isPresent()) directory = optionalDefaultDirectory.get();
        String defaultFilePath = directory + File.separator + fileName;
        String[] tempDir = Parser.simpleParser(fileName, File.separatorChar);
        if (tempDir.length > 1) defaultFilePath = fileName;
        return defaultFilePath;
    }
}
