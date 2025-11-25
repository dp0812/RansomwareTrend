package simulation;

import fileReader.CsvSaver;
import entities.MalwareCollections;

/**
 * This class provide easy access to process the data and outputs them in file, with minimal user input required. 
 * Calling the run method itself is enough. 
 */
public class Simulation {
    /** This is an interface that simplify the underlying process of analyzing malware. */
    MalwareCollections malwareManager = new MalwareCollections();

    public Simulation(){}
    /** Calling this method to set up and run every Java source code module. */
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
        malwareManager.setUpMalware(fetchedInputFile, "formattedMalwareInfo.csv");
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
}
