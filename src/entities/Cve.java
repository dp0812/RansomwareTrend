package entities;
import java.time.LocalDate;
/**
 * To holds all the possible attribute of a recored Cve in the KEV list, in the appropriate data type. 
 */
public class Cve {
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
    private String cwes; //visual
    
    public Cve(){}
    public Cve(     
     String cveID,
     String vendorProject,
     String product,
     String vulnerabilityName,
     LocalDate dateAdded,
     String shortDescription,
     String requiredAction,
     LocalDate dueDate,
     String knownRansomwareCampaignUse,
     String notes,
     String cwes)
    {
        this.cveID = cveID;
        this.vendorProject = vendorProject;
        this.product = product;
        this.vulnerabilityName = vulnerabilityName;
        this.dateAdded = dateAdded;
        this.shortDescription = shortDescription;
        this.requiredAction = requiredAction;
        this.dueDate = dueDate;
        this.knownRansomwareCampaignUse = knownRansomwareCampaignUse;
        this.notes = notes;
        this.cwes = cwes;
    }

    public void setCveID(String o){
        this.cveID = o;
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
     * Get a String array that contains: that contains cveID, vulnerabilityName, dateAdded, shortDescription, 
     * requiredAction, knownRansomwareCampaignUse, cwes 
     * in this exact order. 
     * @param object extract visualization info from this object.
     * @return String array 
     */
    public static String[] getVisualizationInfoArray(Cve object){
        String[] visualizationInfo = {
            object.cveID, 
            object.vulnerabilityName, 
            object.dateAdded.toString(), 
            object.shortDescription,
            object.requiredAction,
            object.knownRansomwareCampaignUse,
            object.cwes
        };
        return visualizationInfo;
    }

}
