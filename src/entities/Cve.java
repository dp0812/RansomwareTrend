package entities;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import fileReader.WritableObject;
/**
 * To holds all the possible attribute of a recorded Cve in the KEV list, in the appropriate data type. 
 */
public class Cve implements WritableObject {
    private String cveID; //visual
    private String vendorProject; 
    private String product;
    private String vulnerabilityName; //visual
    private LocalDate dateAdded; //visual
    private String shortDescription; //visual
    private String requiredAction; //visual
    private LocalDate dueDate; 
    private String knownRansomwareCampaignUse;//this is Known or Unknown.
    private String notes;
    /** potentially empty (have yet been mapped) */
    private String cwes; //visual
    
    public Cve(){}
    public Cve(     
        String cveID,
        String vendorProject,
        String product,
        String vulnerabilityName,
        String uDateAdded,
        String shortDescription,
        String requiredAction,
        String uDueDate,
        String knownRansomwareCampaignUse,
        String notes,
        String cwes)
        {
        //parse String to LocalDate objects. 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate formattedDateAdded = LocalDate.parse(uDateAdded, formatter);
        LocalDate formattedDueDate = LocalDate.parse(uDueDate, formatter);

        this.cveID = cveID;
        this.vendorProject = vendorProject;
        this.product = product;
        this.vulnerabilityName = vulnerabilityName;
        this.dateAdded = formattedDateAdded;
        this.shortDescription = shortDescription;
        this.requiredAction = requiredAction;
        this.dueDate = formattedDueDate;
        this.knownRansomwareCampaignUse = knownRansomwareCampaignUse;
        this.notes = notes;
        this.cwes = cwes;
    }

    /**
     * Print both info to be visualized and non visualized info - for testing purpose. 
     * @param object object which info will be printed.
     */
    public static void printInfo(Cve object){
        System.out.println("Fields to be visualized:");
        System.out.println("cveId = " + object.cveID);
        System.out.println("product = " + object.product);
        System.out.println("vulnerabilityName = " + object.vulnerabilityName);
        System.out.println("dateAdded = " + object.dateAdded);
        System.out.println("shortDescription = " + object.shortDescription);
        System.out.println("required actions:\n" + object.requiredAction);
        System.out.println("cwes  = " + object.cwes);

        System.out.println("Fields to NOT be visualized:");
        System.out.println("vendorProject = " + object.vendorProject);
        System.out.println("dueDate = " + object.dueDate);
        System.out.println("notes: " + object.notes);
    }
    /**
     * Get a String array of the category of the objects.
     * @param object extract visualization info from this object.
     * @return String array 
     */
    public static String[] getVisualizationInfoArray(Cve object){
        String[] visualizationInfo = {
            "cveID",
            "vulnerabilityName",
            "dateAdded",
            "shortDescription",
            "requiredAction",
            "knownRansomwareCampaignUse",
            "cwes"        
        };
        return visualizationInfo;
    }
    /**
     * Get a String array that contains, in this exact order: 
     * cveID, vulnerabilityName, dateAdded, shortDescription, 
     * requiredAction, knownRansomwareCampaignUse, cwes 
     * Use for writing info to file. 
     */
    public String[] getInfoArr(){
        String[] infoArr = {
            this.cveID, 
            this.vulnerabilityName, 
            this.dateAdded.toString(), 
            this.shortDescription,
            this.requiredAction,
            this.knownRansomwareCampaignUse,
            this.cwes
        };
        return infoArr;
    }

    @Override
    public String toString(){
        String result ="";
        String[] infoArr = getInfoArr();
        for (String s: infoArr){
            result+= (s + "\n");
        }
        return result;
    }

}
