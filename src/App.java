import simulation.Simulation;
public class App {
    public static void main(String[] args){
        Simulation total = new Simulation();
        //REPLACE THE NAME OF THE CSV FILE HERE, INCLUDING THE .cvs EXTENSION AND THE PARENTHESIS.  
        total.setUpMalwareDefault("kJhRJiT7.csv"); 
        total.run();
    }
}
