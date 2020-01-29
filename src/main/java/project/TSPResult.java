package project;

import com.google.ortools.constraintsolver.Assignment;
import com.google.ortools.constraintsolver.RoutingModel;
import com.google.ortools.constraintsolver.RoutingIndexManager;

class TSPResult {
    public RoutingModel routing = null;
    public RoutingIndexManager manager = null;
    public Assignment solution = null;

    public TSPResult(RoutingModel routing, RoutingIndexManager manager, Assignment solution) {
        this.routing = routing;
        this.manager = manager;
        this.solution = solution;
    }
}
