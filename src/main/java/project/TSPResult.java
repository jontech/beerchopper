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
        if (this.routing.status() == RoutingModel.ROUTING_SUCCESS) {
            long index = this.routing.start(0);
            while (!this.routing.isEnd(index)) {
                route.add(index);
                index = this.solution.value(this.routing.nextVar(index));
            }
            long lastIndex = this.manager.indexToNode(this.routing.end(0));
            route.add(lastIndex);
        }
        return route;
    }

    public long getDistance() {
        long routeDistance = 0;
        if (this.routing.status() == RoutingModel.ROUTING_SUCCESS) {
            long index = routing.start(0);
            while (!routing.isEnd(index)) {
                long previousIndex = index;
                index = solution.value(routing.nextVar(index));
                routeDistance += routing.getArcCostForVehicle(previousIndex, index, 0);
            }
        }
        return routeDistance;
    }
}
