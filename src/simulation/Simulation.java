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
        ArrayList<Cve> cveArrList = dataset.getCveArrayList();
        for (Cve somecve : cveArrList){
            Cve.printInfo(somecve);
        }
    }
}
