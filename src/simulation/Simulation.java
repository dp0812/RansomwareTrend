package simulation;
import fileReader.ReadCveInfo;

import java.util.ArrayList;

import entities.Cve;
public class Simulation {
    ReadCveInfo dataset = new ReadCveInfo();

    public Simulation(String testFileName){
        dataset.readCsvFile(testFileName);
    }
    public void run(){
        printCveList();
    }

    /** Test */
    @SuppressWarnings("unused") private void printStringArr(){
        ArrayList<String[]> arrList = dataset.getfileContent();
        for (String[] arr: arrList){
            for (String sth: arr){
                System.out.println(sth);
            }
            System.out.println("\n");
        }

    }

    /** Test */
    private void printCveList(){
        ArrayList<Cve> cveArrList = dataset.getCveArrayList();
        String[] cats = Cve.getVisualizationInfoArray(cveArrList.get(0));
        for (String c: cats) System.out.print(c + " , ");
        System.out.println();

        for (Cve somecve : cveArrList){
            //Cve.printInfo(somecve);
            String[] iArr = somecve.getInfoArr();
            for (String s: iArr){
                System.out.println(s);
            }
            System.out.println();
        }
    }
}
