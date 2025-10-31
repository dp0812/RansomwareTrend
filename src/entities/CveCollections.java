package entities;

import java.util.ArrayList;

import fileReader.Logger;
import fileReader.ReadCveInfo;

public class CveCollections {
    ReadCveInfo cveDataset = new ReadCveInfo();
    ArrayList<Cve> cveArrayList = new ArrayList<>();

    /**
     * @param dataFileName Name of the dataset. Default location is in datasets directory. 
     * @param writeFileName Name of the file to be written to. Default location is outputs directory. 
     */
    public final void setUpCve(String dataFileName, String writeFileName){
        cveDataset.readCsvFile(dataFileName);
        cveArrayList = cveDataset.getCveArrayList();
        Logger.writeVisualizationData(cveDataset.getWritableArrayList(), cveDataset.getCategoryLine(),writeFileName);
    }
    public ArrayList<Cve> getCveArrayList(){
        return this.cveArrayList;
    }
}
