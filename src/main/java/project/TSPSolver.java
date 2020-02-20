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
        RoutingIndexManager manager =
            new RoutingIndexManager(data.transMat.length, data.vehicleNumber, data.starts, data.ends);
        RoutingModel routing = new RoutingModel(manager);

        final int transitCallbackIndex =
            routing.registerTransitCallback((long fromIndex, long toIndex) -> {
                    // Convert from routing variable Index to user NodeIndex.
                    int fromNode = manager.indexToNode(fromIndex);
                    int toNode = manager.indexToNode(toIndex);
                    return data.transMat[fromNode][toNode];
                });

        // routing costs
        routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);

        // distance constraint
        routing.addDimension(transitCallbackIndex,
                             0,    // null capacity slack
                             2000, // vehicle maximum capacities
                             true, // start cumul to zero
                             "Capacity");
        int penalty = data.getPenalty();
        for (int i = 1; i < data.transMat.length; ++i) {
            routing.addDisjunction(new long[] {manager.nodeToIndex(i)}, penalty);
        }

        // search params
        RoutingSearchParameters searchParameters =
            main.defaultRoutingSearchParameters()
            .toBuilder()
            .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
            .build();

        // solve
        Assignment solution =  routing.solveWithParameters(searchParameters);

        return new TSPResult(routing, manager, solution);
    }
}
