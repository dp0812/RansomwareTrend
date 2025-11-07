package simulation;

import java.util.Optional;

import utilities.Parser;
import utilities.ConsoleUI;
import entities.CveCollections;
import entities.MalwareCollections;
/**
 * This class provide easy access to process the data and outputs them in file, with minimal user input required. 
 */
public class Simulation {
    MalwareCollections malwareManager = new MalwareCollections();
    CveCollections cveManager = new CveCollections();

    public Simulation(){}

    public void setUpCve(String testFileName, String writeFileName){
        String testFilePath = Parser.createSuitableFilePath(testFileName, Optional.empty());
        cveManager.setUpCve(testFilePath, writeFileName);
    }
    public void setUpMalware(String testFileName, String writeFileName){
        String testFilePath = Parser.createSuitableFilePath(testFileName, Optional.empty());
        malwareManager.setUpMalware(testFilePath, writeFileName);
    }
    public void run(){
        IO.println("Run.");
        testMalware();
    }

    public void testMalware(){
        malwareManager.lastestSort();   
        malwareManager.writeVisualizationData();
        malwareManager.signatureOccurenceReport("signatureReport.csv");
        malwareManager.fileTypeOccurenceReport("fileTypeReport.csv");
        malwareManager.mimeTypeOccurenceReport("mimeTypeReport.csv");
        cveManager.writeVisualizationData(); //this is testing the cve... which is not needed. 
    }

    public void display(){
        ConsoleUI.printArrayList(malwareManager.getMalwareArrayList());
    }

}
