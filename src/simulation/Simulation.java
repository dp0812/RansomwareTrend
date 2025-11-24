package simulation;

import java.util.Optional;

import fileReader.CsvSaver;
import utilities.Parser;
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
    /**
     * Vanila user experience set up method (Stable version)
     * @param inputCsvFile the name of the input file, INCLUDING the file extension.
     */
    public void setUpMalwareDefault(String inputCsvFile){
        String testFilePath = Parser.createSuitableFilePath(inputCsvFile, Optional.empty());
        malwareManager.setUpMalware(testFilePath, "formattedMalwareInfo.csv");
    }

    /**
     * Vanila user experience set up method (WIP)
     * Automatically fetch from the source. 
     * Please do not run this more than 1 times every 5 minutes to comply with data provider policy. 
     */
    public void setUpMalwareExpress(){
        String fetchedInputFile = CsvSaver.importCsvFromWeb();
        String testFilePath = Parser.createSuitableFilePath(fetchedInputFile, Optional.empty());
        malwareManager.setUpMalware(testFilePath, "formattedMalwareInfo.csv");
    }

    public void run(){
        IO.println("Run.");
        processMalware();
    }

    public void processMalware(){
        malwareManager.lastestSort();
        IO.println("Malware write time = " + malwareManager.writeVisualizationData() + "ms");   
        malwareManager.signatureOccurenceReport("signatureReport.csv");
        malwareManager.fileTypeOccurenceReport("fileTypeReport.csv");
        malwareManager.mimeTypeOccurenceReport("mimeTypeReport.csv");
    }
}
