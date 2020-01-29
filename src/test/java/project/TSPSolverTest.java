package project;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TSPSolverTest {
    private static final Logger logger = Logger.getLogger(App.class.getName());

    @Test public void testDistanceConstraint() {
        final Long[][] transMat = {
            {0L, 7L, 5L},
            {7L, 0L, 4L},
            {5L, 4L, 0L},
        };
        
        final DataModel data = new DataModel(transMat);
        TSPResult res = TSPSolver.solve(data);

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

        assertEquals(16L, routeDistance);
    }
}
