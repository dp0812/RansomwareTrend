package simulation;

import java.util.Optional;

import fileReader.CsvSaver;
import utilities.Parser;
import entities.CveCollections;
import entities.MalwareCollections;
/**
 * This class provide easy access to process the data and outputs them in file, with minimal user input required. 
 * Calling the run method itself is enough. 
 */
public class Simulation {
    MalwareCollections malwareManager = new MalwareCollections();
    CveCollections cveManager = new CveCollections();

    public Simulation(){}

    public void run(){
        IO.println("Run.");
        setUpMalwareExpress();
        processMalware();
    }

    /**
     * Vanila user experience set up method. 
     * Automatically fetch from the source. 
     * Please do not run this more than 1 times every 5 minutes to comply with data provider policy. 
     */
    public void setUpMalwareExpress(){
        String fetchedInputFile = CsvSaver.importCsvFromWeb();
        String testFilePath = Parser.createSuitableFilePath(fetchedInputFile, Optional.empty());
        malwareManager.setUpMalware(testFilePath, "formattedMalwareInfo.csv");
    }

    /**
     * Generated report based on the data given in src/datasets folder.
     * The output is located in the outputs folder.
     */
    public void processMalware(){
        malwareManager.lastestSort();
        IO.println("Malware write time = " + malwareManager.writeVisualizationData() + "ms");   
        malwareManager.signatureOccurenceReport("signatureReport.csv");
        malwareManager.fileTypeOccurenceReport("fileTypeReport.csv");
        malwareManager.mimeTypeOccurenceReport("mimeTypeReport.csv");
    }

    /**
     * This is only kept for legacy reason. 
     * @Deprecated  
     * @param testFileName
     * @param writeFileName
     */
    public void setUpCve(String testFileName, String writeFileName){
        String testFilePath = Parser.createSuitableFilePath(testFileName, Optional.empty());
        cveManager.setUpCve(testFilePath, writeFileName);
    }
}
