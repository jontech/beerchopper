package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.List;
import com.google.ortools.constraintsolver.Assignment;
import com.google.ortools.constraintsolver.FirstSolutionStrategy;
import com.google.ortools.constraintsolver.RoutingIndexManager;
import com.google.ortools.constraintsolver.RoutingModel;
import com.google.ortools.constraintsolver.RoutingSearchParameters;
import com.google.ortools.constraintsolver.main;
import java.util.logging.Logger;

public class App {
    private static final Logger logger = Logger.getLogger(App.class.getName());
    
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Double[]> geoLocations = new ArrayList<>();
        try {
            conn = DriverManager.getConnection("jdbc:mysql://db/beerchopper?user=root&password=123");
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM geo_location");
            while(rs.next()) {
                geoLocations.add(new Double[] { Double.parseDouble(rs.getString("latitude")),
                                                Double.parseDouble(rs.getString("longitude")) });
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return;
        }

        final DataModel data = new DataModel(geoLocations);
        TSPResult res = TSPSolver.solve(data);
        printSolution(res);
    }

    static void printSolution(TSPResult res) {
        // Solution cost.
        logger.info("Objective: " + res.solution.objectiveValue() + "km");
        // Inspect solution.
        logger.info("Route:");
        long routeDistance = 0;
        String route = "";
        long index = res.routing.start(0);
        while (!res.routing.isEnd(index)) {
            route += res.manager.indexToNode(index) + " -> ";
            long previousIndex = index;
            index = res.solution.value(res.routing.nextVar(index));
            routeDistance += res.routing.getArcCostForVehicle(previousIndex, index, 0);
        }
        route += res.manager.indexToNode(res.routing.end(0));
        logger.info(route);
        logger.info("Route distance: " + routeDistance + "km");
    }    
}
