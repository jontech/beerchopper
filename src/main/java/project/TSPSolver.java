package project;

import com.google.ortools.constraintsolver.Assignment;
import com.google.ortools.constraintsolver.FirstSolutionStrategy;
import com.google.ortools.constraintsolver.RoutingIndexManager;
import com.google.ortools.constraintsolver.RoutingModel;
import com.google.ortools.constraintsolver.RoutingSearchParameters;
import com.google.ortools.constraintsolver.main;

class TSPSolver {
    static {
        System.loadLibrary("jniortools");
    }

    public static TSPResult solve(DataModel data) {
        RoutingIndexManager manager = new RoutingIndexManager(data.transMat.length, data.vehicleNumber, data.depot);
        RoutingModel routing = new RoutingModel(manager);

        final int transitCallbackIndex =
            routing.registerTransitCallback((long fromIndex, long toIndex) -> {
                    // Convert from routing variable Index to user NodeIndex.
                    int fromNode = manager.indexToNode(fromIndex);
                    int toNode = manager.indexToNode(toIndex);
                    return data.transMat[fromNode][toNode];
                });

        routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);

        RoutingSearchParameters searchParameters =
            main.defaultRoutingSearchParameters()
            .toBuilder()
            .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
            .build();

        Assignment solution =  routing.solveWithParameters(searchParameters);

        return new TSPResult(routing, manager, solution);
    }
}
