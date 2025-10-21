package utilities;

import java.util.ArrayList;

public class Parser {
    
    /**
     * Parsing input from file, delimited by delimiter parameter, ignoring all delimiters enclosed in double quotes "". 
     * @param input some String input, presumably read from file.
     * @param delimiter a character delimiter. Example: comma ','
     * @return String array 
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
}
