package project;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class DataModelTest {
    @Test public void testBuildTransitionMatrix() {
        List<Double[]> geoLocations = new ArrayList<>();
        geoLocations.add(new Double[] {30.223400115966797, -97.76969909667969});
        geoLocations.add(new Double[] {37.782501220703125, -122.39299774169922});
        geoLocations.add(new Double[] {50.76679992675781, 4.30810022354126});

        Double[][] transMat = DataModel.makeTransitionMatrix(geoLocations, pointA -> pointB -> 1.1);

        assertEquals(3, transMat.length);
        assertEquals(3, transMat[0].length);
        assertEquals(1.1, transMat[0][0], 0.001);
    }

    @Test public void testGeoLocationDistance() {
        Double[] pointA = {30.223400115966797, -97.76969909667969};
        Double[] pointB = {37.782501220703125, -122.39299774169922};

        Double res = DataModel.getDistFunction().apply(pointA).apply(pointB);

        assertEquals(2411.58784, res, 0.001);
    }
}
