package simulation;
import fileReader.CsvReader;
import fileReader.Logger;
import fileReader.ReadCveInfo;
import fileReader.ReadMalwareInfo;

import java.util.ArrayList;

import entities.Cve;
import entities.Malware;

public class Simulation {
    ReadCveInfo dataset = new ReadCveInfo();
    ReadMalwareInfo malwareDataset = new ReadMalwareInfo();

    public Simulation(){}

    public void setUpCve(String testFileName, String writeFileName){
        dataset.readCsvFile(testFileName);
        Logger.writeVisualizationData(dataset.getWritableArrayList(), dataset.getCategoryLine(),writeFileName);
    }
    public void setUpMalware(String testFileName, String writeFileName){
        malwareDataset.readCsvFile(testFileName);
        Logger.writeVisualizationData(malwareDataset.getWritableArrayList(), malwareDataset.getCategoryLine(),writeFileName);
    }
    public void run(){
        System.out.println("Run.");
        testCve();
        testMalware();

    }
    public void testCve(){
        printStringArr(dataset);
        printCveList();
    }
    public void testMalware(){
        printStringArr(malwareDataset);
        printMalwareList();
    }

    /** Test */
    private void printStringArr(CsvReader reader){
        ArrayList<String[]> arrList = reader.getFileContent();
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
            String[] iArr = somecve.getInfoArr();
            for (String s: iArr){
                System.out.println(s);
            }
            System.out.println();
        }
    }

        /** Test */
    private void printMalwareList(){
        System.out.println("Looking at MalwareList\n\n");
        ArrayList<Malware> malwareArrList = malwareDataset.getMalwareArrayList();
        String[] cats = Malware.getVisualizationInfoArray(malwareArrList.get(0));
        for (String c: cats) System.out.print(c + " , ");
        System.out.println();
        for (Malware someMalware : malwareArrList){
            String[] iArr = someMalware.getInfoArr();
            for (String s: iArr){
                System.out.println(s);
            }
            System.out.println();
        }
    }
}
