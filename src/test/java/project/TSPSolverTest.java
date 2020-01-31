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
        List<Long> route= res.getRoute();
        
        assertEquals(4, route.size());
        assertEquals((Long) 1L, route.get(0));
        assertEquals((Long) 2L, route.get(1));
        assertEquals((Long) 0L, route.get(2));
        assertEquals((Long) 1L, route.get(3));
    }
}
