package simulation;

import utilities.ConsoleUI;
import utilities.Parser;
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
        testMalware();
        //System.out.println(malwareManager.occurenceReport());
        //display();
    }

    public void testMalware(){
        malwareManager.lastestSort();   
        malwareManager.writeVisualizationData();
        cveManager.writeVisualizationData();
        System.out.println(Parser.flattenStructure(malwareManager.signatureOccurenceReport(),','));
        System.out.println(Parser.flattenStructure(malwareManager.fileTypeOccurenceReport(), ','));
        System.out.println(Parser.flattenStructure(malwareManager.mimeTypeOccurenceReport(), ','));

    }

    public void display(){
        ConsoleUI.printArrayList(malwareManager.getMalwareArrayList());
        //ConsoleUI.printArrayList(cveManager.getCveArrayList());
    }

}
