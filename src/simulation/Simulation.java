package simulation;
import fileReader.Logger;
import fileReader.ReadCveInfo;

import java.util.ArrayList;

import entities.Cve;
public class Simulation {
    ReadCveInfo dataset = new ReadCveInfo();
    

    public Simulation(String testFileName, String writeFileName){
        dataset.readCsvFile(testFileName);
        Logger.writeVisualizationData(dataset.getWritableCveArrayList(), dataset.getCategoryLine(),writeFileName );
    }
    public void run(){
        System.out.println("Run.");
        //printCveList();
        //printStringArr();
    }

    /** Test */
    @SuppressWarnings("unused") private void printStringArr(){
        ArrayList<String[]> arrList = dataset.getFileContent();
        for (String[] arr: arrList){
            for (String sth: arr){
                System.out.println(sth);
            }
            System.out.println("\n");
        }

    }

    /** Test */
    @SuppressWarnings("unused") private void printCveList(){
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
