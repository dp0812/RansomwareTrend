package simulation;

import utilities.ConsoleUI;
import utilities.Parser;

import java.util.Optional;

import entities.CveCollections;
import entities.MalwareCollections;

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
        cveManager.writeVisualizationData();
        IO.println(Parser.flattenStructure(malwareManager.signatureOccurenceReport("signatureReport.csv"),','));
        IO.println(Parser.flattenStructure(malwareManager.fileTypeOccurenceReport("fileTypeReport.csv"), ','));
        IO.println(Parser.flattenStructure(malwareManager.mimeTypeOccurenceReport("mimeTypeReport.csv"), ','));

    }

    public void display(){
        ConsoleUI.printArrayList(malwareManager.getMalwareArrayList());
    }

}
