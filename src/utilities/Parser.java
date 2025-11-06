package utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

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

    public static String flattenStructure(String[] someArray, char delimiter){
        StringBuilder info = new StringBuilder(77);
        Arrays.stream(someArray).forEach(in -> {info.append(in).append(delimiter);});
        //setLength method discard all characters in the original String whose index>newLength. 
        if (info.length() > 0) info.setLength(info.length() - 1); //trim the last delimiter. 
        return info.toString(); 
    }
}
