package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import utilities.Parser;

public class ParserTest {
    LinkedHashMap <String, Integer> inputTestMap = new LinkedHashMap<>();

    @BeforeEach
    public void setUp(){
        IO.println("Setting up inputTestMap.");
        inputTestMap.put("Dang", 5);
        inputTestMap.put("Anny", 10);
        inputTestMap.put("Jinsei", 8);
        inputTestMap.put("Ethan", 9);
    }

    @Test
    public void nonNullSorting(){
        IO.println("Checking sorting with non null.");
        LinkedHashMap <String, Integer> outputTestMap = Parser.sortByValue(inputTestMap);
        int[] values = new int[outputTestMap.size()];
        int i = 0;
        for (Entry<String,Integer> entry : outputTestMap.entrySet()){
            values[i++] = entry.getValue();
        }
        for (int n = 0; n< values.length - 1; n++){
            assertTrue(values[n]>values[n+1]);
        }
    }

    @Test
    public void stableEqualSorting(){
        IO.println("Checking sorting with equal values.");
        inputTestMap.put("Javier", 8);
        LinkedHashMap <String, Integer> outputTestMap = Parser.sortByValue(inputTestMap);
        int[] values = new int[outputTestMap.size()];
        int i = 0;
        for (Entry<String,Integer> entry : outputTestMap.entrySet()){
            values[i++] = entry.getValue();
        }
        for (int n = 0; n< values.length - 1; n++){
            assertFalse(values[n]<values[n+1]);
        }
        //expected order: Anny, Ethan, Jinsei (1st 8), Javier (2nd 8), Dang
        String[] expectedCategory = {"Anny","Ethan","Jinsei", "Javier", "Dang"};
        String[] actualCategory = outputTestMap.keySet().toArray(new String[outputTestMap.size()]);
        assertEquals(
            Parser.flattenStructure(expectedCategory, '?'),
            Parser.flattenStructure(actualCategory,'?')
        );
    }

    @Test
    public void stringConversion(){
        IO.println("Testing string conversion. ");
        StringBuilder category = new StringBuilder();
        StringBuilder values = new StringBuilder();
        char firstDelimiter = ',';
        char secondDelimiter = '/';
        
        for (Entry<String,Integer> entry : inputTestMap.entrySet()){
            category.append(entry.getKey()).append(firstDelimiter);
            values.append(entry.getValue()).append(firstDelimiter);
        }

        category.setLength(category.length()-1);
        values.setLength(values.length()-1);

        String[] testArr = Parser.flattenStructure(inputTestMap, ',');
        String result = Parser.flattenStructure(testArr,'/');

        assertEquals(category.toString(), testArr[0]);
        assertEquals(values.toString(), testArr[1]);
        assertEquals(category.append(secondDelimiter).append(values).toString(), result);

    }

    @AfterEach
    public void tearDown(){
        inputTestMap = null;
    }
    
}
