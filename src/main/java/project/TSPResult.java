package project;

import com.google.ortools.constraintsolver.Assignment;
import com.google.ortools.constraintsolver.RoutingModel;
import com.google.ortools.constraintsolver.RoutingIndexManager;
import java.util.ArrayList;
import java.util.List;

class TSPResult {
    public RoutingModel routing;
    public RoutingIndexManager manager;
    public Assignment solution;

    public TSPResult(RoutingModel routing, RoutingIndexManager manager, Assignment solution) {
        this.routing = routing;
        this.manager = manager;
        this.solution = solution;
    }

    public List<Long> getRoute() {
        List<Long> route = new ArrayList<>();
        long index = this.routing.start(0);

        while (!this.routing.isEnd(index)) {
            route.add(index);
            index = this.solution.value(this.routing.nextVar(index));
        }
        long lastIndex = this.manager.indexToNode(this.routing.end(0));
        route.add(lastIndex);

        return route;
    }
}
