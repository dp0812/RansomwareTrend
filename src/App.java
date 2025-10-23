import simulation.Simulation;
public class App {
    public static void main(String[] args){
        Simulation sim01 = new Simulation("datasets\\test_known_exploited_vulnerabilities.csv", "test01.csv");
        sim01.run();
    }
}
