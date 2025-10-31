package utilities;

import java.util.ArrayList;


public class ConsoleUI {
    
    public static <T> void printArrayList(ArrayList<T> objectArrList){
        for (T o : objectArrList){
            System.out.println(o);
            System.out.println("---------------------------");
        }
    }

}
