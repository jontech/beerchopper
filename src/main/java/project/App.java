package project;

import java.sql.SQLException;
import java.util.function.Function;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;

public class App {
    private static final Logger logger = Logger.getLogger(App.class.getName());
    
    public static void main(String[] args) throws SQLException {
        final DB db = new DB("jdbc:mysql://db/beerchopper?user=root&password=123");
        final List<GeoLocation> geoLocations = db.getGeoLocations();
        final DataModel data = new DataModel(geoLocations);
        final TSPResult res = TSPSolver.solve(data);
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
