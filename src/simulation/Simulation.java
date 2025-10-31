package simulation;

import utilities.ConsoleUI;
import entities.CveCollections;
import entities.MalwareCollections;

public class Simulation {
    MalwareCollections malwareManager = new MalwareCollections();
    CveCollections cveManager = new CveCollections();

    public Simulation(){}

    public void setUpCve(String testFileName, String writeFileName){
        cveManager.setUpCve(testFileName, writeFileName);
    }
    public void setUpMalware(String testFileName, String writeFileName){
        malwareManager.setUpMalware(testFileName, writeFileName);
    }
    public void run(){
        System.out.println("Run.");
        malwareManager.firstSeenSort();   
        malwareManager.writeVisualizationData();
        display();
    }
    public void display(){
        ConsoleUI.printArrayList(malwareManager.getMalwareArrayList());
        //ConsoleUI.printArrayList(cveManager.getCveArrayList());
    }

}
