import simulation.Simulation;
public class App {
    public static void main(String[] args){
        Simulation total = new Simulation();
        total.setUpMalware("datasets\\test-NIkeXLf.csv", "malwareTest.csv");
        total.setUpCve("datasets\\test_known_exploited_vulnerabilities.csv", "cveTest.csv");
        total.run();

    }
}
